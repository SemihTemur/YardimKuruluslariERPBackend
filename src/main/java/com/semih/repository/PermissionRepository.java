package com.semih.repository;

import com.semih.enums.ActionType;
import com.semih.enums.EntityName;
import com.semih.model.Permission;
import com.semih.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByRoleAndEntityNameAndActionType(Role role, EntityName entityName, ActionType actionType);


}
