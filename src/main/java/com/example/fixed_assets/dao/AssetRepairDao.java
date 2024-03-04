package com.example.fixed_assets.dao;

import com.example.fixed_assets.entity.AssetRepair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface AssetRepairDao {

    // 添加维修记录
    int insertAssetRepair(AssetRepair assetRepair);

    // 根据维修ID查询维修记录
    AssetRepair selectAssetRepairById(@Param("repairId") Long repairId);

    // 查询所有维修记录
    List<AssetRepair> selectAllAssetRepairs();

    // 更新维修记录
    int updateAssetRepair(AssetRepair assetRepair);

    // 根据维修ID删除维修记录
    int deleteAssetRepairById(@Param("repairId") Long repairId);

    List<AssetRepair> searchAssetRepair(AssetRepair assetRepair);
}
