package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.AssetScrap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AssetScrapDao {
    int insertAssetScrap(AssetScrap assetScrap);

    void updateAssetScrap(AssetScrap assetScrap);

    List<AssetScrap> searchAssetScrap(AssetScrap assetScrap);

    int deleteAssetScrapById(@Param("scrapId") Long scrapId);

}
