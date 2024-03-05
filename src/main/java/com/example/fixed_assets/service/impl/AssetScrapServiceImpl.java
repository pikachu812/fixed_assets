package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetRepairDao;
import com.example.fixed_assets.dao.AssetScrapDao;
import com.example.fixed_assets.dao.FixedAssetDao;
import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.entity.AssetScrap;
import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.AssetRepairService;
import com.example.fixed_assets.service.AssetScrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AssetScrapServiceImpl implements AssetScrapService {

    final Logger logger = LoggerFactory.getLogger(AssetScrapServiceImpl.class);

    private final AssetScrapDao assetScrapDao;
    private final FixedAssetDao fixedAssetDao;



    @Autowired
    public AssetScrapServiceImpl(AssetScrapDao assetScrapDao, FixedAssetDao fixedAssetDao) {
        this.assetScrapDao = assetScrapDao;
        this.fixedAssetDao = fixedAssetDao;
    }




    @Override
    @Transactional
    public int addAssetScrap(AssetScrap assetScrap) {
        FixedAsset fixedAsset = fixedAssetDao.selectFixedAssetById(assetScrap.getAssetId());

        fixedAsset.setStatus("报废");

        fixedAssetDao.updateFixedAsset(fixedAsset);

        return assetScrapDao.insertAssetScrap(assetScrap);
    }

    @Override
    public void updateAssetScrap(AssetScrap assetScrap) {
        logger.info("更新报废记录");
        logger.info(assetScrap.toString());

        assetScrapDao.updateAssetScrap(assetScrap);
    }

    @Override
    public List<AssetScrap> searchAssetScrap(AssetScrap assetScrap) {
        return assetScrapDao.searchAssetScrap(assetScrap);
    }

    @Override
    public int deleteAssetScrapById(Long scrapId) {
        return assetScrapDao.deleteAssetScrapById(scrapId);
    }

}
