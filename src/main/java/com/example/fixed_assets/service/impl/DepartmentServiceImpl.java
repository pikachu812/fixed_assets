package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.DepartmentDao;
import com.example.fixed_assets.entity.Department;
import com.example.fixed_assets.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public void addDepartment(Department department) {
        departmentDao.insertDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(Integer departmentId) {
        departmentDao.deleteDepartment(departmentId);
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        return departmentDao.selectDepartmentById(departmentId);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.selectAllDepartments();
    }
}
