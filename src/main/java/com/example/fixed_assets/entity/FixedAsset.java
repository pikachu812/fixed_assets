package com.example.fixed_assets.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 固定资产实体类
 */
@lombok.Data
public class FixedAsset {
    private Integer assetId;
    private Integer assetTypeId;
    private String name;
    private Date purchaseDate;
    private BigDecimal price;
    private String imgDir;
    private String status;

    private AssetType assetType;

}
