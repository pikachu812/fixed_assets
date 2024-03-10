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
                    :disabled="item.assetId !== form.assetId"
                >

                    <span style="float: left">{{item.assetId}}.{{ item.name }}</span>

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
        <el-form-item label="维修日期" prop="repairDate">
            <el-date-picker
                v-model="form.repairDate"
                type="date"
                placeholder="请选择维修日期"
                style="width: 100%"
            />
        </el-form-item>
<!--    注意cost和price的区别  -->
<!--        <el-form-item-->
<!--            label="维修费用"-->
<!--            prop="cost">-->
<!--            <el-input-number v-model="form.cost" :precision="2" :step="0.01" :min="0.00" />-->

<!--        </el-form-item>-->
<!--        损坏原因-->
        <el-form-item label="损坏原因" prop="details">
            <el-input v-model="form.details" type="textarea" maxlength="100" show-word-limit></el-input>
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
import {AssetRepair, FixedAsset} from "../interface/interface";
//导入全局常量baseUrl


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
    repairId: null,
    assetId: null,
    repairDate: null,
    cost: null,
    details: null,
    status: null,
    fixedAsset: {
        assetId: null,
        assetTypeId: null,
        name: "",
        purchaseDate: null,
        price: 0,
        imgDir: "",
        status: "",
        usefulYear: 0,
        assetType: {
            assetTypeId: 0,
            typeName: "",
            description: ""
        }
    }
};


interface FormData extends AssetRepair{
}
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
        // const action = props.edit ? '/assetRepair/update/' + form.value.repairId : '/assetRepair/add';

        const action = '/assetRepair/add';


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

