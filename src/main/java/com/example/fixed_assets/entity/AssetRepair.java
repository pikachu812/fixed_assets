package com.example.fixed_assets.entity;

import java.util.Date;

@lombok.Data
public class AssetRepair {

    private Integer repairId;
    private Integer assetId;
    private Date repairDate;
    private Double cost;
    private String details;

}
