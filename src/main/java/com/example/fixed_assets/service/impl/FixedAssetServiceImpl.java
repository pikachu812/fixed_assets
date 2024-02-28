package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.FixedAssetDao;
import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.FixedAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixedAssetServiceImpl implements FixedAssetService {

    @Autowired
    private FixedAssetDao fixedAssetDao;

    @Override
    public void addFixedAsset(FixedAsset fixedAsset) {
        fixedAssetDao.insertFixedAsset(fixedAsset);
    }

    @Override
    public void modifyFixedAsset(FixedAsset fixedAsset) {
        fixedAssetDao.updateFixedAsset(fixedAsset);
    }

    @Override
    public void removeFixedAsset(Integer assetId) {
        fixedAssetDao.deleteFixedAsset(assetId);
    }

    @Override
    public FixedAsset getFixedAssetById(Integer assetId) {
        return fixedAssetDao.selectFixedAssetById(assetId);
    }

    @Override
    public List<FixedAsset> getAllFixedAssets() {
        return fixedAssetDao.selectAllFixedAssets();
    }

    @Override
    public List<FixedAsset> getFixedAssetsByCondition(FixedAsset query) {
        return fixedAssetDao.getFixedAssetsByCondition(query);
    }

}
