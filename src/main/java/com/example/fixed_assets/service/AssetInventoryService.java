package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetInventory;
import java.util.List;

public interface AssetInventoryService {
    AssetInventory saveAssetInventory(AssetInventory assetInventory);
    boolean deleteAssetInventory(Integer inventoryId);
    AssetInventory updateAssetInventory(AssetInventory assetInventory);
    AssetInventory getAssetInventoryById(Integer inventoryId);
    List<AssetInventory> getAllAssetInventories();
    List<AssetInventory> searchAssetInventories(AssetInventory assetInventory);
}
