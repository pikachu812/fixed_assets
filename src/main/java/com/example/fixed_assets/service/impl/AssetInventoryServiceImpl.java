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
    public AssetInventory saveAssetInventory(AssetInventory assetInventory) {


        Map<String, Object> inventoryByDepartment = fixedAssetDao.selectInventoryByDepartment(assetInventory.getDepartmentId());

        logger.info("inventoryByDepartment: " + inventoryByDepartment);


        assetInventory.setBookValue(BigDecimal.valueOf((Double) inventoryByDepartment.get("totalPrice")));
        assetInventory.setBookQuantity((Integer) inventoryByDepartment.get("totalPrice"));
        assetInventory.setInventoryDate(new Date());

        assetInventoryDao.insert(assetInventory);
        return assetInventory;
    }

    @Override
    public boolean deleteAssetInventory(Integer inventoryId) {
        return assetInventoryDao.deleteById(inventoryId) > 0;
    }

    @Override
    public AssetInventory updateAssetInventory(AssetInventory assetInventory) {
        assetInventoryDao.update(assetInventory);
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
