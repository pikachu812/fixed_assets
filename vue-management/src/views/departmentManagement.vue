<template>
    <div>
        <div class="container">
            <div class="search-box">
                <el-input
                    v-model="query.name"
                    placeholder="部门名称"
                    class="search-input mr10"
                    clearable
                ></el-input>
                <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
                <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true"
                >新增
                </el-button
                >
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <!--        <el-table-column label="序号" type="index" align="center" width="60%" />-->

                <el-table-column
                    prop="departmentId"
                    label="部门编号"
                    width="100%"
                    align="center"
                >
                    <template #default="scope">
                        {{ formatDepartmentId(scope.row.departmentId) }}
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="部门名称" align="center"></el-table-column>
                <el-table-column prop="description" label="描述" align="center"></el-table-column>

                <el-table-column label="操作" width="280" align="center">
                    <template #default="scope">
                        <!-- <el-button type="warning" size="small" :icon="View" @click="handleView(scope.row)">
                                        查看
                                    </el-button> -->
                        <el-button
                            type="primary"
                            size="small"
                            :icon="Edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >
                            编辑
                        </el-button>
                        <el-button
                            type="danger"
                            size="small"
                            :icon="Delete"
                            @click="handleDelete(scope.$index, scope.row)"
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
            :title="idEdit ? '编辑部门' : '新增部门'"
            v-model="visible"
            width="500px"
            destroy-on-close
            :close-on-click-modal="false"
            @close="closeDialog"
        >
            <DepartmentEdit :data="rowData" :edit="idEdit" :update="updateData"/>
        </el-dialog>
        <!-- <el-dialog title="查看用户详情" v-model="visible1" width="700px" destroy-on-close>
                <UserTableDetail :data="rowData" />
            </el-dialog> -->
    </div>
</template>

<script setup lang="ts" name="basetable">
import {ref, reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit, Search, CirclePlusFilled, View} from "@element-plus/icons-vue";
import DepartmentEdit from "../components/departmentEdit.vue";
// import UserTableDetail from "../components/userTableDetail.vue";
import service from "../utils/request";
import {Department} from "../interface/interface";

interface TableItem extends Department {
}

const query = reactive({
    departmentId: null,
    name: null,
    description: null,
    offset: null,
    limit: null,
});

const allData = ref<TableItem[]>([]);
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref<TableItem>({
    departmentId: null,
    name: null,
    description: null,
});
const visible1 = ref(false);

const formatDepartmentId = (departmentId) => {
    return `BM${departmentId.toString().padStart(4, '0')}`;
};


// 获取表格数据
const getData = async () => {
    pageIndex.value = 1;
    service.post("/department/details", query).then((res) => {
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
    });
};

const doPagination = () => {
    const start = (pageIndex.value - 1) * pageSize;
    const end = start + pageSize;
    tableData.value = allData.value.slice(start, end); // 从所有数据中切割当前页的数据
};

const handlePageChange = (val: number) => {
    pageIndex.value = val; // 更新当前页码
    doPagination(); // 根据新的页码重新分页
};

getData();

// 查询操作
const handleSearch = () => {
    getData();
};

const handleDelete = async (index: number,row:TableItem) => {
    // 二次确认删除
    try {
        await ElMessageBox.confirm("确定要删除吗？", "提示", {type: "warning"});
        // 调用API删除部门
        const departmentId = row.departmentId;
        await service.delete("/department/delete/" + departmentId);
        ElMessage.success("删除成功");
        tableData.value.splice(index, 1); // 从本地数据中移除
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
    idEdit.value ? (tableData.value[idx] = row) : tableData.value.unshift(row);
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
