<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? '新增' : '修改'"
        :visible.sync="visible"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            @keyup.enter.native="dataFormSubmit()"
            label-width="120px"
            ref="dataForm"
        >
            <el-form-item label="退货原因名" prop="name">
                <el-input placeholder="退货原因名" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
                <el-input placeholder="排序" v-model="dataForm.sort"></el-input>
            </el-form-item>
            <el-form-item label="启用状态" prop="status">
                <el-input placeholder="启用状态" v-model="dataForm.status"></el-input>
            </el-form-item>
            <el-form-item label="create_time" prop="createTime">
                <el-input placeholder="create_time" v-model="dataForm.createTime"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button @click="dataFormSubmit()" type="primary">确定</el-button>
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
                    name: '',
                    sort: '',
                    status: '',
                    createTime: ''
                },
                dataRule: {
                    name: [
                        { required: true, message: '退货原因名不能为空', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: '排序不能为空', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '启用状态不能为空', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: 'create_time不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/order/orderreturnreason/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.orderReturnReason.name
                                this.dataForm.sort = data.orderReturnReason.sort
                                this.dataForm.status = data.orderReturnReason.status
                                this.dataForm.createTime = data.orderReturnReason.createTime
                            }
                        })
                    }
                })
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/order/orderreturnreason/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'name': this.dataForm.name,
                                'sort': this.dataForm.sort,
                                'status': this.dataForm.status,
                                'createTime': this.dataForm.createTime
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: '操作成功',
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
