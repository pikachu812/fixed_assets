package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.Department;
import java.util.List;

public interface DepartmentService {
    void addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(Integer departmentId);
    Department getDepartmentById(Integer departmentId);
    List<Department> getAllDepartments();
}
