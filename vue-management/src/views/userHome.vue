
<template>
  <div>
    <el-row class="content-row">
      <!-- 轮播图 -->
      <el-col :span="24">
        <div class="content-container">
          <el-carousel :interval="4000" type="card" height="300px" width="80%">
            <el-carousel-item v-for="(imgSrc, index) in images" :key="index">
              <el-image style="width: 100%; height: 100%;" fit="cover" :src="imgSrc"></el-image>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="15"></el-col>
      <!-- 空列占据左半边 -->
      <el-col :span="9">
        <!-- 右半边放置控件 -->
        <el-row :gutter="10">
          <el-col :span="10">
            <el-input
                v-model="search"
                placeholder="搜索固定资产..."
                prefix-icon="el-icon-search"
            ></el-input>
          </el-col>
          <el-col :span="6">
            <el-select v-model="sortKey" placeholder="排序依据">
              <el-option label="名称" value="name"></el-option>
              <el-option label="数量" value="quantity"></el-option>
            </el-select>
          </el-col>
          <el-col :span="2">
            <el-button
                type="primary"
                @click="toggleSortOrder" round>{{ sortOrder === "asc" ? "升序" : "降序" }}
            </el-button>
          </el-col>
        </el-row>
      </el-col>
    </el-row>

    <el-row class="list-row">
      <!-- 展示领用列表 -->
      <el-col :span="24">
        <div class="assets-container">
          <el-row :gutter="20">
            <el-col :span="6" v-for="(asset, index) in filteredAssets" :key="index">
              <div class="asset-item">
                <el-image class="asset-image" style="width: 200px; height: 150px" :src="asset.imgDir" fit="cover"></el-image>
                <div class="asset-info">
                  <h3>{{ asset.name }}</h3>
                  <p>数量：{{ asset.quantity }}</p>
                </div>
                <el-button type="primary" class="use-button" @click="handleUseButtonClick(asset)">领用</el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <el-row style="padding-top: 30px">
      <el-col :span="8">

      </el-col>
      <el-col :span="12">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalAssets">
        </el-pagination>
      </el-col>
      <!-- 分页组件 -->

    </el-row>
  </div>
</template>

<script lang="tsx" setup>
import {ref, watch, computed} from "vue";
import {ElMessage} from "element-plus";
import service from "../utils/request";
import {FixedAsset} from "../interface/interface";

interface TableItem extends FixedAsset {
    quantity: number; // BigDecimal from Java can be represented as a number in TypeScript for simplicity, but be cautious of precision issues for very large or very small values
    returnDate: Date;
    allocationDescription: string;
}

const isModalVisible = ref(false);
const selectedAsset = ref<TableItem>({} as TableItem);

const search = ref("");
const sortKey = ref(""); // 排序依据
const sortOrder = ref("asc"); // 排序方向，默认为升序
const maxVal = ref(0);

const assets = ref<TableItem[]>([]);

const images = ref([
  '/assets/img/banner/1.jpg',
  '/assets/img/banner/2.jpg',
  '/assets/img/banner/3.jpg',
  '/assets/img/banner/4.jpg',
  '/assets/img/banner/5.jpg',
  '/assets/img/banner/6.jpg',
  '/assets/img/banner/7.jpg',

]);

const currentPage = ref(1);
const pageSize = ref(10);
const totalAssets = computed(() => {
  return  assets.value.length;
});

const handleSizeChange = (newSize) => {
  pageSize.value = newSize;
  currentPage.value = 1; // 切换页大小时回到第一页
};

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage;
};


const getData = async () => {
    service.post("/fixedAssets/getFixedAssetsByCondition", {
        assetId: null,
        assetTypeId: null,
        name: null,
        purchaseDate: null,
        price: null,
        imgDir: null,
        status: "闲置", // 只获取闲置状态的资产
        assetType: null
    }).then((res) => {

        let result = res.data;

        //按照名称统计资产数量
        let map = new Map();
        for (let i = 0; i < result.length; i++) {
            let obj = result[i];
            if (map.get(obj.name) == null) {
                obj.quantity = 1;
                map.set(obj.name, obj);
            } else {
                let old = map.get(obj.name);
                old.quantity++;
                map.set(obj.name, old);
            }
        }

        // 将map转化为数组
        let arr = [];
        for (let [key, value] of map) {
            arr.push(value);
        }


      //res.data中的数据复制100次，模拟数据量大的情况

      // let arr2 = [];
      //   arr.pop();
      //   for (let i = 0; i < 100; i++) {
      //       for(let j = 0;j < arr.length;j ++){
      //         arr2.push(arr[j]);
      //       }
      //   }
      //
      //   arr = arr2;

        console.log("arr", arr);

        assets.value = arr;
    });
};
getData();

// 排序方向切换方法
const toggleSortOrder = () => {
    sortOrder.value = sortOrder.value === "asc" ? "desc" : "asc";
};

let searchTimeout = null; // 用于存储setTimeout的变量

// 监听search变量的变化
watch(search, () => {
    clearTimeout(searchTimeout); // 取消之前的搜索操作
    searchTimeout = setTimeout(() => {
        getData();
    }, 1000);
});

// 创建一个计算属性来实现前端过滤
const filteredAssets = computed(() => {
    let result = assets.value;
    if (search.value) {
        result = result.filter((asset) =>
            asset.name.toLowerCase().includes(search.value.toLowerCase())
        );
    }
    if (sortKey.value) {
        result.sort((a, b) => {
            let comparison = 0;
            if (sortKey.value === "name") {
                comparison = a.name.localeCompare(b.name);
            } else if (sortKey.value === "quantity") {
                comparison = a.quantity - b.quantity;
            }
            return sortOrder.value === "asc" ? comparison : -comparison;
        });
    }
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return result.slice(start, end);
});


const handleUseButtonClick = (asset) => {
    //selectedAsset的值是asset的拷贝
    selectedAsset.value = JSON.parse(JSON.stringify(asset));
    maxVal.value = asset.quantity;
    isModalVisible.value = true;
};

const submitUseForm = () => {
    // 这里模拟一个API请求，实际应用中你需要替换成真实的API调用
    console.log("提交领用请求", selectedAsset.value);


    service.post("/assetAllocation/allocation", selectedAsset.value, {
        withCredentials: true,
    }).then((res) => {
        console.log("领用成功", res);
        // 假设请求成功
        isModalVisible.value = false; // 关闭模态框
        // 这里可以添加成功提示，比如使用Element UI的Message组件
        ElMessage.success("领用成功");

        getData();
    }).catch((err) => {
        console.error("领用失败", err);
        // 获取请求失败后端返回的错误信息并弹窗提示
        ElMessage.error(err.response.data);
    });


};
</script>
<style scoped>

.content-container {
    /* 根据需要调整，这里设置的是左右各留10%的空间 */
    /** 25px 改成你想要距离轮播图的距离 */
    padding: 0 5% 20px;
}

.el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
}

.assets-container {

    padding: 0 10%; /* 根据需要调整，这里设置的是左右各留10%的空间 */

    margin-top: 20px;
}

.asset-item {
    position: relative;
    text-align: center;
    /*width: 220px; !* 根据需要调整宽度 *!*/
    /*padding: 10px;*/
    padding: 10px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    transition: all 0.3s;
    overflow: hidden;
}

.asset-item:hover .use-button {
    visibility: visible; /* 改变可见性 */
    opacity: 1; /* 当鼠标悬停时按钮完全不透明 */
}

.asset-item:hover .asset-info {
    opacity: 0;
}

.asset-image {
    width: 100%;
    border-radius: 4px;
    transition: transform 0.3s ease;
}

.asset-image:hover {
    transform: scale(1.1);
}

.asset-info {
    margin-top: 10px;
    transition: opacity 0.3s ease;
}

.use-button {
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    visibility: hidden; /* 开始时不可见 */
    opacity: 0; /* 初始设为完全透明 */
    transition: all 0.3s;
}
</style>
