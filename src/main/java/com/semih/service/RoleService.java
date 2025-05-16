package com.semih.service;

import com.semih.dto.request.RoleRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.RoleResponse;
import com.semih.exception.ConflictException;
import com.semih.exception.NotFoundException;
import com.semih.model.Auditable;
import com.semih.model.Role;
import com.semih.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private Role mapToEntity(RoleRequest roleRequest) {
        return new Role(
                roleRequest.roleName()
        );
    }

    private RoleResponse mapToResponse(Role role) {
        return new RoleResponse(
                new BaseResponse(
                        role.getId(),
                        role.getCreatedDate(),
                        role.getModifiedDate()
                ),
                role.getRole()
        );
    }

    private void validateUniqueness(RoleRequest roleRequest) {
        if (roleRepository.existsByRole(roleRequest.roleName())) {
            throw new ConflictException("Bu rol zaten mevcut!!!");
        }
    }

    @Auditable(actionType = "Ekledi", targetEntity = "Rol")
    public RoleResponse saveRole(RoleRequest roleRequest) {
        validateUniqueness(roleRequest);
        Role savedRole = roleRepository.save(mapToEntity(roleRequest));
        return mapToResponse(savedRole);
    }

    public List<RoleResponse> getRoleList() {
        return roleRepository.findAll().stream()
                .filter(role -> !"SUPER_ADMIN".equals(role.getRole()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<String> getRoles() {
        return roleRepository.findAllByRoles().stream()
                .filter(role -> !"SUPER_ADMIN".equals(role))
                .collect(Collectors.toList());
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByRole(roleName).orElseThrow(() -> new NotFoundException("Role bulunamadı!!!"));
    }

    public Role getRoleForPermissionById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role bulunamadı!!!" + id));
    }

    public RoleResponse getRoleById(Long id) {
        return mapToResponse(roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role bulunamadı!!!" + id)));
    }

    @Auditable(actionType = "Güncelledi", targetEntity = "Rol")
    public RoleResponse updateRoleById(Long id, RoleRequest roleRequest) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role bulunamadı!!!" + id));

        validateUniqueness(roleRequest);

        Role updatedRole = mapToEntity(roleRequest);
        updatedRole.setId(id);
        updatedRole.setCreatedDate(existingRole.getCreatedDate());

        updatedRole = roleRepository.save(updatedRole);

        return mapToResponse(updatedRole);
    }

    @Auditable(actionType = "Sildi", targetEntity = "Rol")
    public RoleResponse deleteRoleById(Long id) {
        Role deletedRole = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Role bulunamadı" + id));
        roleRepository.delete(deletedRole);
        return mapToResponse(deletedRole);
    }

}
