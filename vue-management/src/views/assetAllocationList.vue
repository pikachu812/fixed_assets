<template>
    <div>
        <div class="container">
            <div class="search-box">
                <el-input v-model="query" placeholder="搜索记录..." class="search-input mr10" clearable></el-input>
                <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
                      style="width: 100%">
                <el-table-column label="序号" align="center" width="60">
                    <template #default="scope">
                        {{ (pageIndex - 1) * pageSize + scope.$index + 1 }}
                    </template>
                </el-table-column>
                <el-table-column prop="fixedAsset.name" label="资产名称" align="center" width="120"></el-table-column>
                <el-table-column prop="user.employee.name" label="员工姓名" align="center"
                                 width="100"></el-table-column>
                <el-table-column prop="user.employee.department.name" label="部门名称" align="center"
                                 width="100"></el-table-column>
                <el-table-column
                    :filters="[
                        { text: '待审核', value: '待审核' },
                        { text: '审核通过', value: '审核通过' },
                        { text: '审核不通过', value: '审核不通过' },
                        { text: '已归还', value: '已归还' }
                    ]"
                    :filter-method="filterStatus"
                    label="状态"
                    align="center"
                    width="120">
                    <template #default="scope">
                        <el-tag @click="detail(scope.row)"
                                :type="statusToCss[scope.row.status] ? statusToCss[scope.row.status] : 'danger'">
                            {{ scope.row.status }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column sortable prop="allocationDate" :formatter="formatDate" label="领用日期" align="center"
                                 width="90"></el-table-column>
                <el-table-column sortable prop="returnDate" :formatter="formatDate" label="归还日期" align="center"
                                 width="90"></el-table-column>
                <!-- 增加的宽度设置 -->
                <el-table-column prop="allocationDescription" label="申请理由" width="300px"
                                 align="center"></el-table-column>
                <el-table-column label="操作" align="center" width="200%">
                    <template #default="scope">
                        <el-button v-if="scope.row.status === '审核通过' && scope.row.fixedAsset.status === '使用中'"
                                   type="primary" size="small" :icon="Finished"
                                   @click="giveBack(scope.$index, scope.row)">
                            归还
                        </el-button>
                        <el-button v-if="scope.row.status === '审核通过' && scope.row.fixedAsset.status === '使用中'"
                                   type="warning" size="small" :icon="Edit"
                                   @click="handleRepair(scope.$index,scope.row)">
                            报修
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>

            <br><br><br><br><br><br><br><br><br><br>

            <div class="pagination">
                <el-pagination background layout="total, prev, pager, next" :current-page="pageIndex"
                               :page-size="pageSize" :total="pageTotal"
                               @current-change="handlePageChange"></el-pagination>
            </div>
        </div>
        <el-dialog title="新增维修记录" v-model="visible" width="70%" destroy-on-close :close-on-click-modal="false"
                   @close="closeDialog">
            <AssetRepairEdit :data="repairData" :edit="idEdit" :update="updateData"/>
        </el-dialog>
        <!-- 新增的状态时间轴弹窗 -->
        <el-dialog title="审核状态" v-model="timelineVisible" width="500">
            <div style="display: flex; justify-content: center; align-items: center; height: 400px; ">
                <el-steps direction="vertical" process-status="success" :active="activeStep">
                    <el-step
                        v-for="(step, index) in stepList"
                        :key="index"
                        :status = "statusList[activeStep] === step.title ?  (colorList[step.title] ?? ''): ''"
                        :title="step.title"
                        :icon="step.icon"
                    />
                </el-steps>
            </div>

        </el-dialog>
    </div>
</template>

<script setup lang="ts" name="basetable">
import {ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Edit, Search, Finished} from "@element-plus/icons-vue";
import service from "../utils/request";
import {AssetAllocation, AssetRepair} from "../interface/interface";
import AssetRepairEdit from "../components/assetRepairEdit.vue";

interface TableItem extends AssetAllocation {}

interface RepairItem extends AssetRepair {}

const statusToCss = {
    "审核通过": "success",
    "已归还": "primary",
    "待审核": "warning",
    "审核不通过": "danger"
}
const allData = ref<TableItem[]>([]);
const query = ref('');
const reason = ref('');
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const repairData = ref<RepairItem>({
    repairId: null,
    assetId: null,
    repairDate: null,
    cost: null,
    status: null,
    details: null,
    fixedAsset: {
        assetId: null,
        assetTypeId: null,
        name: null,
        purchaseDate: null,
        price: null,
        imgDir: null,
        status: null,
        usefulYear: null,
        assetType: {
            assetTypeId: null,
            typeName: null,
            description: null,
        },
    },
});
const rowData = ref<TableItem>({
    allocationId: null,
    allocationDescription: null,
    status: null,
    reason: null,
    user: {
        userId: null,
        username: null,
        roleId: null,
        employeeId: null,
        employee: {
            employeeId: null,
            name: null,
            departmentId: null,
            department: {
                departmentId: null,
                name: null,
                description: null,
            },
        },
    },
    fixedAsset: {
        assetId: null,
        assetTypeId: null,
        name: null,
        purchaseDate: null,
        price: null,
        imgDir: null,
        status: null,
        usefulYear: null,
        assetType: {
            assetTypeId: null,
            typeName: null,
            description: null,
        },
    },
    allocationDate: null,
    returnDate: null,
});
// 新增的状态时间轴弹窗的可见性
const timelineVisible = ref(false);
const stepList = ref([]);
const statusList = ref([]);
const colorList = {
    '审核不通过': 'error',
    '审核通过': 'success',
}

const getStatusList = (status) => {
    if (status === "待审核") {
        return ["已提交到后台","待审核", "审核状态", "已归还"];
    } else if (status === "审核不通过") {
        return ["已提交到后台","待审核", "审核不通过"];
    }else{
        return ["已提交到后台","待审核", "审核通过", "已归还"];
    }
};

const getStepList = (status) => {
    if (status === "待审核") {
        return [
            {
                title: '已提交到后台',
                content: '已提交到后台',
                icon: 'Upload',
            },
            {
                title: '待审核',
                content: '待审核',
                icon: 'Loading',
            },
            {
                title: '审核状态',
                content: '审核状态',
                icon: 'More',
            },
            {
                title: '已归还',
                content: '已归还',
                icon: 'Finished',
            }
        ];
    } else if (status === "审核不通过") {
        return [
            {
                title: '已提交到后台',
                content: '已提交到后台',
                icon: 'Upload',
            },
            {
                title: '待审核',
                content: '待审核',
                icon: 'Loading',
            },
            {
                title: '审核不通过',
                content: '审核不通过',
                icon: 'Close',
            },
        ];
    }else{
        return [
            {
                title: '已提交到后台',
                content: '已提交到后台',
                icon: 'Upload',
            },
            {
                title: '待审核',
                content: '待审核',
                icon: 'Loading'
            },
            {
                title: '审核通过',
                content: '审核通过',
                icon: 'Check'
            },
            {
                title: '已归还',
                content: '已归还',
                icon: 'Finished'
            }
        ];

    }
}
const activeStep = ref(0); // 你可以根据实际情况来设置活动的步骤



const filterStatus = (value: string, row: TableItem) => {
    return row.status === value;
};

const formatDate = (row, column, cellValue, index) => {
    // 假设cellValue是一个标准的日期字符串或者Date对象
    // 你可以根据需要调整日期格式
    if (cellValue === null) return "";

    const date = new Date(cellValue);
    return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}


// 获取表格数据
const getData = async () => {

    service.post("/assetAllocation/myAllocation", {
        assetName: query.value,
        employeeName: query.value,
        departmentName: query.value,
        allocationDescription: query.value,
    }).then((res) => {    //邱秋3/2改的，不知道对不对
        console.log(res);

        // //res.data中的数据复制100次，模拟数据量大的情况
        //   let data = res.data;
        //   for (let i = 0; i < 100; i++) {
        //       data = data.concat(res.data);
        //   }
        //   res.data = data;

        allData.value = res.data; // 假设后端返回的全部数据在data字段中
        pageTotal.value = res.data.length; // 设置总数据量
        doPagination(); // 调用分页函数
    }).catch((err) => {
        ElMessage.error(err.response.data);
    });
};
getData();

const doPagination = () => {
    const start = (pageIndex.value - 1) * pageSize;
    const end = start + pageSize;
    tableData.value = allData.value.slice(start, end); // 从所有数据中切割当前页的数据
};

// 查询操作
const handleSearch = () => {
    getData();
};
// 分页导航
const handlePageChange = (val: number) => {
    pageIndex.value = val; // 更新当前页码
    doPagination(); // 根据新的页码重新分页
};

const giveBack = (index: number, row: TableItem) => {
    ElMessageBox.confirm("确定要归还吗？", "提示", {type: "warning"}).then(() => {

        service.post("/assetAllocation/return/" + row.allocationId, {}).then((res) => {    //邱秋3/2改的，不知道对不对
            ElMessage.success("归还成功");
            getData();
        }).catch(() => {
            ElMessage.error("归还失败");
        });

    }).catch(() => {
        ElMessage.error("取消");
    });
};

const handleRepair = (index: number, row: TableItem) => {
    idx = index;
    visible.value = true;
    idEdit.value = true;

    repairData.value = {
        repairId: null,
        assetId: row.fixedAsset.assetId,
        repairDate: null,
        cost: null,
        status: null,
        details: null,
        fixedAsset: row.fixedAsset
    };

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


const detail = (row: TableItem) => {

    console.log(row);

    timelineVisible.value = true;

    // 共五个状态，待审核，审核通过，审核不通过，审核状态，已归还
    // 分为以下几种情况：待审核->审核状态
    // 待审核->审核不通过
    // 待审核->审核通过->已归还


    row.status = "已归还" //测试用，实际应该是从后端获取的

    statusList.value = getStatusList(row.status);
    stepList.value = getStepList(row.status);


    //根据row.status来设置activeStep的值
    activeStep.value = statusList.value.indexOf(row.status);

    // if (row.status === "审核不通过") {
    //     //弹窗显示不通过理由
    //     ElMessageBox.alert(row.reason, "不通过理由", {
    //         confirmButtonText: "确定",
    //         type: "warning",
    //     });
    // }
};


</script>

<style scoped>
.container {
    /*height:100%;*/
    background-image: url('../assets/img/f1_bg.png');
    background-size: cover; /* 覆盖整个容器 */
    background-position: center; /* 图片居中显示 */
}

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
