package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetAllocation;
import com.example.fixed_assets.service.AssetAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assetAllocation")
public class AssetAllocationController {

    private final AssetAllocationService assetAllocationService;

    public AssetAllocationController(AssetAllocationService assetAllocationService) {
        this.assetAllocationService = assetAllocationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetAllocation> getAssetAllocationById(@PathVariable Integer id) {
        return ResponseEntity.ok(assetAllocationService.getAssetAllocationById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<AssetAllocation>> getAllAssetAllocations() {
        return ResponseEntity.ok(assetAllocationService.getAllAssetAllocations());
    }

    @PostMapping("/")
    public ResponseEntity<Void> createAssetAllocation(@RequestBody AssetAllocation assetAllocation) {
        assetAllocationService.saveAssetAllocation(assetAllocation);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateAssetAllocation(@RequestBody AssetAllocation assetAllocation) {
        assetAllocationService.updateAssetAllocation(assetAllocation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetAllocation(@PathVariable Integer id) {
        assetAllocationService.deleteAssetAllocationById(id);
        return ResponseEntity.ok().build();
    }
}
