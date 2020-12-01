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
            <el-form-item label="order_id" prop="orderId">
                <el-input placeholder="order_id" v-model="dataForm.orderId"></el-input>
            </el-form-item>
            <el-form-item label="order_sn" prop="orderSn">
                <el-input placeholder="order_sn" v-model="dataForm.orderSn"></el-input>
            </el-form-item>
            <el-form-item label="收货人" prop="consignee">
                <el-input placeholder="收货人" v-model="dataForm.consignee"></el-input>
            </el-form-item>
            <el-form-item label="收货人电话" prop="consigneeTel">
                <el-input placeholder="收货人电话" v-model="dataForm.consigneeTel"></el-input>
            </el-form-item>
            <el-form-item label="配送地址" prop="deliveryAddress">
                <el-input placeholder="配送地址" v-model="dataForm.deliveryAddress"></el-input>
            </el-form-item>
            <el-form-item label="订单备注" prop="orderComment">
                <el-input placeholder="订单备注" v-model="dataForm.orderComment"></el-input>
            </el-form-item>
            <el-form-item label="付款方式【 1:在线付款 2:货到付款】" prop="paymentWay">
                <el-input placeholder="付款方式【 1:在线付款 2:货到付款】" v-model="dataForm.paymentWay"></el-input>
            </el-form-item>
            <el-form-item label="任务状态" prop="taskStatus">
                <el-input placeholder="任务状态" v-model="dataForm.taskStatus"></el-input>
            </el-form-item>
            <el-form-item label="订单描述" prop="orderBody">
                <el-input placeholder="订单描述" v-model="dataForm.orderBody"></el-input>
            </el-form-item>
            <el-form-item label="物流单号" prop="trackingNo">
                <el-input placeholder="物流单号" v-model="dataForm.trackingNo"></el-input>
            </el-form-item>
            <el-form-item label="create_time" prop="createTime">
                <el-input placeholder="create_time" v-model="dataForm.createTime"></el-input>
            </el-form-item>
            <el-form-item label="仓库id" prop="wareId">
                <el-input placeholder="仓库id" v-model="dataForm.wareId"></el-input>
            </el-form-item>
            <el-form-item label="工作单备注" prop="taskComment">
                <el-input placeholder="工作单备注" v-model="dataForm.taskComment"></el-input>
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
                    orderSn: '',
                    consignee: '',
                    consigneeTel: '',
                    deliveryAddress: '',
                    orderComment: '',
                    paymentWay: '',
                    taskStatus: '',
                    orderBody: '',
                    trackingNo: '',
                    createTime: '',
                    wareId: '',
                    taskComment: ''
                },
                dataRule: {
                    orderId: [
                        { required: true, message: 'order_id不能为空', trigger: 'blur' }
                    ],
                    orderSn: [
                        { required: true, message: 'order_sn不能为空', trigger: 'blur' }
                    ],
                    consignee: [
                        { required: true, message: '收货人不能为空', trigger: 'blur' }
                    ],
                    consigneeTel: [
                        { required: true, message: '收货人电话不能为空', trigger: 'blur' }
                    ],
                    deliveryAddress: [
                        { required: true, message: '配送地址不能为空', trigger: 'blur' }
                    ],
                    orderComment: [
                        { required: true, message: '订单备注不能为空', trigger: 'blur' }
                    ],
                    paymentWay: [
                        { required: true, message: '付款方式【 1:在线付款 2:货到付款】不能为空', trigger: 'blur' }
                    ],
                    taskStatus: [
                        { required: true, message: '任务状态不能为空', trigger: 'blur' }
                    ],
                    orderBody: [
                        { required: true, message: '订单描述不能为空', trigger: 'blur' }
                    ],
                    trackingNo: [
                        { required: true, message: '物流单号不能为空', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: 'create_time不能为空', trigger: 'blur' }
                    ],
                    wareId: [
                        { required: true, message: '仓库id不能为空', trigger: 'blur' }
                    ],
                    taskComment: [
                        { required: true, message: '工作单备注不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/ware/wareordertask/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.orderId = data.wareOrderTask.orderId
                                this.dataForm.orderSn = data.wareOrderTask.orderSn
                                this.dataForm.consignee = data.wareOrderTask.consignee
                                this.dataForm.consigneeTel = data.wareOrderTask.consigneeTel
                                this.dataForm.deliveryAddress = data.wareOrderTask.deliveryAddress
                                this.dataForm.orderComment = data.wareOrderTask.orderComment
                                this.dataForm.paymentWay = data.wareOrderTask.paymentWay
                                this.dataForm.taskStatus = data.wareOrderTask.taskStatus
                                this.dataForm.orderBody = data.wareOrderTask.orderBody
                                this.dataForm.trackingNo = data.wareOrderTask.trackingNo
                                this.dataForm.createTime = data.wareOrderTask.createTime
                                this.dataForm.wareId = data.wareOrderTask.wareId
                                this.dataForm.taskComment = data.wareOrderTask.taskComment
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
                            url: this.$http.adornUrl(`/ware/wareordertask/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'orderId': this.dataForm.orderId,
                                'orderSn': this.dataForm.orderSn,
                                'consignee': this.dataForm.consignee,
                                'consigneeTel': this.dataForm.consigneeTel,
                                'deliveryAddress': this.dataForm.deliveryAddress,
                                'orderComment': this.dataForm.orderComment,
                                'paymentWay': this.dataForm.paymentWay,
                                'taskStatus': this.dataForm.taskStatus,
                                'orderBody': this.dataForm.orderBody,
                                'trackingNo': this.dataForm.trackingNo,
                                'createTime': this.dataForm.createTime,
                                'wareId': this.dataForm.wareId,
                                'taskComment': this.dataForm.taskComment
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
