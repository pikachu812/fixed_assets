package com.example.fixed_assets.entity;

import lombok.Data;

/**
 * employee
 */
@lombok.Data
public class Employee{
    private Integer employeeId;
    private String name;
    private Integer departmentId;
    private Department department;

}