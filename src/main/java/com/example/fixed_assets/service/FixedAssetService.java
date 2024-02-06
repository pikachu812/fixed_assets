package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.FixedAsset;

import java.util.List;

public interface FixedAssetService {
    void addFixedAsset(FixedAsset fixedAsset);
    void modifyFixedAsset(FixedAsset fixedAsset);
    void removeFixedAsset(Integer assetId);
    FixedAsset getFixedAssetById(Integer assetId);
    List<FixedAsset> getAllFixedAssets();
}
