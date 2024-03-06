package com.example.fixed_assets.service;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    List<Map<String, Object>> assetByDepartment();

    List<Map<String, Object>> assetByType();

}
