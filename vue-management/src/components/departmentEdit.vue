<template>
  <el-form ref="formRef" :model="form" label-width="100px">
    <el-form-item label="部门名称" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="描述" prop="description">
      <el-input v-model="form.description"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="saveEdit(formRef)">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance } from 'element-plus';
import { ref } from 'vue';
import service from "../utils/request";

const props = defineProps({
  data: {
    type: Object,
    required: true
  },
  edit: {
    type: Boolean,
    required: false
  },
  update: {
    type: Function,
    required: true
  }
});

const defaultData = {
  departmentId: '',
  name: '',
  description: ''
};

const form = ref({ ...(props.edit ? props.data : defaultData) });

const formRef = ref<FormInstance>();

const saveEdit = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (!valid) return false;
    const action = props.edit ? '/department/update' : '/department/add';
    service.post(action, form.value).then((res) => {
      ElMessage.success('操作成功');
      props.update(); // 通知父组件更新列表
    }).catch((err) => {
      ElMessage.error('操作失败');
    });
  });
};
</script>


<style>
.avatar-uploader .el-upload {
	border: 1px dashed var(--el-border-color);
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	overflow: hidden;
	transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
	border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
	font-size: 28px;
	color: #8c939d;
	width: 178px;
	height: 178px;
	text-align: center;
}
</style>

