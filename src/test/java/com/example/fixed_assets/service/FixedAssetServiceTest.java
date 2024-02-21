package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.FixedAsset;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FixedAssetServiceTest {

    private  static final Logger logger = LoggerFactory.getLogger(FixedAssetServiceTest.class);

    @Autowired
    private FixedAssetService fixedAssetService;

    private FixedAsset testFixedAsset;

    @Before
    public void setUp() {

        logger.info(() -> "setUp");

        testFixedAsset = new FixedAsset();
        testFixedAsset.setAssetId(1);
        testFixedAsset.setName("test");
        testFixedAsset.setAssetTypeId(1);
        testFixedAsset.setPrice(BigDecimal.valueOf(1000));

        // 今天日期
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        testFixedAsset.setPurchaseDate(date);
        testFixedAsset.setStatus("normal");

    }

    @Test
    public void test() {
        addFixedAsset();
        getAllFixedAssets();
        modifyFixedAsset();
        getAllFixedAssets();
        removeFixedAsset();
        getAllFixedAssets();
        getFixedAssetById();
    }

    public void addFixedAsset() {
        logger.info(() -> "------------------>addFixedAsset");
        fixedAssetService.addFixedAsset(testFixedAsset);
        // 添加断言或其他验证逻辑
        logger.info(() -> "assetId: " + testFixedAsset.getAssetId());
    }

    public void modifyFixedAsset() {
        logger.info(() -> "------------------>modifyFixedAsset");
        testFixedAsset.setName("test2");
        fixedAssetService.modifyFixedAsset(testFixedAsset);
        // 添加断言或其他验证逻辑
        logger.info(() -> "assetId: " + testFixedAsset.getAssetId());
    }

    public void removeFixedAsset() {

        logger.info(() -> "------------------>removeFixedAsset");
        fixedAssetService.removeFixedAsset(testFixedAsset.getAssetId());

    }

    public void getFixedAssetById() {
        logger.info(() -> "------------------>getFixedAssetById");
        FixedAsset fixedAsset = fixedAssetService.getFixedAssetById(testFixedAsset.getAssetId());
        // 添加断言或其他验证逻辑
        logger.info(() -> "fixedAsset: " + fixedAsset);
    }

    public void getAllFixedAssets() {
        logger.info(() -> "------------------>getAllFixedAssets");
        List<FixedAsset> fixedAssets = fixedAssetService.getAllFixedAssets();
        // 添加断言或其他验证逻辑
        logger.info(() -> "fixedAssets: " + fixedAssets);
    }
}
