package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.Department;
import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
        return ResponseEntity.ok("Department added successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
        return ResponseEntity.ok("Department updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok("Department deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Integer departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @PostMapping("/details")
    public ResponseEntity<List<Department>> searchDepartments(@RequestBody Map<String,Object> department) {
        List<Department> departments = departmentService.searchDepartments(department);
        return ResponseEntity.ok(departments);
    }
}
