package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetRepair;
import com.example.fixed_assets.service.AssetRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/assetRepair")
public class AssetRepairController {

    private final AssetRepairService assetRepairService;

    @Autowired
    public AssetRepairController(AssetRepairService assetRepairService) {
        this.assetRepairService = assetRepairService;
    }

    // 添加维修记录
    @PostMapping("/add")
    public ResponseEntity<AssetRepair> addAssetRepair(@RequestBody AssetRepair assetRepair) {
        assetRepairService.addAssetRepair(assetRepair);
        return ResponseEntity.ok(assetRepair);
    }

    // 更新维修记录
    @PostMapping("/update/{repairId}")
    public ResponseEntity<?> updateAssetRepair(
            @PathVariable Integer repairId,
            @RequestBody AssetRepair assetRepair
    ) {

        assetRepair.setRepairId(repairId);
        assetRepairService.updateAssetRepair(assetRepair);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/repair/{repairId}")
    public ResponseEntity<?> repairAsset(
            @PathVariable Long repairId,
            @RequestBody Map<String, Object> map
            ) {

        Double cost = Double.valueOf(map.get("cost").toString());

        assetRepairService.repairAsset(repairId, cost);
        return ResponseEntity.ok("维修成功");
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


    @PostMapping("/search")
    public ResponseEntity<List<AssetRepair>> searchAssetRepair(@RequestBody AssetRepair assetRepair) {
        return ResponseEntity.ok(assetRepairService.searchAssetRepair(assetRepair));
    }





    // 通过ID删除维修记录
    @DeleteMapping("/delete/{repairId}")
    public ResponseEntity<Void> deleteAssetRepairById(@PathVariable Long repairId) {
        if (assetRepairService.deleteAssetRepairById(repairId) > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
