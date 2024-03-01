package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.AssetAllocation;

import java.util.List;

public interface AssetAllocationDao {
    AssetAllocation selectById(Integer allocationId);

    List<AssetAllocation> selectAll();

    List<AssetAllocation> selectByCondition(AssetAllocation assetAllocation);

    void insert(AssetAllocation assetAllocation);

    void update(AssetAllocation assetAllocation);

    void deleteById(Integer allocationId);


}
