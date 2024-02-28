package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetType;
import com.example.fixed_assets.service.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/assetType")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;

    @PostMapping("/add")
    public ResponseEntity<AssetType> addAssetType(@RequestBody AssetType assetType) {
        assetTypeService.addAssetType(assetType);
        return new ResponseEntity<>(assetType, HttpStatus.CREATED);
    }

    @PostMapping("/update/{assetTypeId}")
    public ResponseEntity<AssetType> updateAssetType(@PathVariable Integer assetTypeId, @RequestBody AssetType assetType) {
        assetType.setAssetTypeId(assetTypeId);
        assetTypeService.updateAssetType(assetType);
        return new ResponseEntity<>(assetType, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{assetTypeId}")
    public ResponseEntity<Void> deleteAssetType(@PathVariable Integer assetTypeId) {
        assetTypeService.deleteAssetType(assetTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get/{assetTypeId}")
    public ResponseEntity<AssetType> getAssetTypeById(@PathVariable Integer assetTypeId) {
        AssetType assetType = assetTypeService.getAssetTypeById(assetTypeId);
        if (assetType != null) {
            return new ResponseEntity<>(assetType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AssetType>> getAllAssetTypes() {
        List<AssetType> assetTypes = assetTypeService.getAllAssetTypes();
        return new ResponseEntity<>(assetTypes, HttpStatus.OK);
    }

    @PostMapping("/getAssetTypeByCondition")
    public ResponseEntity<List<AssetType>> getAssetTypeByCondition(@RequestBody AssetType assetType) {
        List<AssetType> assetTypes = assetTypeService.getAssetTypeByCondition(assetType);
        return new ResponseEntity<>(assetTypes, HttpStatus.OK);
    }

}
