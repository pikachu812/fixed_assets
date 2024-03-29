package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetRepair;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AssetRepairService {

    // 添加维修记录
    int addAssetRepair(AssetRepair assetRepair);

    // 通过ID查询维修记录
    AssetRepair getAssetRepairById(Long repairId);

    // 查询所有维修记录
    List<AssetRepair> getAllAssetRepairs();

    // 更新维修记录
    void updateAssetRepair(AssetRepair assetRepair);

    // 通过ID删除维修记录
    int deleteAssetRepairById(Long repairId);

    List<AssetRepair> searchAssetRepair(AssetRepair assetRepair);

    void repairAsset(Long repairId, Double cost);

}
