<template>
  <div>
    <div class="container">
      <div class="search-box">
        <el-input
            v-model="query"
            placeholder="资产名称"
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
        <el-table-column label="序号" type="index" align="center" width="60%"/>

        <el-table-column prop="assetName" label="资产名称" align="center" width="250"></el-table-column>
        <el-table-column prop="employeeName" label="员工姓名" align="center" width="250"></el-table-column>
        <el-table-column prop="departmentName" label="部门名称" align="center" width="250"></el-table-column>
        <el-table-column prop="allocationDescription" label="申请理由" align="center"></el-table-column>

        <el-table-column label="审核操作" width="250" align="center">
          <template #default="scope">
            <el-button
                type="success"
                size="small"
                :icon="Check"
                @click="allocationPass(scope.$index)"
                v-permiss="16"
            >
              通过
            </el-button>

            <el-button
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
      <el-form ref="formRef" label-width="100px"  >
        <el-form-item label="不通过原因"  prop="name">
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

<script setup lang="ts" name="basetable">
import {ref, reactive} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import { Close, Delete, Edit, Search, CirclePlusFilled, Check, View} from "@element-plus/icons-vue";
import AssetTypeEdit from "../components/assetTypeEdit.vue";
// import UserTableDetail from "../components/userTableDetail.vue";
import service from "../utils/request";

interface TableItem {
  allocationId: number;
  assetName: string;
  employeeName: string;
  departmentName: string;
  allocationDescription: string;
}

const query = ref('');
const reason = ref('');
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const pageSize = 10;
const pageIndex = ref(1);
const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref<TableItem>({
  allocationId: null,
  assetName: null,
  employeeName: null,
  departmentName: null,
  allocationDescription: null
});
const visible1 = ref(false);

// 获取表格数据
const getData = async () => {

  let sampleData = [
    {
      allocationId: 1,
      assetName: "电脑",
      employeeName: "张三",
      departmentName: "技术部",
      allocationDescription: "办公用"
    },
    {
      allocationId: 2,
      assetName: "手机",
      employeeName: "李四",
      departmentName: "人事部",
      allocationDescription: "办公用"
    },
    {
      allocationId: 3,
      assetName: "打印机",
      employeeName: "王五",
      departmentName: "财务部",
      allocationDescription: "办公用"
    }
  ];

  tableData.value = sampleData;
  pageTotal.value = sampleData.length;




  // service.post("/assetAllocation", query).then((res) => {    //邱秋3/2改的，不知道对不对
  //   console.log(res);
  //   tableData.value = res.data;
  //   pageTotal.value = res.data.length;
  //   pageIndex.value = 1;
  // });
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


const allocationPass = (index: number) => {
  ElMessageBox.confirm("确定要通过吗？", "提示", {type: "warning"}).then(() => {
    ElMessage.success("通过");
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
    ElMessage.warning("已通知该员工");
    visible.value = false;

    // 获取当前行的数据
    let row = tableData.value[idx];
    // 获取不通过理由
    row.allocationDescription = reason.value;

    //todo: 提交不通过理由
    console.log(row);
    console.log("不通过理由：" + reason.value);

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
