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
            label-width="120px"
            ref="dataForm"
        >
            <el-form-item label="sku id" prop="skuId">
                <el-input placeholder="sku id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="sku name" prop="skuName">
                <el-input placeholder="sku name" v-model="dataForm.skuName"></el-input>
            </el-form-item>
            <el-form-item label="order quantity" prop="skuNum">
                <el-input placeholder="order quantity" v-model="dataForm.skuNum"></el-input>
            </el-form-item>
            <el-form-item label="task id" prop="taskId">
                <el-input placeholder="task id" v-model="dataForm.taskId"></el-input>
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
                    skuId: '',
                    skuName: '',
                    skuNum: '',
                    taskId: ''
                },
                dataRule: {
                    skuId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    skuName: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    skuNum: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    taskId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/ware/wareordertaskdetail/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.skuId = data.wareOrderTaskDetail.skuId
                                this.dataForm.skuName = data.wareOrderTaskDetail.skuName
                                this.dataForm.skuNum = data.wareOrderTaskDetail.skuNum
                                this.dataForm.taskId = data.wareOrderTaskDetail.taskId
                            }
                        })
                    }
                })
            },
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/ware/wareordertaskdetail/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'skuId': this.dataForm.skuId,
                                'skuName': this.dataForm.skuName,
                                'skuNum': this.dataForm.skuNum,
                                'taskId': this.dataForm.taskId
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: 'Successfully',
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
