package com.example.fixed_assets.service;

import com.example.fixed_assets.entity.AssetType;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetTypeServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(AssetTypeServiceTest.class);

    @Autowired
    private AssetTypeService assetTypeService;

    private AssetType testAssetType;

    @Before
    public void setUp() {
        logger.info(() -> "Setting up testAssetType");

        testAssetType = new AssetType();
        testAssetType.setTypeName("Vehicle");
        testAssetType.setDescription("Description of Vehicle");
    }

    @Test
    public void testAssetTypeOperations() {
        addAssetType();
        getAllAssetTypes();
        modifyAssetType();
        getAssetTypeById();
        removeAssetType();
    }

    private void addAssetType() {
        logger.info(() -> "Adding AssetType");
        assetTypeService.addAssetType(testAssetType);
        logger.info(() -> "Added AssetType: " + testAssetType.getAssetTypeId());
    }

    private void modifyAssetType() {
        logger.info(() -> "Modifying AssetType");
        testAssetType.setTypeName("Updated Name");
        assetTypeService.updateAssetType(testAssetType);
        logger.info(() -> "Modified AssetType: " + testAssetType.getAssetTypeId());
    }

    private void removeAssetType() {
        logger.info(() -> "Removing AssetType");
        assetTypeService.deleteAssetType(testAssetType.getAssetTypeId());
        logger.info(() -> "Removed AssetType: " + testAssetType.getAssetTypeId());
    }

    private void getAssetTypeById() {
        logger.info(() -> "Getting AssetType by ID");
        AssetType assetType = assetTypeService.getAssetTypeById(testAssetType.getAssetTypeId());
        logger.info(() -> "Retrieved AssetType: " + assetType);
    }

    private void getAllAssetTypes() {
        logger.info(() -> "Getting all AssetTypes");
        List<AssetType> assetTypes = assetTypeService.getAllAssetTypes();
        logger.info(() -> "Retrieved AssetTypes: " + assetTypes.size());
    }
}
