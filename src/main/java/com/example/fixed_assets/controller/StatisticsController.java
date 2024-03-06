package com.example.fixed_assets.controller;

import com.example.fixed_assets.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // 各部门占有资产
    @GetMapping("/assetByDepartment")
    public ResponseEntity<List<Map<String, Object>>> assetByDepartment() {
        return ResponseEntity.ok(statisticsService.assetByDepartment());
    }

    // 不同类别资产
    @GetMapping("/assetByType")
    public ResponseEntity<List<Map<String, Object>>> assetByType() {
        return ResponseEntity.ok(statisticsService.assetByType());
    }





}
