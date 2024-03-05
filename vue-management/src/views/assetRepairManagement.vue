<template>
    <div>
        <div class="container">
            <div class="search-box">
                <el-input
                    v-model="query"
                    placeholder="请输入资产名称或类型"
                    class="search-input mr10"
                    clearable
                ></el-input>
                <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
                <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true"
                >新增
                </el-button
                >
            </div>


            <!--      表格部分 -->
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <!--        <el-table-column label="序号" type="index" align="center" width="60%"/>-->

                <el-table-column
                    prop="assetId"
                    label="资产编号"
                    width="100%"
                    align="center"
                >
                    <template #default="scope">
                        {{ formatAssetId(scope.row.fixedAsset.assetId) }}
                    </template>
                </el-table-column>
                <el-table-column prop="fixedAsset.name" label="资产名称" align="center" width="160%"></el-table-column>
                <el-table-column prop="repairDate" :formatter="formatDate"
                                 label="维修日期" align="center" width="120%"></el-table-column>

                <el-table-column label="资产图片" align="center" width="120%">
                    <template #default="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row?.fixedAsset.imgDir"
                            :z-index="10"
                            :preview-src-list="[scope.row.fixedAsset.imgDir]"
                            preview-teleported
                        >
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="cost" label="维修费用￥" align="center" width="100%"></el-table-column>
                <el-table-column prop="details" label="维修状态" align="center" width="160%">
                    <template #default="scope">
                        <el-tag :type="scope.row.status === '待维修' ? 'danger' : 'success'">
                            {{ scope.row.status }}
                        </el-tag>
                    </template>

                </el-table-column>
                <el-table-column prop="details" label="损坏原因" align="center" width="160%"></el-table-column>

                <el-table-column label="操作" align="center">
                    <template #default="scope">
                        <el-button
                            v-if="scope.row.status === '待维修'"
                            type="success"
                            size="small"
                            :icon="WarnTriangleFilled"
                            @click="handleRepair(scope.row)">
                          维修
                        </el-button>
                        <el-button
                            type="primary"
                            size="small"
                            :icon="Edit"
                            @click="handleEdit(scope.$index, scope.row)"
                            v-permiss="16"
                        >
                            编辑
                        </el-button>
                        <el-button
                            type="danger"
                            size="small"
                            :icon="Delete"
                            @click="handleDelete(scope.$index)"
                            v-permiss="16"
                        >
                            删除
                        </el-button>
                    </template>
                </el-table-column>
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
        <el-dialog
            :title="idEdit ? '编辑资产' : '新增维修记录'"
            v-model="visible"
            width="70%"
            destroy-on-close
            :close-on-click-modal="false"
            @close="closeDialog"
        >
            <AssetRepairEdit :data="rowData" :edit="idEdit" :update="updateData"/>
        </el-dialog>
        <!-- <el-dialog title="查看用户详情" v-model="visible1" width="700px" destroy-on-close>
                <UserTableDetail :data="rowData" />
            </el-dialog> -->
    </div>
</template>

<script setup lang="ts" name="basetable">
import {ref, reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit, Search, CirclePlusFilled, WarnTriangleFilled} from "@element-plus/icons-vue";
import service from "../utils/request";
import AssetRepairEdit from "../components/assetRepairEdit.vue";
import {AssetRepair} from "../interface/interface";

// 如果 TableItem 仅用于展示 AssetRepair 数据，则可以直接使用 AssetRepair 接口
// 否则，如果 TableItem 是一个更通用的接口，它可以继承或包含 AssetRepair
interface TableItem extends AssetRepair {
}

const statusToCss = {
    "闲置": "success",
    "维修中": "warning",
    "使用中": "info",
    "报废": "danger"
}

const query = ref('');
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref<TableItem>({
    repairId: null,
    assetId: null,
    repairDate: null,
    cost: null,
    status: null,
    details: null,
    fixedAsset: {
        assetId: null,
        assetTypeId: null,
        name: "",
        purchaseDate: null,
        price: 0,
        imgDir: "",
        status: "",
        assetType: {
            assetTypeId: 0,
            typeName: "",
            description: ""
        }
    }

});
const visible1 = ref(false);

const formatDate = (row, column, cellValue, index) => {
    // 假设cellValue是一个标准的日期字符串或者Date对象
    // 你可以根据需要调整日期格式
    const date = new Date(cellValue);
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}

const formatAssetId = (assetId) => {
    return `ZC${assetId.toString().padStart(6, '0')}`;
};


// 获取表格数据
const getData = async () => {
    service.post("/assetRepair/search", {
        fixedAsset: {
            name: query.value,
            assetType: {
                typeName: query.value
            }
        }
    }).then((res) => {
        console.log("res", res);
        console.log(res);
        tableData.value = res.data;
        pageTotal.value = res.data.length;
        pageIndex.value = 1;
    });
};
getData();

// 查询操作
const handleSearch = () => {
    getData();
};
// 分页导航
const handlePageChange = (val: number) => {
    getData();
};

const handleRepair = (row: TableItem) => {
    console.log(row);
    ElMessageBox.confirm("确定维修完成了吗？", "提示", {type: "warning"}).then(() => {
        service.post("/assetRepair/repair/"+row.repairId, {

        }).then((res) => {
            ElMessage.success("维修成功");
            getData();
        }).catch((err) => {
            ElMessage.error("维修失败");
        });
    }).catch(() => {
        ElMessage.info("取消维修");
    });
};

const handleDelete = async (index: number) => {
    // 二次确认删除
    try {
        await ElMessageBox.confirm("确定要删除吗？", "提示", {type: "warning"});
        // 调用API删除部门
        const repairId = tableData.value[index].repairId;
        await service.delete("/assetRepair/delete/" + repairId).then(
            (res) => {
                ElMessage.success("删除成功");
                getData();
            }
        ).catch((err) => {
            ElMessage.error("删除失败");
        });

    } catch (error) {
        // 处理取消删除或API调用错误
        if (error !== "cancel") {
            ElMessage.error("删除失败");
        }
    }
};

const handleEdit = (index: number, row: TableItem) => {
    // 当处理编辑操作时，会将当前行的数据传递给子组件
    idx = index;
    rowData.value = row;
    idEdit.value = true;
    visible.value = true;
};
const updateData = (row: TableItem) => {
    console.log(tableData.value);
    closeDialog();
};

const closeDialog = () => {
    visible.value = false;
    idEdit.value = false;
    getData();
};

const handleView = (row: TableItem) => {
    rowData.value = row;
    visible1.value = true;
};
</script>

<style scoped>
.search-box {
    margin-bottom: 20px;
}

.search-input {
    width: 200px;
}

.mr10 {
    margin-right: 10px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
