<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      :default-active="onRoutes"
      @select="handleSelect"
      router

  >
    <div class="system-name">企业固定资产核算系统</div>
    <div class="flex-grow"/>
    <el-menu-item index="/userHome" key="/userHome">主页</el-menu-item>
    <el-menu-item index="/assetAllocationList" key="/assetAllocationList">固定资产申领列表</el-menu-item>
    <el-sub-menu index="3">
      <template #title>我的</template>
      <el-menu-item index="/user" key="/user">个人中心</el-menu-item>
      <el-menu-item divided @click="loginout">退出登录</el-menu-item>

    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
import {computed, ref} from 'vue'
import {useRoute, useRouter} from "vue-router";

const route = useRoute();
const onRoutes = computed(() => {
  return route.path;
});

const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const router = useRouter();

const loginout = () => {
  localStorage.removeItem("role");
  localStorage.removeItem("ms_username");
  router.push("/login");
}


</script>

<style>

.el-menu-demo {
  background-color: #edf0f4 !important; /* 深灰色背景 */
  color: #fff; /* 文字颜色设置为白色以便于深色背景下的阅读 */
}

.system-name {
  font-size: 20px; /* 可根据需要调整 */
  color: #1B7BB5; /* 设置系统名称的文字颜色 */
  line-height: 60px; /* 调整这个值以垂直居中文字，根据实际高度调整 */
  padding-left: 20px; /* 添加一些左侧内边距 */
  font-weight: bold; /* 可选：如果你希望系统名称更加显眼 */
}

.flex-grow {
  flex-grow: 1;
}

.flex-grow {
  flex-grow: 1;
}
</style>
