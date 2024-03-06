package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.AssetInventory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetInventoryDao {
    int insert(AssetInventory assetInventory);

    int deleteById(Integer inventoryId);

    int update(AssetInventory assetInventory);

    AssetInventory selectById(Integer inventoryId);

    List<AssetInventory> selectAll();

    List<AssetInventory> searchByCondition(AssetInventory assetInventory);

}
