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
            <el-form-item label="member_id" prop="memberId">
                <el-input placeholder="member_id" v-model="dataForm.memberId"></el-input>
            </el-form-item>
            <el-form-item label="sku_id" prop="skuId">
                <el-input placeholder="sku_id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="活动场次id" prop="sessionId">
                <el-input placeholder="活动场次id" v-model="dataForm.sessionId"></el-input>
            </el-form-item>
            <el-form-item label="订阅时间" prop="subcribeTime">
                <el-input placeholder="订阅时间" v-model="dataForm.subcribeTime"></el-input>
            </el-form-item>
            <el-form-item label="发送时间" prop="sendTime">
                <el-input placeholder="发送时间" v-model="dataForm.sendTime"></el-input>
            </el-form-item>
            <el-form-item label="通知方式" prop="noticeType">
                <el-select placeholder="请选择" v-model="dataForm.noticeType">
                    <el-option :value="0" label="短信"></el-option>
                    <el-option :value="1" label="邮件"></el-option>
                </el-select>
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
                    memberId: '',
                    skuId: '',
                    sessionId: '',
                    subcribeTime: '',
                    sendTime: '',
                    noticeType: ''
                },
                dataRule: {
                    memberId: [
                        { required: true, message: 'member_id不能为空', trigger: 'blur' }
                    ],
                    skuId: [{ required: true, message: 'sku_id不能为空', trigger: 'blur' }],
                    sessionId: [
                        { required: true, message: '活动场次id不能为空', trigger: 'blur' }
                    ],
                    subcribeTime: [
                        { required: true, message: '订阅时间不能为空', trigger: 'blur' }
                    ],
                    sendTime: [
                        { required: true, message: '发送时间不能为空', trigger: 'blur' }
                    ],
                    noticeType: [
                        {
                            required: true,
                            message: '通知方式不能为空',
                            trigger: 'blur'
                        }
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
                            url: this.$http.adornUrl(
                                `/coupon/seckillskunotice/info/${this.dataForm.id}`
                            ),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.memberId = data.seckillSkuNotice.memberId
                                this.dataForm.skuId = data.seckillSkuNotice.skuId
                                this.dataForm.sessionId = data.seckillSkuNotice.sessionId
                                this.dataForm.subcribeTime = data.seckillSkuNotice.subcribeTime
                                this.dataForm.sendTime = data.seckillSkuNotice.sendTime
                                this.dataForm.noticeType = data.seckillSkuNotice.noticeType
                            }
                        })
                    }
                })
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(
                                `/coupon/seckillskunotice/${!this.dataForm.id ? 'save' : 'update'
                                }`
                            ),
                            method: 'post',
                            data: this.$http.adornData({
                                id: this.dataForm.id || undefined,
                                memberId: this.dataForm.memberId,
                                skuId: this.dataForm.skuId,
                                sessionId: this.dataForm.sessionId,
                                subcribeTime: this.dataForm.subcribeTime,
                                sendTime: this.dataForm.sendTime,
                                noticeType: this.dataForm.noticeType
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
