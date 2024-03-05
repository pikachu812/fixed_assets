<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input
            v-model="query.inventoryName"
            placeholder="资产名称"
            class="search-input mr10"
            clearable
        ></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">
          新增
        </el-button>
        <!--筛选两个日期之间的盘点的记录-->
        <el-date-picker
            v-model="query.date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            style="margin-left: 20px"
        ></el-date-picker>

      </div>

      <el-table
          :data="tableData"
          border
          fit
          class="table"
          ref="multipleTable"
      >
        <el-table-column
            prop="inventoryId"
            label="盘点编号"
            align="center"
            width="100"
        ></el-table-column>
        <el-table-column
            prop="inventoryName"
            label="盘点名称"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="inventoryDate"
            :formatter="formatDate"
            label="盘点日期"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="department"
            label="部门"
            align="center"
        ></el-table-column>
        <el-table-column
            sortable
            prop="bookQuantity"
            label="账面数量"
            align="center"
        ></el-table-column>
        <el-table-column
            sortable
            prop="bookAmount"
            label="账面金额"
            :formatter="priceFormatter"
            align="center"
        ></el-table-column>
        <el-table-column label="操作" align="center" >
          <template #default="scope">
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
      <el-form ref="formRef" label-width="100px">

        <el-form-item label="部门" prop="employee.departmentId">
          <el-select
              v-model="form.departmentId"
              filterable
              remote
              reserve-keyword
              placeholder="请输入一个关键字"
              remote-show-suffix
              :remote-method="remoteMethod"
              :loading="loading"
              style="width: 240px"
          >
            <el-option
                v-for="item in options"
                :key="item.departmentId"
                :label="item.name"
                :value="parseInt(item.departmentId)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="盘点名称" prop="name">
          <el-input
              maxlength="50"
              show-word-limit
              type="textarea" placeholder="请输入此次盘点名称" v-model="inventoryName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveEdit()">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

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
import {Department, FixedAsset} from "../interface/interface";

interface TableItem extends FixedAsset {
  currentDepreciation: number;
  accumulatedDepreciation: number;
  netBookValue: number;
}

interface ListItem extends Department {}

const query = reactive({
  inventoryId: null,
  inventoryDate: null,
  department: null,
  bookQuantity: null,
  bookAmount: null,
});

const tableData = ref<TableItem[]>([]);

const pageIndex = ref(1);
const pageSize = 10;
const pageTotal = ref(tableData.value.length);
const visible = ref(false);
const idEdit = ref(false);
const rowData = ref<TableItem | null>(null);
let idx: number = -1;


const loading = ref(false)
const options = ref<ListItem[]>([])

const form = ref({
  departmentId: null,
});
const remoteMethod = (query: string) => {

  loading.value = true
  service.get("/department/all",).then((res) => {
    loading.value = false

    if (query) {
      options.value = res.data.filter((item) => {
        return item.name.toLowerCase().includes(query.toLowerCase())
      })

    } else {
      options.value = res.data
    }


  }).catch((err) => {
    loading.value = false
    options.value = []
  })

}

remoteMethod("");

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
  service.post("/fixedAssets/", query).then((res) => {
    console.log("res", res);
    console.log(res);


    tableData.value = res.data;
    pageTotal.value = res.data.length;
    pageIndex.value = 1;
  });
};
getData();

const handleEdit = (index: number, row: TableItem) => {
  // 当处理编辑操作时，会将当前行的数据传递给子组件
  idx = index;
  rowData.value = row;
  idEdit.value = true;
  visible.value = true;
};

const handleSearch = () => {
  // 实际应用中应发送请求到后端进行搜索
  getData()
};

const closeDialog = () => {
  visible.value = false;
  idEdit.value = false;
  getData();
};

const handlePageChange = (val: number) => {
  // 实际应用中应根据分页参数重新获取数据
  pageIndex.value = val;
};

const handleDelete = async (index: number) => {
  try {
    await ElMessageBox.confirm("确定要删除此盘点记录吗？", "警告", {
      type: "warning",
    });
    // 这里替换为实际的后端请求代码
    console.log('删除盘点记录', tableData.value[index].inventoryId);
    ElMessage.success("删除成功");
    tableData.value.splice(index, 1);
    pageTotal.value -= 1;
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error("删除失败");
    }
  }
};

// const saveEdit = () => {
//   // 验证表单
//   const formRef = ref.value.formRef; // 获取表单引用
//   formRef.validate(async (valid) => {
//     if (valid) {
//       // 表单验证通过
//       loading.value = true; // 显示加载状态
//       try {
//         const formData = {
//           departmentId: form.value.departmentId,
//           inventoryName: rowData.value ? rowData.value.inventoryName : '',
//           ... // 其他需要提交的表单数据
//         };
//
//         let response;
//         if (idEdit.value) {
//           // 编辑模式
//           response = await service.put(`/fixedAssets/${rowData.value?.inventoryId}`, formData);
//         } else {
//           // 新增模式
//           response = await service.post("/fixedAssets", formData);
//         }
//
//         if (response && response.data) {
//           ElMessage.success(idEdit.value ? "编辑成功" : "新增成功");
//           closeDialog(); // 关闭对话框
//           getData(); // 刷新表格数据
//         } else {
//           ElMessage.error("操作失败");
//         }
//       } catch (error) {
//         console.error("保存失败", error);
//         ElMessage.error("保存异常，请稍后重试");
//       } finally {
//         loading.value = false; // 隐藏加载状态
//       }
//     } else {
//       ElMessage.error("表单数据有误，请检查后再试");
//     }
//   });
// };


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

