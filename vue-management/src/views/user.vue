<template>
	<div>
		<el-row :gutter="20">
			<el-col :span="12">
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>基础信息</span>
						</div>
					</template>
					<div class="info">
						<div class="info-image">
							<el-avatar :size="100" :src="avatarImg" />
							<span class="info-edit">
								<i class="el-icon-lx-camerafill"></i>
							</span>
						</div>
						<div class="info-name">{{ userDetail.username }}</div>
						<div class="info-desc">不可能！我的代码怎么可能会有bug！</div>
					</div>
				</el-card>
			</el-col>
			<el-col :span="12">
				<el-card shadow="hover">
					<template #header>
						<div class="clearfix">
							<span>账户编辑</span>
						</div>
					</template>
					<el-form label-width="90px">
						<el-form-item label="用户名："> {{ userDetail.username }} </el-form-item>
                        <el-form-item label="角色">
                            <el-tag v-if="userDetail.roleId === 1" type="success">管理员</el-tag>
                            <el-tag v-else type="warning">普通用户</el-tag>
                        </el-form-item>
                        <el-form-item label="员工姓名：" v-if="userDetail.employee && userDetail.roleId !== 1">
                            {{ userDetail.employee.name }}
                        </el-form-item>
                        <el-form-item label="部门：" v-if="userDetail.employee.department && userDetail.roleId !== 1">
                            {{ userDetail.employee.department.name }}
                        </el-form-item>
						<el-form-item label="旧密码：">
							<el-input type="password" v-model="form.old"></el-input>
						</el-form-item>
						<el-form-item label="新密码：">
							<el-input type="password" v-model="form.new"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="onSubmit">保存</el-button>
						</el-form-item>
					</el-form>
				</el-card>
			</el-col>
		</el-row>

	</div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue';
import 'cropperjs/dist/cropper.css';
import avatar from '../assets/img/img.jpg';
import service from "../utils/request";
import {User} from "../interface/interface";
import {ElMessage} from "element-plus";

const name = localStorage.getItem('ms_username');
const form = reactive({
	old: '',
	new: '',
});
const onSubmit = () => {
    service.post("/user/updatePassword",form).then((res) => {
        ElMessage.success("修改成功");
    }).catch((err) => {
        ElMessage.error("修改失败");
    });
};

interface UserDetail extends User{
    password: string | null;
}

const userDetail = ref<UserDetail>({
    userId: null,
    username: null,
    password: null,
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
})


const avatarImg = ref(avatar);
const getData = async () => {
    service.get("/user/my").then((res) => {

        console.log("res", res);

        userDetail.value = res.data;
        console.log("userDetail", userDetail.value);

    });
};
getData();



</script>

<style scoped>
.info {
	text-align: center;
	padding: 35px 0;
}
.info-image {
	position: relative;
	margin: auto;
	width: 100px;
	height: 100px;
	background: #f8f8f8;
	border: 1px solid #eee;
	border-radius: 50px;
	overflow: hidden;
}

.info-edit {
	display: flex;
	justify-content: center;
	align-items: center;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	opacity: 0;
	transition: opacity 0.3s ease;
}
.info-edit i {
	color: #eee;
	font-size: 25px;
}
.info-name {
	margin: 15px 0 10px;
	font-size: 24px;
	font-weight: 500;
	color: #262626;
}
.crop-demo-btn {
	position: relative;
}
.crop-input {
	position: absolute;
	width: 100px;
	height: 40px;
	left: 0;
	top: 0;
	opacity: 0;
	cursor: pointer;
}
</style>
