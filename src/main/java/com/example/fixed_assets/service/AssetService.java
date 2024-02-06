package com.example.fixed_assets.service;
import com.example.fixed_assets.dao.AssetDao;
import com.example.fixed_assets.entity.Asset;
import com.example.fixed_assets.entity.AssetStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssetService {
    private final AssetDao assetDao;

    @Autowired
    public AssetService(AssetDao assetDao) {
        this.assetDao = assetDao;
    }

    public void addAsset(Asset asset) {
        assetDao.insertAsset(asset);
    }

    public List<Asset> getAllAssets() {
        return assetDao.selectAllAssets();
    }

    public Asset getAssetById(Integer id) {
        return assetDao.selectAssetById(id);
    }

    public List<Asset> getAssetsByCondition(Map<String, Object> conditions) {
        return assetDao.selectAssetsByCondition(conditions);
    }

    public void updateAsset(Asset asset) {
        assetDao.updateAsset(asset);
    }

    public void deleteAsset(Integer id) {
        assetDao.deleteAsset(id);
    }


    public AssetStatistics getAssetStatistics() {
        //TODO 实现统计逻辑
        return null;
    }




}
