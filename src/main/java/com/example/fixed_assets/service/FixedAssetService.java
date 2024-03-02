package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.FixedAsset;

import java.util.List;
import java.util.Map;

public interface FixedAssetService {
    void addFixedAsset(FixedAsset fixedAsset);
    void modifyFixedAsset(FixedAsset fixedAsset);
    void removeFixedAsset(Integer assetId);
    FixedAsset getFixedAssetById(Integer assetId);
    List<FixedAsset> getAllFixedAssets();
    List<FixedAsset> getFixedAssetsByCondition(FixedAsset query);
    List<Map<String, Object>> getFixedAssetsGroupByAssetName();
}
