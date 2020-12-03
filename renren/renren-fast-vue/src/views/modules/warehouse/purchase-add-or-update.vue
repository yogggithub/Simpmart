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
            <el-form-item label="priority" prop="priority">
                <el-input placeholder="priority" v-model="dataForm.priority"></el-input>
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
                    assigneeId: '',
                    assigneeName: '',
                    phone: '',
                    priority: '',
                    status: 0,
                    wareId: '',
                    amount: '',
                    createTime: '',
                    updateTime: ''
                },
                dataRule: {
                    assigneeId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    assigneeName: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    priority: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    wareId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    amount: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    updateTime: [
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
                            url: this.$http.adornUrl(`/ware/purchase/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.assigneeId = data.purchase.assigneeId
                                this.dataForm.assigneeName = data.purchase.assigneeName
                                this.dataForm.phone = data.purchase.phone
                                this.dataForm.priority = data.purchase.priority
                                this.dataForm.status = data.purchase.status
                                this.dataForm.wareId = data.purchase.wareId
                                this.dataForm.amount = data.purchase.amount
                                this.dataForm.createTime = data.purchase.createTime
                                this.dataForm.updateTime = data.purchase.updateTime
                            }
                        })
                    }
                })
            },
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/ware/purchase/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'assigneeId': this.dataForm.assigneeId,
                                'assigneeName': this.dataForm.assigneeName,
                                'phone': this.dataForm.phone,
                                'priority': this.dataForm.priority,
                                'status': this.dataForm.status,
                                'wareId': this.dataForm.wareId,
                                'amount': this.dataForm.amount,
                                'createTime': this.dataForm.createTime,
                                'updateTime': this.dataForm.updateTime
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
