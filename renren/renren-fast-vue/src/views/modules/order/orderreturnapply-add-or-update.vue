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
            <el-form-item label="退货商品id" prop="skuId">
                <el-input placeholder="退货商品id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="订单编号" prop="orderSn">
                <el-input placeholder="订单编号" v-model="dataForm.orderSn"></el-input>
            </el-form-item>
            <el-form-item label="申请时间" prop="createTime">
                <el-input placeholder="申请时间" v-model="dataForm.createTime"></el-input>
            </el-form-item>
            <el-form-item label="会员用户名" prop="memberUsername">
                <el-input placeholder="会员用户名" v-model="dataForm.memberUsername"></el-input>
            </el-form-item>
            <el-form-item label="退款金额" prop="returnAmount">
                <el-input placeholder="退款金额" v-model="dataForm.returnAmount"></el-input>
            </el-form-item>
            <el-form-item label="退货人姓名" prop="returnName">
                <el-input placeholder="退货人姓名" v-model="dataForm.returnName"></el-input>
            </el-form-item>
            <el-form-item label="退货人电话" prop="returnPhone">
                <el-input placeholder="退货人电话" v-model="dataForm.returnPhone"></el-input>
            </el-form-item>
            <el-form-item label="申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]" prop="status">
                <el-input placeholder="申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]" v-model="dataForm.status"></el-input>
            </el-form-item>
            <el-form-item label="处理时间" prop="handleTime">
                <el-input placeholder="处理时间" v-model="dataForm.handleTime"></el-input>
            </el-form-item>
            <el-form-item label="商品图片" prop="skuImg">
                <el-input placeholder="商品图片" v-model="dataForm.skuImg"></el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="skuName">
                <el-input placeholder="商品名称" v-model="dataForm.skuName"></el-input>
            </el-form-item>
            <el-form-item label="商品品牌" prop="skuBrand">
                <el-input placeholder="商品品牌" v-model="dataForm.skuBrand"></el-input>
            </el-form-item>
            <el-form-item label="商品销售属性(JSON)" prop="skuAttrsVals">
                <el-input placeholder="商品销售属性(JSON)" v-model="dataForm.skuAttrsVals"></el-input>
            </el-form-item>
            <el-form-item label="退货数量" prop="skuCount">
                <el-input placeholder="退货数量" v-model="dataForm.skuCount"></el-input>
            </el-form-item>
            <el-form-item label="商品单价" prop="skuPrice">
                <el-input placeholder="商品单价" v-model="dataForm.skuPrice"></el-input>
            </el-form-item>
            <el-form-item label="商品实际支付单价" prop="skuRealPrice">
                <el-input placeholder="商品实际支付单价" v-model="dataForm.skuRealPrice"></el-input>
            </el-form-item>
            <el-form-item label="原因" prop="reason">
                <el-input placeholder="原因" v-model="dataForm.reason"></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description述">
                <el-input placeholder="描述" v-model="dataForm.description述"></el-input>
            </el-form-item>
            <el-form-item label="凭证图片，以逗号隔开" prop="descPics">
                <el-input placeholder="凭证图片，以逗号隔开" v-model="dataForm.descPics"></el-input>
            </el-form-item>
            <el-form-item label="处理备注" prop="handleNote">
                <el-input placeholder="处理备注" v-model="dataForm.handleNote"></el-input>
            </el-form-item>
            <el-form-item label="处理人员" prop="handleMan">
                <el-input placeholder="处理人员" v-model="dataForm.handleMan"></el-input>
            </el-form-item>
            <el-form-item label="收货人" prop="receiveMan">
                <el-input placeholder="收货人" v-model="dataForm.receiveMan"></el-input>
            </el-form-item>
            <el-form-item label="收货时间" prop="receiveTime">
                <el-input placeholder="收货时间" v-model="dataForm.receiveTime"></el-input>
            </el-form-item>
            <el-form-item label="收货备注" prop="receiveNote">
                <el-input placeholder="收货备注" v-model="dataForm.receiveNote"></el-input>
            </el-form-item>
            <el-form-item label="收货电话" prop="receivePhone">
                <el-input placeholder="收货电话" v-model="dataForm.receivePhone"></el-input>
            </el-form-item>
            <el-form-item label="公司收货地址" prop="companyAddress">
                <el-input placeholder="公司收货地址" v-model="dataForm.companyAddress"></el-input>
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
                    skuId: '',
                    orderSn: '',
                    createTime: '',
                    memberUsername: '',
                    returnAmount: '',
                    returnName: '',
                    returnPhone: '',
                    status: '',
                    handleTime: '',
                    skuImg: '',
                    skuName: '',
                    skuBrand: '',
                    skuAttrsVals: '',
                    skuCount: '',
                    skuPrice: '',
                    skuRealPrice: '',
                    reason: '',
                    description述: '',
                    descPics: '',
                    handleNote: '',
                    handleMan: '',
                    receiveMan: '',
                    receiveTime: '',
                    receiveNote: '',
                    receivePhone: '',
                    companyAddress: ''
                },
                dataRule: {
                    orderId: [
                        { required: true, message: 'order_id不能为空', trigger: 'blur' }
                    ],
                    skuId: [
                        { required: true, message: '退货商品id不能为空', trigger: 'blur' }
                    ],
                    orderSn: [
                        { required: true, message: '订单编号不能为空', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: '申请时间不能为空', trigger: 'blur' }
                    ],
                    memberUsername: [
                        { required: true, message: '会员用户名不能为空', trigger: 'blur' }
                    ],
                    returnAmount: [
                        { required: true, message: '退款金额不能为空', trigger: 'blur' }
                    ],
                    returnName: [
                        { required: true, message: '退货人姓名不能为空', trigger: 'blur' }
                    ],
                    returnPhone: [
                        { required: true, message: '退货人电话不能为空', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]不能为空', trigger: 'blur' }
                    ],
                    handleTime: [
                        { required: true, message: '处理时间不能为空', trigger: 'blur' }
                    ],
                    skuImg: [
                        { required: true, message: '商品图片不能为空', trigger: 'blur' }
                    ],
                    skuName: [
                        { required: true, message: '商品名称不能为空', trigger: 'blur' }
                    ],
                    skuBrand: [
                        { required: true, message: '商品品牌不能为空', trigger: 'blur' }
                    ],
                    skuAttrsVals: [
                        { required: true, message: '商品销售属性(JSON)不能为空', trigger: 'blur' }
                    ],
                    skuCount: [
                        { required: true, message: '退货数量不能为空', trigger: 'blur' }
                    ],
                    skuPrice: [
                        { required: true, message: '商品单价不能为空', trigger: 'blur' }
                    ],
                    skuRealPrice: [
                        { required: true, message: '商品实际支付单价不能为空', trigger: 'blur' }
                    ],
                    reason: [
                        { required: true, message: '原因不能为空', trigger: 'blur' }
                    ],
                    description述: [
                        { required: true, message: '描述不能为空', trigger: 'blur' }
                    ],
                    descPics: [
                        { required: true, message: '凭证图片，以逗号隔开不能为空', trigger: 'blur' }
                    ],
                    handleNote: [
                        { required: true, message: '处理备注不能为空', trigger: 'blur' }
                    ],
                    handleMan: [
                        { required: true, message: '处理人员不能为空', trigger: 'blur' }
                    ],
                    receiveMan: [
                        { required: true, message: '收货人不能为空', trigger: 'blur' }
                    ],
                    receiveTime: [
                        { required: true, message: '收货时间不能为空', trigger: 'blur' }
                    ],
                    receiveNote: [
                        { required: true, message: '收货备注不能为空', trigger: 'blur' }
                    ],
                    receivePhone: [
                        { required: true, message: '收货电话不能为空', trigger: 'blur' }
                    ],
                    companyAddress: [
                        { required: true, message: '公司收货地址不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/order/orderreturnapply/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.orderId = data.orderReturnApply.orderId
                                this.dataForm.skuId = data.orderReturnApply.skuId
                                this.dataForm.orderSn = data.orderReturnApply.orderSn
                                this.dataForm.createTime = data.orderReturnApply.createTime
                                this.dataForm.memberUsername = data.orderReturnApply.memberUsername
                                this.dataForm.returnAmount = data.orderReturnApply.returnAmount
                                this.dataForm.returnName = data.orderReturnApply.returnName
                                this.dataForm.returnPhone = data.orderReturnApply.returnPhone
                                this.dataForm.status = data.orderReturnApply.status
                                this.dataForm.handleTime = data.orderReturnApply.handleTime
                                this.dataForm.skuImg = data.orderReturnApply.skuImg
                                this.dataForm.skuName = data.orderReturnApply.skuName
                                this.dataForm.skuBrand = data.orderReturnApply.skuBrand
                                this.dataForm.skuAttrsVals = data.orderReturnApply.skuAttrsVals
                                this.dataForm.skuCount = data.orderReturnApply.skuCount
                                this.dataForm.skuPrice = data.orderReturnApply.skuPrice
                                this.dataForm.skuRealPrice = data.orderReturnApply.skuRealPrice
                                this.dataForm.reason = data.orderReturnApply.reason
                                this.dataForm.description述 = data.orderReturnApply.description述
                                this.dataForm.descPics = data.orderReturnApply.descPics
                                this.dataForm.handleNote = data.orderReturnApply.handleNote
                                this.dataForm.handleMan = data.orderReturnApply.handleMan
                                this.dataForm.receiveMan = data.orderReturnApply.receiveMan
                                this.dataForm.receiveTime = data.orderReturnApply.receiveTime
                                this.dataForm.receiveNote = data.orderReturnApply.receiveNote
                                this.dataForm.receivePhone = data.orderReturnApply.receivePhone
                                this.dataForm.companyAddress = data.orderReturnApply.companyAddress
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
                            url: this.$http.adornUrl(`/order/orderreturnapply/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'orderId': this.dataForm.orderId,
                                'skuId': this.dataForm.skuId,
                                'orderSn': this.dataForm.orderSn,
                                'createTime': this.dataForm.createTime,
                                'memberUsername': this.dataForm.memberUsername,
                                'returnAmount': this.dataForm.returnAmount,
                                'returnName': this.dataForm.returnName,
                                'returnPhone': this.dataForm.returnPhone,
                                'status': this.dataForm.status,
                                'handleTime': this.dataForm.handleTime,
                                'skuImg': this.dataForm.skuImg,
                                'skuName': this.dataForm.skuName,
                                'skuBrand': this.dataForm.skuBrand,
                                'skuAttrsVals': this.dataForm.skuAttrsVals,
                                'skuCount': this.dataForm.skuCount,
                                'skuPrice': this.dataForm.skuPrice,
                                'skuRealPrice': this.dataForm.skuRealPrice,
                                'reason': this.dataForm.reason,
                                'description述': this.dataForm.description述,
                                'descPics': this.dataForm.descPics,
                                'handleNote': this.dataForm.handleNote,
                                'handleMan': this.dataForm.handleMan,
                                'receiveMan': this.dataForm.receiveMan,
                                'receiveTime': this.dataForm.receiveTime,
                                'receiveNote': this.dataForm.receiveNote,
                                'receivePhone': this.dataForm.receivePhone,
                                'companyAddress': this.dataForm.companyAddress
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
