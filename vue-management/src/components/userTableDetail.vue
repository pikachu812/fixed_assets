<template>
    <el-descriptions title="" :column="2" border>
        <el-descriptions-item>
            <template #label> 用户ID </template>
            {{ data.userId }}
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label> 用户名 </template>
            {{ data.username }}
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label> 用户角色 </template>
            {{ data.role.roleName}}
        </el-descriptions-item>
        <el-descriptions-item label="用户权限">
            <el-tree :data="treeData" node-key="permissionId" ref="tree"></el-tree>
        </el-descriptions-item>

    </el-descriptions>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from 'vue';

const props = defineProps({
    data: {
        type: Object,
        required: true
    }
});

const treeData = ref([]);

// 转换权限数据为树形结构
const convertToTreeData = (permissions) => {


    return permissions.map(permission => ({
        id: permission.permissionId,
        label: permission.permissionName,
        children: [] // 根据需要，你可以在这里添加子权限
    }));
};

watchEffect(() => {
    if (props.data.permissions) {
        treeData.value = convertToTreeData(props.data.permissions);
    }
});
</script>
