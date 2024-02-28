package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.Department;
import java.util.List;
import java.util.Map;

public interface DepartmentService {
    void addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(Integer departmentId);
    Department getDepartmentById(Integer departmentId);
    List<Department> getAllDepartments();
    List<Department> searchDepartments(Map<String, Object> department);
}
