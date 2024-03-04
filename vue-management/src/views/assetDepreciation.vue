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

        <el-dropdown split-button type="warning" @command="handleDepreciationMethodChange" style=" margin-left: 40px">
          固定资产折旧方法
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="straightLine">直线法</el-dropdown-item>
              <el-dropdown-item command="decliningBalance">双倍余额递减法</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </div>
<!--        <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">新增</el-button>-->
      </div>
      <div class="table-container">
        <el-table
            :data="tableData"
            border
            class="table"
            style="width: 100%;"
            ref="multipleTable"
            header-cell-class-name="table-header"
        >
          <el-table-column
              prop="assetId"
              label="资产编号"
              width="85"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="name"
              label="资产名称"
              width="160"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="purchaseDate"
              :formatter="formatDate"
              label="购置日期"
              width="150"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="usefulYear"

              label="使用年限"
              width="100"
              align="center"
          ></el-table-column>
          <el-table-column
              sortable
              prop="price"
              label="价格￥"
              width="150"
              align="center"
          ></el-table-column>
          <el-table-column
              sortable
              prop="currentDepreciation"
              label="当期折旧金额"
              width="150"
              align="center"
          ></el-table-column>
          <el-table-column
              sortable
              prop="accumulatedDepreciation"
              label="累计折旧"
              width="150"
              align="center"
          ></el-table-column>
          <el-table-column
              sortable
              prop="netBookValue"
              label="固定资产折旧净值"
              width="180"
              align="center"
          ></el-table-column>
<!--          固定该列-->
          <el-table-column
              fixed = "right"
              label="操作"
              width="180"
              align="center"
          >
            <template #default="scope">
              <div class="operation-buttons">
                <el-button
                    type="primary"
                    size="small"
                    :icon="Edit"
                    @click="handleEdit(scope.$index, scope.row)"
                >编辑</el-button>
                <el-button
                    type="danger"
                    size="small"
                    :icon="Delete"
                    @click="handleDelete(scope.$index)"
                >删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
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
      <AssetEdit :data="rowData" :edit="idEdit" :update="updateData"/>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import {
  ElMessage,
  ElMessageBox,
} from "element-plus";
import { Search } from "@element-plus/icons-vue";
import AssetEdit from "../components/AssetEdit.vue";
import dayjs from "dayjs";

interface TableItem {
  assetId: number;
  name: string;
  purchaseDate: string;
  usefulYear: number;
  price: number;
  currentDepreciation: number;
  accumulatedDepreciation: number;
  netBookValue: number;
}

const query = reactive({
  name: '' // 搜索查询对象
});

const tableData = ref<TableItem[]>([
  // ...您之前的样例数据...
  {
    assetId: 1,
    name: '办公电脑',
    purchaseDate: '2019-01-15',
    usefulYear: 3,
    price: 8000.00,
    currentDepreciation: 0, // 将通过计算填充
    accumulatedDepreciation: 0, // 将通过计算填充
    netBookValue: 8000.00, // 初始净值等于购置价
  },
  {
    assetId: 2,
    name: '打印机',
    purchaseDate: '2020-06-10',
    usefulYear: 5,
    price: 1500.00,
    currentDepreciation: 0,
    accumulatedDepreciation: 0,
    netBookValue: 1500.00,
  },
  {
    assetId: 3,
    name: '办公桌椅',
    purchaseDate: '2018-09-20',
    usefulYear: 5,
    price: 1200.00,
    currentDepreciation: 0,
    accumulatedDepreciation: 0,
    netBookValue: 1200.00,
  },
  {
    assetId: 4,
    name: '空调',
    purchaseDate: '2017-11-30',
    usefulYear: 10,
    price: 5000.00,
    currentDepreciation: 0,
    accumulatedDepreciation: 0,
    netBookValue: 5000.00,
  },
]);

const pageIndex = ref(1);
const pageSize = 10;
const pageTotal = ref(tableData.value.length);
const visible = ref(false);
const idEdit = ref(false);
const rowData = ref<TableItem | null>(null);

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
    depreciationExpense = (item.price / item.usefulYear).toFixed(2);
    accumulatedDepreciation = (depreciationExpense * Math.min(yearsElapsed, item.usefulYear)).toFixed(2);
  } else if (method === 'decliningBalance') {
    // 双倍余额递减法计算
    for (let year = 1; year <= yearsElapsed && year <= item.usefulYear; year++) {
      if (year === 1) {
        depreciationExpense = ((item.price * 2) / item.usefulYear).toFixed(2);
      } else {
        let tempBookValue = item.price - accumulatedDepreciation; // 临时计算当前账面价值
        depreciationExpense = ((tempBookValue * 2) / item.usefulYear).toFixed(2);
      }
      accumulatedDepreciation = (+accumulatedDepreciation + +depreciationExpense).toFixed(2); // 更新累计折旧
      if (+accumulatedDepreciation >= item.price) {
        accumulatedDepreciation = item.price.toFixed(2); // 确保累计折旧不超过原价
        break;
      }
    }
  }

  netBookValue = (item.price - accumulatedDepreciation).toFixed(2); // 计算净账面价值
  // 确保净账面价值不会变成负数
  netBookValue = Math.max(0, netBookValue).toFixed(2);

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
  console.log('执行搜索', query);
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

const handleEdit = (index: number, row: TableItem) => {
  rowData.value = row;
  idEdit.value = true;
  visible.value = true;
};

const updateData = (row: TableItem) => {
  // 这里替换为实际的后端请求代码
  console.log('更新数据', row);
  closeDialog();
};

const closeDialog = () => {
  visible.value = false;
  idEdit.value = false;
  // 可能需要重新获取数据
};
</script>



<style scoped>
.container {
  display: flex;
  flex-direction: column;
  width: 100%;
}

.search-box {
  display: flex;
  align-items: center; /* 确保所有元素垂直居中 */
  margin-bottom: 20px; /* 与下面内容的间隔 */
}

.search-input {
  width: 200px; /* 输入框的宽度，可根据需要调整 */
  margin-right: 10px; /* 输入框和搜索按钮之间的间隔 */
}

/* .mr10 类已经没有必要，因为已经在 .search-input 中设置了 margin-right
.mr10 {
  margin-right: 10px;
}
*/

/* 为了更精确地控制下拉框的位置，我们可以将其放入一个新的 div 中 */
.dropdown-container {
  margin-left: 20px; /* 将下拉框推向右侧 */
}

/* 调整表格容器的样式 */
.table-container {
  width: 100%; /* 表格容器宽度 */
}

/* 根据需要调整表格头部单元格的样式 */
.table-header {
  background-color: #f9fafb; /* 举例，可根据需要调整 */
}

/* 调整分页器样式 */
.pagination {
  margin-top: 20px; /* 与表格的间隔 */
}

/* 以下样式用于操作按钮，保持与之前一致 */
.operation-buttons {
  display: flex;
  justify-content: center;
  gap: 5px; /* 按钮之间的间隔 */
}

/* 按钮和图标的样式根据需要进行调整 */
.el-button {
  /* 按钮样式 */
}

/* 图标样式 */
:deep(.el-icon) {
  /* 图标样式 */
}
</style>

