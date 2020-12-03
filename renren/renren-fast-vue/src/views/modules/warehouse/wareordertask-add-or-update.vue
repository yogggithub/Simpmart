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
            <el-form-item label="order_id" prop="orderId">
                <el-input placeholder="order_id" v-model="dataForm.orderId"></el-input>
            </el-form-item>
            <el-form-item label="order_sn" prop="orderSn">
                <el-input placeholder="order_sn" v-model="dataForm.orderSn"></el-input>
            </el-form-item>
            <el-form-item label="consignee" prop="consignee">
                <el-input placeholder="consignee" v-model="dataForm.consignee"></el-input>
            </el-form-item>
            <el-form-item label="consignee phone" prop="consigneeTel">
                <el-input placeholder="consignee phone" v-model="dataForm.consigneeTel"></el-input>
            </el-form-item>
            <el-form-item label="delivery address" prop="deliveryAddress">
                <el-input placeholder="delivery address" v-model="dataForm.deliveryAddress"></el-input>
            </el-form-item>
            <el-form-item label="order comment" prop="orderComment">
                <el-input placeholder="order comment" v-model="dataForm.orderComment"></el-input>
            </el-form-item>
            <el-form-item label="payment" prop="paymentWay">
                <el-input placeholder="payment" v-model="dataForm.paymentWay"></el-input>
            </el-form-item>
            <el-form-item label="task status" prop="taskStatus">
                <el-input placeholder="task status" v-model="dataForm.taskStatus"></el-input>
            </el-form-item>
            <el-form-item label="order description" prop="orderBody">
                <el-input placeholder="order description" v-model="dataForm.orderBody"></el-input>
            </el-form-item>
            <el-form-item label="delivery tracking" prop="trackingNo">
                <el-input placeholder="delivery tracking" v-model="dataForm.trackingNo"></el-input>
            </el-form-item>
            <el-form-item label="create_time" prop="createTime">
                <el-input placeholder="create_time" v-model="dataForm.createTime"></el-input>
            </el-form-item>
            <el-form-item label="warehouse id" prop="wareId">
                <el-input placeholder="warehouseid" v-model="dataForm.wareId"></el-input>
            </el-form-item>
            <el-form-item label="task comment" prop="taskComment">
                <el-input placeholder="task comment" v-model="dataForm.taskComment"></el-input>
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
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    orderSn: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    consignee: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    consigneeTel: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    deliveryAddress: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    orderComment: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    paymentWay: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    taskStatus: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    orderBody: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    trackingNo: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    wareId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    taskComment: [
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
