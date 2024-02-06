package com.example.fixed_assets.entity;

import java.math.BigDecimal;
import java.util.Date;

@lombok.Data
public class Asset {
    private Integer id;
    private String name;
    private String category;
    private Date purchaseDate;
    private BigDecimal cost;
    private String status;
    private String department;
}
