package com.semih.model;

import com.semih.enums.ActionType;
import com.semih.enums.EntityName;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "entity_name", "action_type"})
})
public class Permission extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_name", nullable = false)
    private EntityName entityName;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    public Permission() {
    }

    public Permission(Role role, EntityName entityName, ActionType actionType) {
        this.role = role;
        this.entityName = entityName;
        this.actionType = actionType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public EntityName getEntityName() {
        return entityName;
    }

    public void setEntityName(EntityName entityName) {
        this.entityName = entityName;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
