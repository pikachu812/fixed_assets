package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.Asset;
import com.example.fixed_assets.entity.AssetStatistics;
import com.example.fixed_assets.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/assets")
public class AssetController {
    private final AssetService assetService;
    /**
     * AssetController的构造函数。
     * 它自动装配了AssetService。
     *
     * @param assetService 用于管理资产的服务
     */
    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    /**
     * 添加新资产的端点。
     * 它在请求体中接收资产数据。
     *
     * @param asset 要添加的资产
     * @return 已添加的资产
     */
    @PostMapping("/add")
    public ResponseEntity<Asset> addAsset(@RequestBody Asset asset) {
        assetService.addAsset(asset);
        return ResponseEntity.ok(asset);
    }

    /**
     * 列出所有资产的端点。
     *
     * @return 所有资产的列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<Asset>> listAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    /**
     * 通过其id获取资产的端点。
     *
     * @param id 要检索的资产的id
     * @return 具有给定id的资产
     */
    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAsset(@PathVariable Integer id) {
        Asset asset = assetService.getAssetById(id);
        return ResponseEntity.ok(asset);
    }

    /**
     * 更新资产的端点。
     * 它在请求体中接收更新的资产数据。
     *
     * @param asset 要更新的资产
     * @return 更新的资产
     */
    @PutMapping("/update")
    public ResponseEntity<Asset> updateAsset(@RequestBody Asset asset) {
        assetService.updateAsset(asset);
        return ResponseEntity.ok(asset);
    }

    /**
     * 通过其id删除资产的端点。
     *
     * @param id 要删除的资产的id
     * @return 无内容的响应实体
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Integer id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取资产统计信息的端点。
     *
     * @return 资产统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<AssetStatistics> assetStatistics() {
        AssetStatistics statistics = assetService.getAssetStatistics();
        return ResponseEntity.ok(statistics);
    }
}
