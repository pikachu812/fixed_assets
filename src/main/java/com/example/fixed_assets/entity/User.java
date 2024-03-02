package com.example.fixed_assets.entity;

import java.util.List;

/**
 * 用户实体类
 */
@lombok.Data
public class User {

    private Integer userId;
    private String username;
    private String password;
    private Integer roleId; // 关联角色ID
    private Integer employeeId; // 关联员工ID
    private Role role; // 关联角色对象
    private Employee employee; // 关联员工对象
    private List<Permission> permissions; // 关联权限对象


}
