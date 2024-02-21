package com.example.fixed_assets.dao;


import com.example.fixed_assets.entity.AssetType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetTypeDao {
    void insertAssetType(AssetType assetType);
    void updateAssetType(AssetType assetType);
    void deleteAssetType(Integer assetTypeId);
    AssetType selectAssetTypeById(Integer assetTypeId);
    List<AssetType> selectAllAssetTypes();
}