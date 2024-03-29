package com.example.fixed_assets.entity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 资产统计实体类
 */
@lombok.Data
public class AssetStatistics {
    private int totalAssetCount;
    private BigDecimal totalAssetCost;
    private Map<String, Integer> countByCategory;
}
