package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetType;

import java.util.List;

public interface AssetTypeService {
    void addAssetType(AssetType assetType);
    void updateAssetType(AssetType assetType);
    void deleteAssetType(Integer assetTypeId);
    AssetType getAssetTypeById(Integer assetTypeId);
    List<AssetType> getAllAssetTypes();
}
