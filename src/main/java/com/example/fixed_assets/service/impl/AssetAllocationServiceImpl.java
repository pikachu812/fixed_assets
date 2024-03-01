package com.example.fixed_assets.service.impl;

import com.example.fixed_assets.dao.AssetAllocationDao;
import com.example.fixed_assets.entity.AssetAllocation;
import com.example.fixed_assets.service.AssetAllocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资产领用服务实现类
 */
@Service
@Transactional
public class AssetAllocationServiceImpl implements AssetAllocationService {

    private final AssetAllocationDao assetAllocationMapper;

    public AssetAllocationServiceImpl(AssetAllocationDao assetAllocationMapper) {
        this.assetAllocationMapper = assetAllocationMapper;
    }

    @Override
    public AssetAllocation getAssetAllocationById(Integer allocationId) {
        return assetAllocationMapper.selectById(allocationId);
    }

    @Override
    public List<AssetAllocation> getAllAssetAllocations() {
        return assetAllocationMapper.selectAll();
    }

    @Override
    public void saveAssetAllocation(AssetAllocation assetAllocation) {
        assetAllocationMapper.insert(assetAllocation);
    }

    @Override
    public void updateAssetAllocation(AssetAllocation assetAllocation) {
        assetAllocationMapper.update(assetAllocation);
    }

    @Override
    public void deleteAssetAllocationById(Integer allocationId) {
        assetAllocationMapper.deleteById(allocationId);
    }
}
