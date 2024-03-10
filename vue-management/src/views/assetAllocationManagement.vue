<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input
            v-model="query"
            placeholder="请输入关键词"
            class="search-input mr10"
            clearable
        ></el-input>
        <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
        <!--        <el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">新增</el-button>-->
      </div>
      <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
      >
        <el-table-column label="序号"

                         align="center" width="60%">
          <template #default="scope">
            <!--            分页后的处理序号-->
            {{ (pageIndex - 1) * pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="fixedAsset.name" label="资产名称" align="center" width="120%"></el-table-column>
        <el-table-column prop="user.employee.name" label="员工姓名" align="center"
                         width="90px"></el-table-column>
        <el-table-column prop="user.employee.department.name" label="部门名称" align="center"
                         width="100px"></el-table-column>
        <el-table-column sortable prop="allocationDate" :formatter="formatDate"
                         label="领用日期" align="center" width="110px"></el-table-column>
        <el-table-column sortable prop="returnDate" :formatter="formatDate"
                         label="返回日期" align="center" width="110px"></el-table-column>
        <el-table-column
            :filters="[
                        { text: '待审核', value: '待审核' },
                        { text: '审核通过', value: '审核通过' },
                        { text: '审核不通过', value: '审核不通过' },
                        { text: '已归还', value: '已归还' }
                    ]"
            :filter-method="filterStatus"
            label="状态" align="center" width="100px">
          <template #default="scope">
            <el-tag @click="detail(scope.row)"
                    :type="statusToCss[scope.row.status] ? statusToCss[scope.row.status] : 'danger'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="allocationDescription" label="申请理由" align="center"></el-table-column>

        <el-table-column label="审核操作" width="200px" align="center">
          <template #default="scope">
            <el-button
                v-if="scope.row.status === '待审核'"
                type="success"
                size="small"
                :icon="Check"
                @click="allocationPass(scope.$index)"
                v-permiss="16"
            >
              通过
            </el-button>

            <el-button
                v-if="scope.row.status === '待审核'"
                type="danger"
                size="small"
                :icon="Close"
                @click="allocationUnpass(scope.$index)"
                v-permiss="16">
              不通过
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
        align-center
        :title="'审核详情'"
        v-model="visible"
        width="500px"
        destroy-on-close
        :close-on-click-modal="false"
        @close="closeDialog"
    >
      <!--      <AllocationEdit :data="rowData" :edit="idEdit" :update="updateData"/>-->
      <el-form ref="formRef" label-width="100px">
        <el-form-item label="不通过原因" prop="name">
          <el-input
              maxlength="50"
              show-word-limit
              type="textarea" placeholder="请管理员输入审核不通过原因" v-model="reason"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveEdit()">提交</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- <el-dialog title="查看用户详情" v-model="visible1" width="700px" destroy-on-close>
            <UserTableDetail :data="rowData" />
        </el-dialog> -->
  </div>
</template>

<script setup lang="ts">
import {ref, reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {Close, Search, Check, View} from "@element-plus/icons-vue";
import service from "../utils/request";
import {AssetAllocation} from "../interface/interface";


interface TableItem extends AssetAllocation {
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

const statusToCss = {
  "审核通过": "success",
  "已归还": "primary",
  "待审核": "warning",
  "审核不通过": "danger"
}

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
const formatDate = (row, column, cellValue, index) => {
  // 假设cellValue是一个标准的日期字符串或者Date对象
  // 你可以根据需要调整日期格式
  if (cellValue === null) return "";

  const date = new Date(cellValue);
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
}


const filterStatus = (value, row) => {
  return row.status === value;
};

const detail = (row: TableItem) => {

  if (row.status === "审核不通过") {
    //弹窗显示不通过理由
    ElMessageBox.alert(row.reason, "不通过理由", {
      confirmButtonText: "确定",
      type: "warning",
    });
  }
};
// 获取表格数据
const getData = async () => {
  pageIndex.value = 1; // 重置页码
  service.post("/assetAllocation/search", {
    assetName: query.value,
    employeeName: query.value,
    departmentName: query.value,
    allocationDescription: query.value,
    // status: '待审核'
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
  });
};
getData();

// 查询操作
const handleSearch = () => {
  getData();
};

// 分页导航
const handlePageChange = (val: number) => {
  pageIndex.value = val; // 更新当前页码
  doPagination(); // 根据新的页码重新分页
};

const doPagination = () => {
  const start = (pageIndex.value - 1) * pageSize;
  const end = start + pageSize;
  tableData.value = allData.value.slice(start, end); // 从所有数据中切割当前页的数据
};


const allocationPass = (index: number) => {
  ElMessageBox.confirm("确定要通过吗？", "提示", {type: "warning"}).then(() => {


    // 获取当前行的数据
    let row = tableData.value[index];


    service.post("/assetAllocation/pass/" + row.allocationId, {}).then((res) => {    //邱秋3/2改的，不知道对不对
      ElMessage.success("通过");
      getData();
    }).catch(() => {
      ElMessage.error("通过失败");
    });

  }).catch(() => {
    ElMessage.error("取消");
  });
};

// 不通过，需要填写不通过理由
const allocationUnpass = (index: number) => {
  idx = index;
  visible.value = true;
};

// 关闭弹窗
const closeDialog = () => {
  visible.value = false;
  idEdit.value = false;
  getData();
};

const saveEdit = () => {
  ElMessageBox.confirm("确定要不通过吗？", "提示", {type: "warning"}).then(() => {


    // 获取当前行的数据
    let row = tableData.value[idx];
    // 获取不通过理由
    row.reason = reason.value;

    //todo: 提交不通过理由
    console.log(row);
    console.log("不通过理由：" + reason.value);

    service.post("/assetAllocation/reject/" + row.allocationId, {
      reason: reason.value
    }).then((res) => {    //邱秋3/2改的，不知道对不对

      ElMessage.warning("已通知该员工");
      visible.value = false;
      getData();

    }).catch(() => {
      ElMessage.error("操作失败");
      visible.value = false;
    });


  }).catch(() => {
    ElMessage.error("取消");
  });
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
