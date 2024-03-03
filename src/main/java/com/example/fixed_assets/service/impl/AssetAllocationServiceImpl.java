package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetAllocationDao;
import com.example.fixed_assets.dao.FixedAssetDao;
import com.example.fixed_assets.entity.AssetAllocation;
import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.AssetAllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资产领用服务实现类
 */
@Service
@Transactional
public class AssetAllocationServiceImpl implements AssetAllocationService {

    final Logger logger = LoggerFactory.getLogger(AssetAllocationServiceImpl.class);


    @Autowired
    private AssetAllocationDao assetAllocationDao;

    @Autowired
    private FixedAssetDao fixedAssetDao;


    @Override
    public AssetAllocation getAssetAllocationById(Integer allocationId) {
        return assetAllocationDao.selectById(allocationId);
    }

    @Override
    public List<AssetAllocation> getAllAssetAllocations() {
        return assetAllocationDao.selectAll();
    }

    @Override
    @Transactional
    public void saveAssetAllocation(Map<String,Object> map) {

        int num = map.get("quantity") == null ? 0 : (int) map.get("quantity");
        String name = (String) map.get("name");
        String allocationDescription = (String) map.get("allocationDescription");



        String date = (String) map.get("returnDate");

        if (date == null || date.isEmpty()) {
            throw new RuntimeException("归还日期不能为空");
        }

        Instant time = Instant.parse(date);
        ZonedDateTime zonedDateTime = time.atZone(ZonedDateTime.now().getZone());
        Date returnDate = Date.from(zonedDateTime.toInstant());
        Date now = new Date();


        User user = (User) map.get("user");

        if (num <= 0) {
            throw new RuntimeException("领用数量必须大于0");
        }

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("资产名称不能为空");
        }



        if(user.getEmployee().getDepartment().getName() == null || user.getEmployee().getDepartment().getName().isEmpty()){
            throw new RuntimeException("部门不能为空");
        }

        if (allocationDescription == null || allocationDescription.isEmpty()) {
            throw new RuntimeException("领用描述不能为空");
        }
        logger.info("num: " + num);
        logger.info("name: " + name);
        logger.info("allocationDescription: " + allocationDescription);
        logger.info("returnDate: " + returnDate);
        logger.info("now: " + now);
        logger.info("user: " + user);
        logger.info("user.getEmployee().getDepartment().getName(): " + user.getEmployee().getDepartment().getName());




        // 获取num个name名称的资产
        List<FixedAsset> fixedAssets = fixedAssetDao.selectSomeByName(name,num);


        List<AssetAllocation> allocationList = new ArrayList<>();
        AssetAllocation tmp;

        // 将这些资产的状态改为“使用中”
        for (FixedAsset fixedAsset : fixedAssets) {
            fixedAsset.setStatus("使用中");
            fixedAssetDao.updateFixedAsset(fixedAsset);

            tmp = new AssetAllocation();
            tmp.setAssetId(fixedAsset.getAssetId());
            tmp.setUserId(user.getUserId());
            tmp.setDepartment(user.getEmployee().getDepartment().getName());
            tmp.setAllocationDate(now);
            tmp.setReturnDate(returnDate);
            tmp.setAllocationDescription(allocationDescription);
            allocationList.add(tmp);

        }

        // 将这些资产的领用信息插入到asset_allocation表中
        assetAllocationDao.insertAsList(allocationList);

    }

    @Override
    public void updateAssetAllocation(AssetAllocation assetAllocation) {
        assetAllocationDao.update(assetAllocation);
    }

    @Override
    public void deleteAssetAllocationById(Integer allocationId) {
        assetAllocationDao.deleteById(allocationId);
    }
}
