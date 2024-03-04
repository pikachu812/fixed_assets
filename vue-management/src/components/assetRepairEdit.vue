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

                    <span style="float: left">{{ item.name }}</span>
                    <span
                        style="float: right;
                        color: var(--el-text-color-secondary);
                        font-size: 13px;"
                    >{{ item.status }}</span
                    >

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
        <el-form-item
            label="维修费用"
            prop="cost">
            <el-input-number v-model="form.cost" :precision="2" :step="0.01" :min="0.00" />

        </el-form-item>
<!--        维修明细-->
        <el-form-item label="维修明细" prop="details">
            <el-input v-model="form.details" type="textarea" maxlength="100" show-word-limit></el-input>
        </el-form-item>


<!--        <el-form-item label="资产图片" prop="imgDir">-->
<!--            <el-upload-->
<!--                class="avatar-uploader"-->
<!--                :action="baseUrl+ '/fixedAssets/upload'"-->
<!--                :show-file-list="false"-->
<!--                :on-success="handleAvatarSuccess"-->
<!--                :before-upload="beforeAvatarUpload"-->
<!--            >-->

<!--                <el-image-->
<!--                    v-if="form.imgDir !== undefined"-->
<!--                    style="width: 150px; height: 150px"-->
<!--                    :src="form.imgDir"-->
<!--                    :zoom-rate="1.2"-->
<!--                    :max-scale="7"-->
<!--                    :min-scale="0.2"-->
<!--                    :initial-index="4"-->
<!--                    fit="cover"-->
<!--                />-->
<!--                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>-->
<!--            </el-upload>-->
<!--        </el-form-item>-->

<!--   自动修改状态？   -->



<!--        <el-form-item label="资产状态" prop="description">-->
<!--            <el-select-->
<!--                v-model="form.status"-->
<!--                placeholder="Select"-->
<!--                size="large"-->
<!--                style="width: 240px"-->
<!--            >-->
<!--                <el-option-->
<!--                    v-for="item in statusOptions"-->
<!--                    :key="item.value"-->
<!--                    :label="item.label"-->
<!--                    :value="item.value"-->
<!--                />-->
<!--            </el-select>-->
<!--        </el-form-item>-->
        <el-form-item>
            <el-button type="primary" @click="saveEdit(formRef)">保存</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import {ElMessage, FormInstance} from 'element-plus';
import {ref} from 'vue';
import service from "../utils/request";
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
};


interface FormData {
    repairId: number | null;
    assetId: number | null;
    repairDate: string | null;
    cost: number | null;
    details: string | null;
}
interface ListItem {
    assetId: number;
    assetTypeId: number;
    name: string;
    purchaseDate: Date;
    price: number;
    status: string;
    imgDir: string;
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
                return item.assetName.toLowerCase().includes(query.toLowerCase())
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

