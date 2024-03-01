package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetAllocation;

import java.util.List;

/**
 * 资产领用服务接口
 */
public interface AssetAllocationService {
    AssetAllocation getAssetAllocationById(Integer allocationId); // 根据ID获取领用记录
    List<AssetAllocation> getAllAssetAllocations(); // 获取所有领用记录
    void saveAssetAllocation(AssetAllocation assetAllocation); // 保存领用记录
    void updateAssetAllocation(AssetAllocation assetAllocation); // 更新领用记录
    void deleteAssetAllocationById(Integer allocationId); // 根据ID删除领用记录
}