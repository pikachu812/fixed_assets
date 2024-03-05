package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.entity.AssetScrap;

import java.util.List;

public interface AssetScrapService {

    // 添加报废
    int addAssetScrap(AssetScrap assetScrap);

    void updateAssetScrap(AssetScrap assetScrap);

    List<AssetScrap> searchAssetScrap(AssetScrap assetScrap);

    int deleteAssetScrapById(Long scrapId);


}
