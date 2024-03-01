<template>
    <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="资产名称" prop="name">
            <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="资产类别" prop="assetTypeId">
            <el-select
                v-model="form.assetTypeId"
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
                    :key="item.value"
                    :label="item.typeName"
                    :value="item.assetTypeId"
                />
            </el-select>
        </el-form-item>
        <el-form-item label="购置日期" prop="purchaseDate">
            <el-date-picker
                v-model="form.purchaseDate"
                type="date"
                placeholder="请选择购置日期"
                style="width: 100%"
            />
        </el-form-item>
        <el-form-item
            label="价格"
            prop="price">
            <el-input-number v-model="form.price" :precision="2" :step="0.01" :min="0.00" />

        </el-form-item>
        <el-form-item label="资产图片" prop="imgDir">
            <el-upload
                class="avatar-uploader"
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
            >
<!--                TODO 上传图片的操作-->
                <img v-if="form.imgDir" :src="imgDir" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
        </el-form-item>
        <el-form-item label="资产状态" prop="description">
            <el-select
                v-model="form.status"
                placeholder="Select"
                size="large"
                style="width: 240px"
            >
                <el-option
                    v-for="item in statusOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="saveEdit(formRef)">保存</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import {ElMessage, FormInstance} from 'element-plus';
import {ref} from 'vue';
import type { UploadProps } from 'element-plus'
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
    assetId: null,
    assetTypeId: null,
    name: null,
    purchaseDate: null,
    price: null,
    imgDir: null,
    status: null,
    assetType: null
};

interface ListItem {
    assetTypeId: number;
    typeName: string;
    description: string;
}

const statusOptions = [
    {
        value: 'Available',
        label: 'Available',
    },
    {
        value: 'Repairing',
        label: 'Repairing',
    },
    {
        value: 'Other',
        label: 'Other',
    }
]


const loading = ref(false)
const form = ref({...(props.edit ? props.data : defaultData)});
const list = ref<ListItem[]>([])
const options = ref<ListItem[]>([])
const value = ref<string[]>([])
const formRef = ref<FormInstance>();

const remoteMethod = (query: string) => {

    loading.value = true
    service.post("/assetType/getAssetTypeByCondition", {
        typeName: null,
        description: null,
    }).then((res) => {
        loading.value = false

        if (query) {
            options.value = res.data.filter((item) => {
                return item.typeName.toLowerCase().includes(query.toLowerCase())
            })

        } else {
            options.value = res.data
        }

        console.log(options.value)
    }).catch((err) => {
        loading.value = false
        options.value = []
    })

}

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    form.imgSrc = URL.createObjectURL(uploadFile.raw!)
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.type !== 'image/jpeg'
        && rawFile.type !== 'image/png'
        && rawFile.type !== 'image/jpg'
        && rawFile.type !== 'image/gif') {
        ElMessage.error('请上传图片格式!')
        return false
    } else if (rawFile.size / 1024 / 1024 > 50) {
        ElMessage.error('图片大小不能超过 50MB!')
        return false
    }
    return true
}

const saveEdit = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.validate((valid) => {
        if (!valid) return false;
        const action = props.edit ? '/asset/update/' + form.value.assetTypeId : '/assetType/add';
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

