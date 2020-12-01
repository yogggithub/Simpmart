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
            <el-form-item label="spuId" prop="spuId">
                <el-input placeholder v-model="dataForm.spuId"></el-input>
            </el-form-item>
            <el-form-item label="成长积分" prop="growBounds">
                <el-input placeholder="成长积分" v-model="dataForm.growBounds"></el-input>
            </el-form-item>
            <el-form-item label="购物积分" prop="buyBounds">
                <el-input placeholder="购物积分" v-model="dataForm.buyBounds"></el-input>
            </el-form-item>
            <!-- [1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】] -->
            <el-form-item label="优惠生效情况" prop="work">
                <el-input placeholder="优惠生效情况" v-model="dataForm.work"></el-input>
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
                    spuId: '',
                    growBounds: '',
                    buyBounds: '',
                    work: ''
                },
                dataRule: {
                    spuId: [
                        { required: true, message: '不能为空', trigger: 'blur' }
                    ],
                    growBounds: [
                        { required: true, message: '成长积分不能为空', trigger: 'blur' }
                    ],
                    buyBounds: [
                        { required: true, message: '购物积分不能为空', trigger: 'blur' }
                    ],
                    work: [
                        { required: true, message: '优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/coupon/spubounds/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.spuId = data.spuBounds.spuId
                                this.dataForm.growBounds = data.spuBounds.growBounds
                                this.dataForm.buyBounds = data.spuBounds.buyBounds
                                this.dataForm.work = data.spuBounds.work
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
                            url: this.$http.adornUrl(`/coupon/spubounds/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'spuId': this.dataForm.spuId,
                                'growBounds': this.dataForm.growBounds,
                                'buyBounds': this.dataForm.buyBounds,
                                'work': this.dataForm.work
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
