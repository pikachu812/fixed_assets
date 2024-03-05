<template>
    <el-form  ref="formRef" :model="form" label-width="100px">
        <el-form-item label="资产名称" prop="assetId">
            <el-select
                v-model="form.assetId"
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
                    :key="item.assetId"
                    :label="item.name"
                    :value="item.assetId"
                    :disabled="item.status !== '闲置'"
                >

                    <span style="float: left">{{ item.assetId }}-{{ item.name }}</span>

                    <span
                        style="float: right;
                        color: var(--el-text-color-secondary);
                        font-size: 13px;"
                    >
                        <el-tag :type="statusToCss[item.status] ? statusToCss[item.status] : 'danger'">
                            {{ item.status }}
                        </el-tag>
                    </span>

                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="报废日期" prop="scrapDate">
            <el-date-picker
                v-model="form.scrapDate"
                type="date"
                placeholder="请选择报废日期"
                style="width: 100%"
            />
        </el-form-item>
        <el-form-item label="报废原因" prop="details">
            <el-input v-model="form.reason" type="textarea" maxlength="100" show-word-limit></el-input>
        </el-form-item>


        <el-form-item>
            <el-button type="primary" @click="saveEdit(formRef)">保存</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import {ElMessage, FormInstance} from 'element-plus';
import {ref} from 'vue';
import service from "../utils/request";
import {AssetScrap, FixedAsset} from "../interface/interface";


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
    scrapId: 0,
    assetId: null,
    scrapDate: null,
    reason: '',
    fixedAsset: {
        assetId: 0,
        assetTypeId: 0,
        name: "",
        purchaseDate: null,
        price: 0,
        imgDir: "",
        status: "",
        assetType: {
            assetTypeId: 0,
            typeName: "",
            description: ""
        }
    }
};


interface FormData extends AssetScrap{}
interface ListItem extends FixedAsset{}


const statusToCss = {
    "闲置": "success",
    "维修中": "warning",
    "使用中": "info",
    "报废": "danger"
}

const loading = ref(false)
const form = ref<FormData>({...(props.edit ? props.data as FormData : defaultData)});
const list = ref<ListItem[]>([])
const options = ref<ListItem[]>([])
const value = ref<string[]>([])
const formRef = ref<FormInstance>();

const remoteMethod = (query: string) => {

    loading.value = true
    service.post("/fixedAssets/getFixedAssetsByCondition", {
    }).then((res) => {
        loading.value = false

        if (query) {
            options.value = res.data.filter((item) => {
                return item.name.toLowerCase().includes(query.toLowerCase())
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

const saveEdit = (formEl: FormInstance | undefined) => {
    if (!formEl) return;

    console.log(form.value);

    formEl.validate((valid) => {
        if (!valid) return false;
        const action = props.edit ? '/assetScrap/update/' + form.value.scrapId : '/assetScrap/add';

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

