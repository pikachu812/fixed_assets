package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.FixedAsset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FixedAssetDao {
    void insertFixedAsset(FixedAsset fixedAsset);
    void updateFixedAsset(FixedAsset fixedAsset);
    void deleteFixedAsset(Integer assetId);
    FixedAsset selectFixedAssetById(Integer assetId);
    List<FixedAsset> selectAllFixedAssets();
    List<FixedAsset> getFixedAssetsByCondition(FixedAsset query);

    List<Map<String, Object>> getFixedAssetsGroupByAssetName();
}

