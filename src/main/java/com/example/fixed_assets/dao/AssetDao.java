package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.Asset;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AssetDao {
    void insertAsset(Asset asset);

    List<Asset> selectAllAssets();
    Asset selectAssetById(Integer id);
    List<Asset> selectAssetsByCondition(Map<String, Object> conditions);

    void updateAsset(Asset asset);

    void deleteAsset(Integer id);


}
