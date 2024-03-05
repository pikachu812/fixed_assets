package com.example.fixed_assets.entity;

import java.util.Date;

@lombok.Data
public class AssetScrap {

    private Integer scrapId;
    private Integer assetId;
    private Date scrapDate;
    private String reason;
    private FixedAsset fixedAsset;

}
