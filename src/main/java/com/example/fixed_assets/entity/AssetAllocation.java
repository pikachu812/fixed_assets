package com.example.fixed_assets.entity;

import java.util.Date;
@lombok.Data

public class AssetAllocation {
    private Integer allocationId;
    private Integer assetId;
    private Integer userId;
    private String department;
    private Date allocationDate;
    private Date returnDate;
    private String status;
    private String reason;
    private String allocationDescription;

    private User user;
    private FixedAsset fixedAsset;

}
