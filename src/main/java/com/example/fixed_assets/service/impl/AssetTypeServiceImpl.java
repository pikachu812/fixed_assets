package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetTypeDao;
import com.example.fixed_assets.entity.AssetType;
import com.example.fixed_assets.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    @Autowired
    AssetTypeDao assetTypeDao;
    @Override
    public void addAssetType(AssetType assetType) {
        assetTypeDao.insertAssetType(assetType);
    }

    @Override
    public void updateAssetType(AssetType assetType) {
        assetTypeDao.updateAssetType(assetType);
    }

    @Override
    public void deleteAssetType(Integer assetTypeId) {
        assetTypeDao.deleteAssetType(assetTypeId);
    }

    @Override
    public AssetType getAssetTypeById(Integer assetTypeId) {
        return assetTypeDao.selectAssetTypeById(assetTypeId);
    }

    @Override
    public List<AssetType> getAllAssetTypes() {
        return assetTypeDao.selectAllAssetTypes();
    }

    @Override
    public List<AssetType> getAssetTypeByCondition(AssetType assetType) {
        return assetTypeDao.selectAssetTypeByCondition(assetType);
    }
}
