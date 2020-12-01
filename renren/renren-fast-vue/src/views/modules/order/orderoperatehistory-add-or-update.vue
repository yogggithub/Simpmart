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
            <el-form-item label="订单id" prop="orderId">
                <el-input placeholder="订单id" v-model="dataForm.orderId"></el-input>
            </el-form-item>
            <el-form-item label="操作人[用户；系统；后台管理员]" prop="operateMan">
                <el-input placeholder="操作人[用户；系统；后台管理员]" v-model="dataForm.operateMan"></el-input>
            </el-form-item>
            <el-form-item label="操作时间" prop="createTime">
                <el-input placeholder="操作时间" v-model="dataForm.createTime"></el-input>
            </el-form-item>
            <el-form-item
                label="订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】"
                prop="orderStatus"
            >
                <el-input
                    placeholder="订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】"
                    v-model="dataForm.orderStatus"
                ></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="note">
                <el-input placeholder="备注" v-model="dataForm.note"></el-input>
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
                    orderId: '',
                    operateMan: '',
                    createTime: '',
                    orderStatus: '',
                    note: ''
                },
                dataRule: {
                    orderId: [
                        { required: true, message: '订单id不能为空', trigger: 'blur' }
                    ],
                    operateMan: [
                        { required: true, message: '操作人[用户；系统；后台管理员]不能为空', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: '操作时间不能为空', trigger: 'blur' }
                    ],
                    orderStatus: [
                        { required: true, message: '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】不能为空', trigger: 'blur' }
                    ],
                    note: [
                        { required: true, message: '备注不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/order/orderoperatehistory/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.orderId = data.orderOperateHistory.orderId
                                this.dataForm.operateMan = data.orderOperateHistory.operateMan
                                this.dataForm.createTime = data.orderOperateHistory.createTime
                                this.dataForm.orderStatus = data.orderOperateHistory.orderStatus
                                this.dataForm.note = data.orderOperateHistory.note
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
                            url: this.$http.adornUrl(`/order/orderoperatehistory/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'orderId': this.dataForm.orderId,
                                'operateMan': this.dataForm.operateMan,
                                'createTime': this.dataForm.createTime,
                                'orderStatus': this.dataForm.orderStatus,
                                'note': this.dataForm.note
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
