package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.FixedAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产管理控制器 <br><br>
 * @ RestController  注解表示该类的所有方法返回的数据都是直接写给浏览器（如果是对象转为json数据）
 *                   也就是说，这个类的所有方法都是直接返回数据，而不是返回视图<br><br>
 * @ RequestMapping 注解表示该类的所有方法都是以 /assets 开头的 <br><br>
 * @ Controller     注解表示该类是一个控制器，用于接收请求和返回视图，这个注解是SpringMVC的注解
 */
@RestController
@RequestMapping("/fixedAssets")
public class FixedAssetController {

    @Autowired
    private FixedAssetService fixedAssetService;

    @PostMapping
    public ResponseEntity<?> addFixedAsset(@RequestBody FixedAsset fixedAsset) {
        fixedAssetService.addFixedAsset(fixedAsset);
        return new ResponseEntity<>("Asset added successfully.", HttpStatus.CREATED);
    }

    @PutMapping("/{assetId}")
    public ResponseEntity<?> updateFixedAsset(@PathVariable Integer assetId, @RequestBody FixedAsset fixedAsset) {
        fixedAsset.setAssetId(assetId);
        fixedAssetService.modifyFixedAsset(fixedAsset);
        return new ResponseEntity<>("Asset updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/{assetId}")
    public ResponseEntity<?> deleteFixedAsset(@PathVariable Integer assetId) {
        fixedAssetService.removeFixedAsset(assetId);
        return new ResponseEntity<>("Asset deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<FixedAsset> getFixedAssetById(@PathVariable Integer assetId) {
        FixedAsset fixedAsset = fixedAssetService.getFixedAssetById(assetId);
        return new ResponseEntity<>(fixedAsset, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FixedAsset>> getAllFixedAssets() {
        List<FixedAsset> fixedAssets = fixedAssetService.getAllFixedAssets();
        return new ResponseEntity<>(fixedAssets, HttpStatus.OK);
    }

}
