package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentDao {
    void insertDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(Integer departmentId);
    Department selectDepartmentById(Integer departmentId);
    List<Department> selectAllDepartments();
}
