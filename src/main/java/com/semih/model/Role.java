package com.semih.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<User> userList;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Permission> permission;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roleName) {
        this.role = roleName;
    }


}
