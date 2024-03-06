package com.example.fixed_assets.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AssetInventory {

    private Integer inventoryId;

    private Date inventoryDate;

    private Integer departmentId;

    private Integer bookQuantity;

    private BigDecimal bookValue;

    private String inventoryName;

    private Department department;

}
