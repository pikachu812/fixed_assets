package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.service.AssetRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assetRepair")
public class AssetRepairController {

    private final AssetRepairService assetRepairService;

    @Autowired
    public AssetRepairController(AssetRepairService assetRepairService) {
        this.assetRepairService = assetRepairService;
    }

    // 添加维修记录
    @PostMapping("/")
    public ResponseEntity<AssetRepair> addAssetRepair(@RequestBody AssetRepair assetRepair) {
        assetRepairService.addAssetRepair(assetRepair);
        return ResponseEntity.ok(assetRepair);
    }

    // 通过ID获取维修记录
    @GetMapping("/{repairId}")
    public ResponseEntity<AssetRepair> getAssetRepairById(@PathVariable Long repairId) {
        AssetRepair assetRepair = assetRepairService.getAssetRepairById(repairId);
        return assetRepair != null ? ResponseEntity.ok(assetRepair) : ResponseEntity.notFound().build();
    }

    // 获取所有维修记录
    @GetMapping("/all")
    public ResponseEntity<List<AssetRepair>> getAllAssetRepairs() {
        return ResponseEntity.ok(assetRepairService.getAllAssetRepairs());
    }

    // 更新维修记录
    @PutMapping("/")
    public ResponseEntity<AssetRepair> updateAssetRepair(@RequestBody AssetRepair assetRepair) {
        if (assetRepairService.updateAssetRepair(assetRepair) > 0) {
            return ResponseEntity.ok(assetRepair);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 通过ID删除维修记录
    @DeleteMapping("/{repairId}")
    public ResponseEntity<Void> deleteAssetRepairById(@PathVariable Long repairId) {
        if (assetRepairService.deleteAssetRepairById(repairId) > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
