package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentDao {
    void insertDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(Integer departmentId);
    Department selectDepartmentById(Integer departmentId);
    List<Department> selectAllDepartments();
    List<Department> searchDepartments(Map<String, Object> department);
}
