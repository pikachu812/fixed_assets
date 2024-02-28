package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetRepairDao;
import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.service.AssetRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AssetRepairServiceImpl implements AssetRepairService {

    private final AssetRepairDao AssetRepairDao;

    @Autowired
    public AssetRepairServiceImpl(AssetRepairDao AssetRepairDao) {
        this.AssetRepairDao = AssetRepairDao;
    }

    @Override
    public int addAssetRepair(AssetRepair assetRepair) {
        return AssetRepairDao.insertAssetRepair(assetRepair);
    }

    @Override
    public AssetRepair getAssetRepairById(Long repairId) {
        return AssetRepairDao.selectAssetRepairById(repairId);
    }

    @Override
    public List<AssetRepair> getAllAssetRepairs() {
        return AssetRepairDao.selectAllAssetRepairs();
    }

    @Override
    public int updateAssetRepair(AssetRepair assetRepair) {
        return AssetRepairDao.updateAssetRepair(assetRepair);
    }

    @Override
    public int deleteAssetRepairById(Long repairId) {
        return AssetRepairDao.deleteAssetRepairById(repairId);
    }
}
