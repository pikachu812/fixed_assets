<template>
    <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="用户名" prop="name">
            <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="用户角色" prop="state">
            <el-select v-model="form.role.roleName" class="m-2" placeholder="Select" size="large" style="width: 240px">
                <el-option v-for="item in roleList" :key="item.value" :label="item.label" :value="item.value"/>
            </el-select>
        </el-form-item>

        <el-form-item v-if="form.role.roleName === 'user'" label="职工姓名" prop="employee.name">
            <el-input v-model="form.employee.name"></el-input>
        </el-form-item>

        <el-form-item v-if="form.role.roleName === 'user'" label="部门" prop="employee.departmentId">
            <el-select
                v-model="form.employee.departmentId"
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

        <el-form-item label="密码" prop="password">
            <el-input v-model="form.password"></el-input>
            <span style="color: #999; font-size: 12px;">（留空表示不修改密码）</span>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="saveEdit(formRef)">保 存</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import {ElMessage, FormInstance, FormRules, UploadProps} from 'element-plus';
import {ref} from 'vue';
import service from "../utils/request";
import {Department} from "../interface/interface";

interface ListItem extends Department {
}

const roleList = [
    {
        value: 'admin',
        label: '管理员',
    },
    {
        value: 'user',
        label: '普通员工',
    }
]

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
    userId: '',
    username: '',
    password: '',
    roleId: '',
    role: {
        roleId: '',
        roleName: ''
    },
    employeeId: '',
    employee: {
        employeeId: '',
        name: '',
        departmentId: '',
        department: {
            departmentId: '',
            name: '',
            description: ''
        }
    }

};

const form = ref({...(props.edit ? props.data : defaultData)});


const formRef = ref<FormInstance>();
const loading = ref(false)
const options = ref<ListItem[]>([])


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


        console.log("options", options.value);
        console.log(typeof form.value.employee.departmentId);

    }).catch((err) => {
        loading.value = false
        options.value = []
    })

}

remoteMethod("");

const saveEdit = (formEl: FormInstance | undefined) => {
    if (!formEl) return;


    if (props.edit) {
        formEl.validate(valid => {

            if (!valid) return false;

            if (form.value.role.roleName === 'admin') {
                form.value.roleId = 1;
                form.value.role.roleId = 1;
            } else {
                form.value.roleId = 2;
                form.value.role.roleId = 2;
            }

            console.log(form.value);

            service.put('/user/update', form.value).then((res) => {
                ElMessage.success('修改成功');
            }).catch((err) => {
                ElMessage.error('修改失败');
            });


        });
    } else {
        formEl.validate(valid => {

            if (!valid) return false;

            if (form.value.role.roleName === 'admin') {
                form.value.roleId = 1;
                form.value.role.roleId = 1;
            } else {
                form.value.roleId = 2;
                form.value.role.roleId = 2;
            }

            console.log(form.value);

            service.post('/user/add', form.value).then((res) => {
                ElMessage.success('新增成功');
            }).catch((err) => {
                ElMessage.error('新增失败');
            });


        });
    }


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

