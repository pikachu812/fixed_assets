package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetInventoryDao;
import com.example.fixed_assets.dao.FixedAssetDao;
import com.example.fixed_assets.entity.AssetInventory;
import com.example.fixed_assets.service.AssetInventoryService;
import com.example.fixed_assets.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AssetInventoryServiceImpl implements AssetInventoryService {


    final Logger logger = LoggerFactory.getLogger(AssetInventoryServiceImpl.class);

    @Autowired
    private AssetInventoryDao assetInventoryDao;

    @Autowired
    private FixedAssetDao fixedAssetDao;


    @Override
    @Transactional
    public AssetInventory saveAssetInventory(AssetInventory assetInventory) {


        Map<String, Object> inventoryByDepartment = fixedAssetDao.selectInventoryByDepartment(assetInventory.getDepartmentId());

        logger.info("inventoryByDepartment: " + inventoryByDepartment);

        BigDecimal totalPrice = (BigDecimal) inventoryByDepartment.get("totalPrice");
        Long totalNum = (Long) inventoryByDepartment.get("totalNum");

        logger.info("totalPrice: " + totalPrice);
        logger.info("totalNum: " + totalNum);

        assetInventory.setBookValue(totalPrice);
        assetInventory.setBookQuantity(totalNum.intValue());
        assetInventory.setInventoryDate(new Date());

        assetInventoryDao.insert(assetInventory);
        return assetInventory;
    }

    @Override
    public boolean deleteAssetInventory(Integer inventoryId) {
        return assetInventoryDao.deleteById(inventoryId) > 0;
    }

    @Override
    @Transactional
    public AssetInventory updateAssetInventory(AssetInventory assetInventory) {

        AssetInventory oldAssetInventory = assetInventoryDao.selectById(assetInventory.getInventoryId());
        if (oldAssetInventory == null) {
            return null;
        }


        if (assetInventory.getDepartmentId() != null && !assetInventory.getDepartmentId().equals(oldAssetInventory.getDepartmentId())) {
            Map<String, Object> inventoryByDepartment = fixedAssetDao.selectInventoryByDepartment(assetInventory.getDepartmentId());

            BigDecimal totalPrice = (BigDecimal) inventoryByDepartment.get("totalPrice");
            Long totalNum = (Long) inventoryByDepartment.get("totalNum");

            logger.info("totalPrice: " + totalPrice);
            logger.info("totalNum: " + totalNum);
            oldAssetInventory.setDepartmentId(assetInventory.getDepartmentId());
            oldAssetInventory.setBookValue(totalPrice);
            oldAssetInventory.setBookQuantity(totalNum.intValue());
            oldAssetInventory.setInventoryDate(new Date());

        }

        oldAssetInventory.setInventoryName(assetInventory.getInventoryName());


        assetInventoryDao.update(oldAssetInventory);
        return assetInventory;
    }

    @Override
    public AssetInventory getAssetInventoryById(Integer inventoryId) {
        return assetInventoryDao.selectById(inventoryId);
    }

    @Override
    public List<AssetInventory> getAllAssetInventories() {
        return assetInventoryDao.selectAll();
    }

    @Override
    public List<AssetInventory> searchAssetInventories(AssetInventory assetInventory) {
        return assetInventoryDao.searchByCondition(assetInventory);
    }
}
