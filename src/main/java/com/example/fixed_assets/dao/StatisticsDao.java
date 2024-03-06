package com.example.fixed_assets.dao;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsDao {

    @MapKey("department")
    List<Map<String, Object>> assetByDepartment();


    @MapKey("type")
    List<Map<String, Object>> assetByType();

}
