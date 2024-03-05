package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetScrap;
import com.example.fixed_assets.service.AssetScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/assetScrap")
public class AssetScrapController {

    private final AssetScrapService assetScrapService;

    @Autowired
    public AssetScrapController(AssetScrapService assetScrapService) {
        this.assetScrapService = assetScrapService;
    }

    // 添加维修记录
    @PostMapping("/add")
    public ResponseEntity<AssetScrap> addAssetScrap(@RequestBody AssetScrap assetRepair) {
        assetScrapService.addAssetScrap(assetRepair);
        return ResponseEntity.ok(assetRepair);
    }

    // 更新维修记录
    @PostMapping("/update/{scrapId}")
    public ResponseEntity<?> updateAssetScrap(
            @PathVariable Integer scrapId,
            @RequestBody AssetScrap assetScrap
    ) {

        assetScrap.setScrapId(scrapId);
        assetScrapService.updateAssetScrap(assetScrap);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/search")
    public ResponseEntity<List<AssetScrap>> searchAssetScrap(@RequestBody AssetScrap assetScrap) {
        return ResponseEntity.ok(assetScrapService.searchAssetScrap(assetScrap));
    }

    // 通过ID删除维修记录
    @DeleteMapping("/delete/{scrapId}")
    public ResponseEntity<Void> deleteAssetScrapById(@PathVariable Long scrapId) {
        if (assetScrapService.deleteAssetScrapById(scrapId) > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
