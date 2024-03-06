package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetInventory;
import com.example.fixed_assets.service.AssetInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/assetInventory")
public class AssetInventoryController {

    @Autowired
    private AssetInventoryService assetInventoryService;

    @PostMapping("/add")
    public ResponseEntity<AssetInventory> addAssetInventory(@RequestBody AssetInventory assetInventory) {
        AssetInventory savedAssetInventory = assetInventoryService.saveAssetInventory(assetInventory);
        return ResponseEntity.ok(savedAssetInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetInventory(@PathVariable("id") Integer inventoryId) {
        boolean isDeleted = assetInventoryService.deleteAssetInventory(inventoryId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{inventoryId}")
    public ResponseEntity<AssetInventory> updateAssetInventory(@PathVariable Integer inventoryId,@RequestBody AssetInventory assetInventory) {
        assetInventory.setInventoryId(inventoryId);
        AssetInventory updatedAssetInventory = assetInventoryService.updateAssetInventory(assetInventory);
        return ResponseEntity.ok(updatedAssetInventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetInventory> getAssetInventoryById(@PathVariable("id") Integer inventoryId) {
        AssetInventory assetInventory = assetInventoryService.getAssetInventoryById(inventoryId);
        if (assetInventory != null) {
            return ResponseEntity.ok(assetInventory);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<AssetInventory>> getAllAssetInventories() {
        List<AssetInventory> assetInventories = assetInventoryService.getAllAssetInventories();
        return ResponseEntity.ok(assetInventories);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AssetInventory>> searchAssetInventories(@RequestBody AssetInventory assetInventory) {
        List<AssetInventory> foundAssetInventories = assetInventoryService.searchAssetInventories(assetInventory);
        return ResponseEntity.ok(foundAssetInventories);
    }
}
