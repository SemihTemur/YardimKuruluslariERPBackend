package com.semih.service;

import com.semih.dto.request.PermissionRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.PermissionResponse;
import com.semih.exception.ConflictException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.Permission;
import com.semih.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    public PermissionService(PermissionRepository permissionRepository, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }

    private Permission mapToEntity(PermissionRequest permissionRequest) {
        return new Permission(
                roleService.getRoleByName(permissionRequest.roleName()),
                permissionRequest.entityName(),
                permissionRequest.actionType()
        );
    }


    private PermissionResponse mapToResponse(Permission permission) {
        return new PermissionResponse(
                new BaseResponse(
                        permission.getId(),
                        permission.getCreatedDate(),
                        permission.getModifiedDate()
                ),
                permission.getRole().getRole(),
                permission.getEntityName(),
                permission.getActionType()
        );
    }

    private void validateUniqueness(Permission permission) {
        if (permissionRepository.existsByRoleAndEntityNameAndActionType(permission.getRole(), permission.getEntityName(), permission.getActionType())) {
            throw new ConflictException("Böyle bir yetki zaten mevcut!!!");
        }
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Yetkilendirme")
    public PermissionResponse savePermission(PermissionRequest permissionRequest) {
        Permission permission = mapToEntity(permissionRequest);
        validateUniqueness(permission);
        return mapToResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getPermissionList() {
        return permissionRepository.findAll().stream()
                .filter(permission -> !"SUPER_ADMIN".equals(permission.getRole().getRole()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PermissionResponse getPermissionById(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bulunamadı!!!"));
        return mapToResponse(permission);
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Yetkilendirme")
    public PermissionResponse updatePermissionById(Long id, PermissionRequest permissionRequest) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bulunamadı"));

        Permission updatedPermission = mapToEntity(permissionRequest);
        validateUniqueness(updatedPermission);

        updatedPermission.setId(id);
        updatedPermission.setCreatedDate(permission.getCreatedDate());
        updatedPermission.setModifiedDate(permission.getModifiedDate());

        return mapToResponse(permissionRepository.save(updatedPermission));
    }

    @Auditable(actionType = "Sildi", targetEntity = "Yetkilendirme")
    public PermissionResponse deletePermissionById(Long id) {
        Permission deletedPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Bulunamadı!!!"));
        permissionRepository.delete(deletedPermission);
        return mapToResponse(deletedPermission);
    }


}
