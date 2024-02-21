package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.Department;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceTest.class);

    @Autowired
    private DepartmentService departmentService;

    private Department testDepartment;

    @Before
    public void setUp() {
        logger.info(() -> "Setting up test data for DepartmentServiceTest");

        testDepartment = new Department();
        testDepartment.setName("IT");
        testDepartment.setDescription("Information Technology");

        // 可以在这里添加其他初始化代码
    }

    @Test
    public void testDepartmentService() {
        addDepartment();
        getAllDepartments();
        modifyDepartment();
        getDepartmentById();
        removeDepartment();
    }

    private void addDepartment() {
        logger.info(() -> "-------------------> addDepartment");
        departmentService.addDepartment(testDepartment);
        // 断言或其他验证逻辑
        logger.info(() -> "Added department: " + testDepartment.getDepartmentId());
    }

    private void modifyDepartment() {
        logger.info(() -> "-------------------> modifyDepartment");
        testDepartment.setDescription("IT and Services");
        departmentService.updateDepartment(testDepartment);
        // 断言或其他验证逻辑
    }

    private void removeDepartment() {
        logger.info(() -> "-------------------> removeDepartment");
        departmentService.deleteDepartment(testDepartment.getDepartmentId());
        // 断言或其他验证逻辑
    }

    private void getDepartmentById() {
        logger.info(() -> "-------------------> getDepartmentById");
        Department department = departmentService.getDepartmentById(testDepartment.getDepartmentId());
        // 断言或其他验证逻辑
        logger.info(() -> "Fetched department: " + department);
    }

    private void getAllDepartments() {
        logger.info(() -> "-------------------> getAllDepartments");
        List<Department> departments = departmentService.getAllDepartments();
        // 断言或其他验证逻辑
        logger.info(() -> "Total departments found: " + departments.size());
    }
}
