<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      :ellipsis="false"
      :default-active="onRoutes"
      @select="handleSelect"
      router

  >

    <div class="flex-grow" />
    <el-menu-item index="1">主页</el-menu-item>
    <el-sub-menu index="2">
      <template #title>我的</template>
      <el-menu-item index="/userManagement" key="/userManagement">个人中心</el-menu-item>
      <el-menu-item divided @click="loginout">退出登录</el-menu-item>
      <el-menu-item index="2-3">item three</el-menu-item>
      <el-sub-menu index="2-4">
        <template #title>item four</template>
        <el-menu-item index="2-4-1">item one</el-menu-item>
        <el-menu-item index="2-4-2">item two</el-menu-item>
        <el-menu-item index="2-4-3">item three</el-menu-item>
      </el-sub-menu>
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
.flex-grow {
  flex-grow: 1;
}
</style>
