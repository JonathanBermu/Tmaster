package com.example.Users.Types;

public enum RolesEnum {
    ADMIN(1), USER(2);
    private int roleId;
    RolesEnum(int i) {
        roleId = i;
    }
    public Integer getValue() {
        return this.roleId;
    }
}
