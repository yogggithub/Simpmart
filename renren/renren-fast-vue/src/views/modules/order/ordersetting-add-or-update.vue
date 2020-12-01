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
            <el-form-item label="秒杀订单超时关闭时间(分)" prop="flashOrderOvertime">
                <el-input placeholder="秒杀订单超时关闭时间(分)" v-model="dataForm.flashOrderOvertime"></el-input>
            </el-form-item>
            <el-form-item label="正常订单超时时间(分)" prop="normalOrderOvertime">
                <el-input placeholder="正常订单超时时间(分)" v-model="dataForm.normalOrderOvertime"></el-input>
            </el-form-item>
            <el-form-item label="发货后自动确认收货时间（天）" prop="confirmOvertime">
                <el-input placeholder="发货后自动确认收货时间（天）" v-model="dataForm.confirmOvertime"></el-input>
            </el-form-item>
            <el-form-item label="自动完成交易时间，不能申请退货（天）" prop="finishOvertime">
                <el-input placeholder="自动完成交易时间，不能申请退货（天）" v-model="dataForm.finishOvertime"></el-input>
            </el-form-item>
            <el-form-item label="订单完成后自动好评时间（天）" prop="commentOvertime">
                <el-input placeholder="订单完成后自动好评时间（天）" v-model="dataForm.commentOvertime"></el-input>
            </el-form-item>
            <el-form-item label="会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】" prop="memberLevel">
                <el-input
                    placeholder="会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】"
                    v-model="dataForm.memberLevel"
                ></el-input>
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
                    flashOrderOvertime: '',
                    normalOrderOvertime: '',
                    confirmOvertime: '',
                    finishOvertime: '',
                    commentOvertime: '',
                    memberLevel: ''
                },
                dataRule: {
                    flashOrderOvertime: [
                        { required: true, message: '秒杀订单超时关闭时间(分)不能为空', trigger: 'blur' }
                    ],
                    normalOrderOvertime: [
                        { required: true, message: '正常订单超时时间(分)不能为空', trigger: 'blur' }
                    ],
                    confirmOvertime: [
                        { required: true, message: '发货后自动确认收货时间（天）不能为空', trigger: 'blur' }
                    ],
                    finishOvertime: [
                        { required: true, message: '自动完成交易时间，不能申请退货（天）不能为空', trigger: 'blur' }
                    ],
                    commentOvertime: [
                        { required: true, message: '订单完成后自动好评时间（天）不能为空', trigger: 'blur' }
                    ],
                    memberLevel: [
                        { required: true, message: '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/order/ordersetting/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.flashOrderOvertime = data.orderSetting.flashOrderOvertime
                                this.dataForm.normalOrderOvertime = data.orderSetting.normalOrderOvertime
                                this.dataForm.confirmOvertime = data.orderSetting.confirmOvertime
                                this.dataForm.finishOvertime = data.orderSetting.finishOvertime
                                this.dataForm.commentOvertime = data.orderSetting.commentOvertime
                                this.dataForm.memberLevel = data.orderSetting.memberLevel
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
                            url: this.$http.adornUrl(`/order/ordersetting/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'flashOrderOvertime': this.dataForm.flashOrderOvertime,
                                'normalOrderOvertime': this.dataForm.normalOrderOvertime,
                                'confirmOvertime': this.dataForm.confirmOvertime,
                                'finishOvertime': this.dataForm.finishOvertime,
                                'commentOvertime': this.dataForm.commentOvertime,
                                'memberLevel': this.dataForm.memberLevel
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
