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
        <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true"
          >新增</el-button
        >
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
      >
        <el-table-column label="序号" type="index" align="center" width="60%" />

        <el-table-column
          prop="assetId"
          label="资产编号"
          width="100%"
          align="center"
        ></el-table-column>
        <el-table-column prop="name" label="资产名称" align="center" width="200"></el-table-column>
        <el-table-column prop="purchaseDate" :formatter="formatDate"
                         label="购置日期" align="center" width="200"></el-table-column>
        <el-table-column prop="price" label="价格" align="center" width="160"></el-table-column>

        <el-table-column label="资产图片" align="center">
          <template #default="scope">
            <el-image
                class="table-td-thumb"
                :src="scope.row.imgDir"
                :z-index="10"
                :preview-src-list="[scope.row.imgDir]"
                preview-teleported
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="资产状态" align="center">
          <template #default="scope">
            <el-tag :type="statusToCss[scope.row.status] ? statusToCss[scope.row.status] : 'danger'">
              {{ scope.row.status}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" align="center">
          <template #default="scope">
            <!-- <el-button type="warning" size="small" :icon="View" @click="handleView(scope.row)">
							查看
						</el-button> -->
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
      :title="idEdit ? '编辑资产' : '新增资产'"
      v-model="visible"
      width="500px"
      destroy-on-close
      :close-on-click-modal="false"
      @close="closeDialog"
    >
      <AssetTypeEdit :data="rowData" :edit="idEdit" :update="updateData" />
    </el-dialog>
    <!-- <el-dialog title="查看用户详情" v-model="visible1" width="700px" destroy-on-close>
			<UserTableDetail :data="rowData" />
		</el-dialog> -->
  </div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Delete, Edit, Search, CirclePlusFilled, View } from "@element-plus/icons-vue";
import AssetTypeEdit from "../components/assetTypeEdit.vue";
// import UserTableDetail from "../components/userTableDetail.vue";
import service from "../utils/request";
interface TableItem {
  assetId: number;
  assetTypeId: number;
  name: string;
  purchaseDate: Date; // TypeScript uses the Date object for dates
  price: number; // BigDecimal from Java can be represented as a number in TypeScript for simplicity, but be cautious of precision issues for very large or very small values
  imgDir: string;
  status: string;
  assetType: object; // Optional property, assuming AssetType is another interface you have defined
}


const statusToCss = {
  "Available":"success",
  "Repairing":"warning"
}

const query = reactive({
  assetId: null,
  assetTypeId: null,
  name: null,
  purchaseDate: null,
  price: null,
  imgDir: null,
  status: null,
  assetType: null
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({
  assetId: null,
  assetTypeId: null,
  name: null,
  purchaseDate: null,
  price: null,
  imgDir: null,
  status: null,
  assetType: null
});
const visible1 = ref(false);

const formatDate = (row, column, cellValue, index) => {
  // 假设cellValue是一个标准的日期字符串或者Date对象
  // 你可以根据需要调整日期格式
  const date = new Date(cellValue);
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}

// 获取表格数据
const getData = async () => {
  service.post("/fixedAssets/getFixedAssetsByCondition", query).then((res) => {
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

const handleDelete = async (index: number) => {
  // 二次确认删除
  try {
    await ElMessageBox.confirm("确定要删除吗？", "提示", { type: "warning" });
    // 调用API删除部门
    const assetTypeId = tableData.value[index].assetTypeId;
    await service.delete("/assetType/delete/" + assetTypeId);
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
