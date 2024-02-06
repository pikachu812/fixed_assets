package com.example.fixed_assets.entity;

import java.math.BigDecimal;
import java.util.Date;

@lombok.Data
public class FixedAsset {
    private Integer assetId;
    private Integer assetTypeId;
    private String name;
    private Date purchaseDate;
    private BigDecimal price;
    private String status;

    public FixedAsset(Integer assetId, Integer assetTypeId, String name, Date purchaseDate, BigDecimal price, String status) {
        this.assetId = assetId;
        this.assetTypeId = assetTypeId;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.status = status;
    }
}
