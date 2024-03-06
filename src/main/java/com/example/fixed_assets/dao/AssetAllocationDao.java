package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.AssetAllocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AssetAllocationDao {
    AssetAllocation selectById(@Param("allocationId") Integer allocationId);

    List<AssetAllocation> selectAll();

    List<AssetAllocation> selectByCondition(AssetAllocation assetAllocation);

    void insert(AssetAllocation assetAllocation);

    void update(AssetAllocation assetAllocation);

    void deleteById(Integer allocationId);


    void insertAsList(List<AssetAllocation> allocationList);

    List<AssetAllocation> selectByMap(Map<String, Object> map);

    void passAssetAllocation(Integer allocationId);

    void rejectAssetAllocation(@Param("allocationId") Integer allocationId,
                                @Param("reason") String reason);

    void returnAsset(AssetAllocation assetAllocation);
}
