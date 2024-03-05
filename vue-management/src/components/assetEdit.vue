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
        <el-form-item
            label="使用年限"
            prop="price">
            <el-input-number v-model="form.usefulYear" :precision="0" :step="1" :min="0" />

        </el-form-item>
        <el-form-item label="资产图片" prop="imgDir">
            <el-upload
                class="avatar-uploader"
                :action="baseUrl+ '/fixedAssets/upload'"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
            >

                <el-image
                    v-if="form.imgDir !== undefined"
                    style="width: 150px; height: 150px"
                    :src="form.imgDir"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :initial-index="4"
                    fit="cover"
                />
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
//导入全局常量baseUrl
import {baseUrl} from "../utils/request";
import {AssetType, FixedAsset} from "../interface/interface";


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
    usefulYear: null,
    assetType: null
};


interface FormData extends FixedAsset{}
interface ListItem extends AssetType{}

const statusOptions = [
    {
        value: '闲置',
        label: '闲置',
    },
    {
        value: '维修中',
        label: '维修中',
    },
    {
        value: '使用中',
        label: '使用中',
    },
    {
        value: '报废',
        label: '报废',
    }
]


const loading = ref(false)
const form = ref<FormData>({...(props.edit ? props.data as FormData : defaultData)});
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

remoteMethod('');

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {

    console.log(response);

    form.value.imgDir = baseUrl + '/fixedAssets/files/' + response.fileName;
    ElMessage.success('上传成功');
    console.log(form.value.imgDir);
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

    console.log(form.value);

    formEl.validate((valid) => {
        if (!valid) return false;
        const action = props.edit ? '/fixedAssets/update/' + form.value.assetId : '/fixedAssets/add';

        console.log(action);

        service.post(action, form.value).then((res) => {
            console.log("res");
            console.log(res);
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

