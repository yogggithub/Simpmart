<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? 'Add' : 'Update'"
        :visible.sync="visible"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            @keyup.enter.native="dataFormSubmit()"
            label-width="80px"
            ref="dataForm"
        >
            <el-form-item label="spu name" prop="spuName">
                <el-input placeholder="spu name" v-model="dataForm.spuName"></el-input>
            </el-form-item>
            <el-form-item label="spu description" prop="spuDescription">
                <el-input placeholder="spu description" v-model="dataForm.spuDescription"></el-input>
            </el-form-item>
            <el-form-item label="category id" prop="catalogId">
                <el-input placeholder="category id" v-model="dataForm.catalogId"></el-input>
            </el-form-item>
            <el-form-item label="brand id" prop="brandId">
                <el-input placeholder="brand id" v-model="dataForm.brandId"></el-input>
            </el-form-item>
            <el-form-item label="product weight" prop="weight">
                <el-input placeholder="product weight" v-model="dataForm.weight"></el-input>
            </el-form-item>
            <el-form-item label="Is the product on sale [0->no, 1->yes]" prop="publishStatus">
                <el-input
                    placeholder="Is the product on sale [0->no, 1->yes]"
                    v-model="dataForm.publishStatus"
                ></el-input>
            </el-form-item>
            <el-form-item label="spu create time" prop="createTime">
                <el-input placeholder="spu create time" v-model="dataForm.createTime"></el-input>
            </el-form-item>
            <el-form-item label="spu update time" prop="updateTime">
                <el-input placeholder="spu update time" v-model="dataForm.updateTime"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">Cancel</el-button>
            <el-button @click="dataFormSubmit()" type="primary">Confirm</el-button>
        </span>
    </el-dialog>
</template>

<script>
    export default {
        data () {
            return {
                visible: false,
                dataForm: {
                    id: 0,
                    spuName: '',
                    spuDescription: '',
                    catalogId: '',
                    brandId: '',
                    weight: '',
                    publishStatus: '',
                    createTime: '',
                    updateTime: ''
                },
                dataRule: {
                    spuName: [
                        { required: true, message: 'spu name can not be empty', trigger: 'blur' }
                    ],
                    spuDescription: [
                        { required: true, message: 'spu description can not be empty', trigger: 'blur' }
                    ],
                    catalogId: [
                        { required: true, message: 'category id can not be empty', trigger: 'blur' }
                    ],
                    brandId: [
                        { required: true, message: 'brand id can not be empty', trigger: 'blur' }
                    ],
                    weight: [
                        { required: true, message: 'product weight can not be empty', trigger: 'blur' }
                    ],
                    publishStatus: [
                        { required: true, message: 'Is the product on sale [0->no, 1->yes] can not be empty', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: 'spu create time can not be empty', trigger: 'blur' }
                    ],
                    updateTime: [
                        { required: true, message: 'spu update time can not be empty', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init (id) {
                this.dataForm.id = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.id) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/spuinfo/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.spuName = data.spuInfo.spuName
                                this.dataForm.spuDescription = data.spuInfo.spuDescription
                                this.dataForm.catalogId = data.spuInfo.catalogId
                                this.dataForm.brandId = data.spuInfo.brandId
                                this.dataForm.weight = data.spuInfo.weight
                                this.dataForm.publishStatus = data.spuInfo.publishStatus
                                this.dataForm.createTime = data.spuInfo.createTime
                                this.dataForm.updateTime = data.spuInfo.updateTime
                            }
                        })
                    }
                })
            },
            // Form submission
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/spuinfo/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'spuName': this.dataForm.spuName,
                                'spuDescription': this.dataForm.spuDescription,
                                'catalogId': this.dataForm.catalogId,
                                'brandId': this.dataForm.brandId,
                                'weight': this.dataForm.weight,
                                'publishStatus': this.dataForm.publishStatus,
                                'createTime': this.dataForm.createTime,
                                'updateTime': this.dataForm.updateTime
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: 'Completed',
                                    type: 'success',
                                    duration: 1500,
                                    onClose: () => {
                                        this.visible = false
                                        this.$emit('refreshDataList')
                                    }
                                })
                            } else {
                                this.$message.error(data.msg)
                            }
                        })
                    }
                })
            }
        }
    }
</script>
