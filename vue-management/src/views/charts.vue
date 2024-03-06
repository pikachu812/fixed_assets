<template>
    <el-row gutter="20">
        <el-col :span="12">
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span>部门资产分布</span>
                    </div>
                </template>
                <div ref="departmentChart" style="width: 100%; height: 400px;"></div>
            </el-card>
        </el-col>
        <el-col :span="12">
            <el-card class="box-card">
                <template #header>
                    <div class="clearfix">
                        <span>不同类别资产数据</span>
                    </div>
                </template>
                <div ref="assetTypeChart" style="width: 100%; height: 400px;"></div>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';
import { ElMessage } from 'element-plus';
import service from "../utils/request";

const departmentChart = ref(null);
const assetTypeChart = ref(null);

const getDepartmentData = async () => {
    try {
        const res = await service.get("/statistics/assetByDepartment");
        updateDepartmentChart(res.data);
    } catch (err) {
        ElMessage.error(err.response.data);
    }
};

const getAssetTypeData = async () => {
    try {
        const res = await service.get("/statistics/assetByType");
        updateAssetTypeChart(res.data);
    } catch (err) {
        ElMessage.error(err.response.data);
    }
};

// 更新部门资产分布图表数据
const updateDepartmentChart = (data) => {
    if (!departmentChart.value) return;

    const myChart = echarts.init(departmentChart.value);
    myChart.setOption({
        title: {
            text: '部门资产分布',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                name: '资产数量',
                type: 'pie',
                radius: '50%',
                data: data.map(item => ({
                    value: item.asset_count,
                    name: item.department
                })),
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
};

// 更新资产类别图表数据
const updateAssetTypeChart = (data) => {
    if (!assetTypeChart.value) return;

    const myChart = echarts.init(assetTypeChart.value);
    myChart.setOption({
        title: {
            text: '不同类别资产数据',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        xAxis: {
            type: 'category',
            data: data.map(item => item.type),
            axisLabel: {
                rotate: -45, // 使X轴的标签斜着显示
                interval: 0, // 显示所有标签，不跳过
                textStyle: {
                    fontSize: 12 // 可以调整字体大小以确保标签的清晰度
                }
            }
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: data.map(item => item.count),
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        }]
    });
};

onMounted(() => {
    getDepartmentData(); // 调用函数获取部门资产数据并更新图表
    getAssetTypeData(); // 调用函数获取资产类型数据并更新图表
});
</script>
