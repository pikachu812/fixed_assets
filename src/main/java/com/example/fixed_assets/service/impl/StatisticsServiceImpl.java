package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.StatisticsDao;
import com.example.fixed_assets.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {


    final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private StatisticsDao statisticsDao;

    @Autowired
    public StatisticsServiceImpl(StatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }


    @Override
    public List<Map<String, Object>> assetByDepartment() {
        return statisticsDao.assetByDepartment();
    }

    @Override
    public List<Map<String, Object>> assetByType() {
        return statisticsDao.assetByType();
    }
}
