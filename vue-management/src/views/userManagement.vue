x
<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input v-model="query.username" placeholder="用户名" class="search-input mr10" clearable></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">新增</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="userId" label="ID" width="100%" align="center">
            <template #default="scope">
                {{ formatUserId(scope.row.userId) }}
            </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" align="center"></el-table-column>
        <el-table-column label="用户角色" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.role.roleName == 'admin' ? 'success' : 'danger'">
              {{ scope.row.role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template #default="scope">
            <el-button type="warning" size="small" :icon="View" @click="handleView(scope.row)">
              查看
            </el-button>
            <el-button
                type="primary"
                size="small"
                :icon="Edit"
                @click="handleEdit(scope.$index, scope.row)">
              编辑
            </el-button>
            <el-button
                type="danger"
                size="small"
                :icon="Delete"
                @click="handleDelete(scope.$index)">
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
        :title="idEdit ? '编辑用户' : '新增用户'"
        v-model="visible"
        width="500px"
        destroy-on-close
        :close-on-click-modal="false"
        @close="closeDialog"
    >
      <UserTableEdit :data="rowData" :edit="idEdit" :update="updateData"/>
    </el-dialog>
    <el-dialog title="查看用户详情" v-model="visible1" width="700px" destroy-on-close>
      <UserTableDetail :data="rowData"/>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="basetable">
import {ref, reactive} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {Delete, Edit, Search, CirclePlusFilled, View} from '@element-plus/icons-vue';
import UserTableEdit from "../components/userTableEdit.vue";
import UserTableDetail from "../components/userTableDetail.vue";
import service from "../utils/request";
import {User} from "../interface/interface";

interface TableItem extends User{
  userId: number;
  username: string;
  password: string;
  role: object;
  permissions: object;
}


const query = reactive({
  userId: null,
  username: null,
  roleName: null,
  permissionName: null,
  offset: null,
  limit: null,
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({
  password: null,
});
const visible1 = ref(false);

const formatUserId = (userId) => {
    return `${userId.toString().padStart(6, '0')}`;
};

// 获取表格数据
const getData = async () => {

  service.post('/user/details', query).then((res) => {

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

// 删除操作
// const handleDelete = (index: number) => {
// 	// 二次确认删除
// 	ElMessageBox.confirm('确定要删除吗？', '提示', {
// 		type: 'warning'
// 	})
// 		.then(() => {
// 			ElMessage.success('删除成功');
// 			tableData.value.splice(index, 1);
// 		})
// 		.catch(() => {});
// };

const handleDelete = async (index: number) => {
  // 二次确认删除
  try {
    await ElMessageBox.confirm('确定要删除吗？', '提示', {type: 'warning'});
    // 调用API删除用户
    const userId = tableData.value[index].userId;
    await service.delete('/user/delete/' + userId);
    ElMessage.success('删除成功');
    tableData.value.splice(index, 1); // 从本地数据中移除
  } catch (error) {
    // 处理取消删除或API调用错误
    if (error !== 'cancel') {
      ElMessage.error('删除失败');
    }
  }
};


const handleEdit = (index: number, row: TableItem) => {
  // 当处理编辑操作时，会将当前行的数据传递给子组件
  idx = index;
  rowData.value = row;
  rowData.value.password = '';
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
