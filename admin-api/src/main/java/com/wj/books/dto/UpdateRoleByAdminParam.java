package com.wj.books.dto;

import java.util.List;

public class UpdateRoleByAdminParam {
    private Integer adminId;
    private List<Integer> roles;

    // Getters and Setters
    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }
}
