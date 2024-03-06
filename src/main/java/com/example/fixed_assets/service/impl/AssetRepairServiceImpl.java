package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetRepairDao;
import com.example.fixed_assets.dao.FixedAssetDao;
import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.AssetRepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AssetRepairServiceImpl implements AssetRepairService {

    final Logger logger = LoggerFactory.getLogger(AssetRepairServiceImpl.class);

    private final AssetRepairDao AssetRepairDao;
    private final FixedAssetDao fixedAssetDao;



    @Autowired
    public AssetRepairServiceImpl(AssetRepairDao AssetRepairDao, FixedAssetDao fixedAssetDao) {
        this.AssetRepairDao = AssetRepairDao;
        this.fixedAssetDao = fixedAssetDao;
    }



    Date parseDate(Date date) {
        long originalTime = date.getTime();
        long offset = date.getTimezoneOffset() * 60 * 1000;
        return new Date(originalTime + offset);
    }


    @Override
    @Transactional
    public int addAssetRepair(AssetRepair assetRepair) {

        FixedAsset fixedAsset = fixedAssetDao.selectFixedAssetById(assetRepair.getAssetId());

        fixedAsset.setStatus("维修中");

        fixedAssetDao.updateFixedAsset(fixedAsset);

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
    public void updateAssetRepair(AssetRepair assetRepair) {

        logger.info("更新维修记录");
        logger.info(assetRepair.toString());

        AssetRepairDao.updateAssetRepair(assetRepair);
    }

    @Override
    public int deleteAssetRepairById(Long repairId) {
        return AssetRepairDao.deleteAssetRepairById(repairId);
    }

    @Override
    public List<AssetRepair> searchAssetRepair(AssetRepair assetRepair) {
        return AssetRepairDao.searchAssetRepair(assetRepair);
    }

    @Override
    @Transactional
    public void repairAsset(Long repairId) {
        AssetRepair assetRepair = AssetRepairDao.selectAssetRepairById(repairId);
        assetRepair.setStatus("已维修");
        AssetRepairDao.updateAssetRepair(assetRepair);

        FixedAsset fixedAsset = fixedAssetDao.selectFixedAssetById(assetRepair.getAssetId());

        //2024-03-06 是使用中报修了，所以维修后状态应该是使用中
        fixedAsset.setStatus("使用中");
        fixedAssetDao.updateFixedAsset(fixedAsset);
    }
}
