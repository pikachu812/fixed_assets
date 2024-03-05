<template>
    <div>
        <div class="container">
            <div class="search-box">
                <el-input
                    v-model="query.name"
                    placeholder="资产名称"
                    class="search-input mr10"
                    clearable
                ></el-input>
                <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
                <!--下拉选择-->

                <el-dropdown split-button type="warning" @command="handleDepreciationMethodChange"
                             style=" margin-left: 40px">
                    固定资产折旧方法
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="straightLine">直线法</el-dropdown-item>
                            <el-dropdown-item command="decliningBalance">双倍余额递减法</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>

            </div>

            <el-table
                :data="tableData"
                border
                fit
                class="table"
                ref="multipleTable"
            >
                <el-table-column
                    prop="assetId"
                    label="资产编号"
                    align="center"
                    width="100"
                ></el-table-column>
                <el-table-column
                    prop="name"
                    label="资产名称"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="purchaseDate"
                    :formatter="formatDate"
                    label="购置日期"
                    align="center"
                ></el-table-column>
                <el-table-column
                    prop="usefulYear"
                    label="使用年限"
                    align="center"
                ></el-table-column>
                <el-table-column
                    sortable
                    prop="price"
                    label="价格￥"
                    :formatter="priceFormatter"
                    align="center"
                ></el-table-column>
                <el-table-column
                    sortable
                    prop="currentDepreciation"
                    label="当期折旧金额"
                    :formatter="priceFormatter"
                    align="center"
                ></el-table-column>
                <el-table-column
                    sortable
                    prop="accumulatedDepreciation"
                    label="累计折旧"
                    :formatter="priceFormatter"

                    align="center"
                ></el-table-column>
                <el-table-column
                    sortable
                    prop="netBookValue"
                    label="固定资产折旧净值"
                    :formatter="priceFormatter"
                    align="center"
                ></el-table-column>

            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="pageIndex"
                    :page-size="pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import {ref, reactive} from "vue";
import {
    ElMessage,
    ElMessageBox,
} from "element-plus";
import {Search, Edit, Delete} from "@element-plus/icons-vue";
import service from "../utils/request";
import {FixedAsset} from "../interface/interface";

interface TableItem extends FixedAsset {
    currentDepreciation: number;
    accumulatedDepreciation: number;
    netBookValue: number;
}

const query = reactive({
    name: '' // 搜索查询对象
});

const tableData = ref<TableItem[]>([]);

const pageIndex = ref(1);
const pageSize = 10;
const pageTotal = ref(tableData.value.length);
const visible = ref(false);
const idEdit = ref(false);
const rowData = ref<TableItem | null>(null);

// 价格格式化
const priceFormatter = (row, column, cellValue, index) => {
    return `￥${cellValue.toFixed(2)}`;
}

const formatDate = (row, column, cellValue, index) => {
    // 假设cellValue是一个标准的日期字符串或者Date对象
    // 你可以根据需要调整日期格式
    const date = new Date(cellValue);
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}

// 获取表格数据
const getData = async () => {
    service.post("/fixedAssets/getFixedAssetsByCondition", query).then((res) => {
        console.log("res", res);
        console.log(res);

        //遍历res.data
        for (let i = 0; i < res.data.length; i++) {
            const item = res.data[i];
            const depreciation = calculateDepreciation(item, 'straightLine');
            item.currentDepreciation = depreciation.currentDepreciation;
            item.accumulatedDepreciation = depreciation.accumulatedDepreciation;
            item.netBookValue = depreciation.netBookValue;
        }

        tableData.value = res.data;
        pageTotal.value = res.data.length;
        pageIndex.value = 1;
    });
};
getData();

// 计算折旧
const calculateDepreciation = (item, method) => {
    const purchaseYear = new Date(item.purchaseDate).getFullYear();
    const currentYear = new Date().getFullYear();
    const yearsElapsed = currentYear - purchaseYear; // 已过去的年份
    let depreciationExpense = 0; // 当期折旧金额
    let accumulatedDepreciation = 0; // 累计折旧
    let netBookValue = item.price; // 初始净账面价值设为原价

    if (method === 'straightLine') {
        // 直线法计算
        depreciationExpense = (item.price / item.usefulYear);
        accumulatedDepreciation = (depreciationExpense * Math.min(yearsElapsed, item.usefulYear));
    } else if (method === 'decliningBalance') {
        // 双倍余额递减法计算
        for (let year = 1; year <= yearsElapsed && year <= item.usefulYear; year++) {
            if (year === 1) {
                depreciationExpense = ((item.price * 2) / item.usefulYear);
            } else {
                let tempBookValue = item.price - accumulatedDepreciation; // 临时计算当前账面价值
                depreciationExpense = ((tempBookValue * 2) / item.usefulYear);
            }
            accumulatedDepreciation = (+accumulatedDepreciation + +depreciationExpense); // 更新累计折旧
            if (+accumulatedDepreciation >= item.price) {
                accumulatedDepreciation = item.price; // 确保累计折旧不超过原价
                break;
            }
        }
    }

    netBookValue = (item.price - accumulatedDepreciation); // 计算净账面价值
    // 确保净账面价值不会变成负数
    netBookValue = Math.max(0, netBookValue);


    return {
        currentDepreciation: depreciationExpense,
        accumulatedDepreciation: accumulatedDepreciation,
        netBookValue: netBookValue,
    };
};


const handleDepreciationMethodChange = (method: string) => {
    tableData.value.forEach((item, index) => {
        const depreciation = calculateDepreciation(item, method);
        tableData.value[index].currentDepreciation = depreciation.currentDepreciation;
        tableData.value[index].accumulatedDepreciation = depreciation.accumulatedDepreciation;
        tableData.value[index].netBookValue = depreciation.netBookValue;
    });
};

const handleSearch = () => {
    // 实际应用中应发送请求到后端进行搜索
    getData()
};

const handlePageChange = (val: number) => {
    // 实际应用中应根据分页参数重新获取数据
    pageIndex.value = val;
};

const handleDelete = async (index: number) => {
    try {
        await ElMessageBox.confirm("确定要删除这个资产吗？", "警告", {
            type: "warning",
        });
        // 这里替换为实际的后端请求代码
        console.log('删除资产', tableData.value[index].assetId);
        ElMessage.success("删除成功");
        tableData.value.splice(index, 1);
        pageTotal.value -= 1;
    } catch (error) {
        if (error !== 'cancel') {
            ElMessage.error("删除失败");
        }
    }
};

</script>


<style scoped>

.search-box {
    display: flex;
    align-items: center; /* 确保所有元素垂直居中 */
    margin-bottom: 20px; /* 与下面内容的间隔 */
}

.search-input {
    width: 200px; /* 输入框的宽度，可根据需要调整 */
    margin-right: 10px; /* 输入框和搜索按钮之间的间隔 */
}


/* 调整分页器样式 */
.pagination {
    margin-top: 20px; /* 与表格的间隔 */
}

</style>

