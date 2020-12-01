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
            <el-form-item label="优惠券id" prop="couponId">
                <el-input placeholder="优惠券id" v-model="dataForm.couponId"></el-input>
            </el-form-item>
            <el-form-item label="产品分类id" prop="categoryId">
                <el-input placeholder="产品分类id" v-model="dataForm.categoryId"></el-input>
            </el-form-item>
            <el-form-item label="产品分类名称" prop="categoryName">
                <el-input placeholder="产品分类名称" v-model="dataForm.categoryName"></el-input>
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
                    couponId: '',
                    categoryId: '',
                    categoryName: ''
                },
                dataRule: {
                    couponId: [
                        { required: true, message: '优惠券id不能为空', trigger: 'blur' }
                    ],
                    categoryId: [
                        { required: true, message: '产品分类id不能为空', trigger: 'blur' }
                    ],
                    categoryName: [
                        { required: true, message: '产品分类名称不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/coupon/couponspucategoryrelation/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.couponId = data.couponSpuCategoryRelation.couponId
                                this.dataForm.categoryId = data.couponSpuCategoryRelation.categoryId
                                this.dataForm.categoryName = data.couponSpuCategoryRelation.categoryName
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
                            url: this.$http.adornUrl(`/coupon/couponspucategoryrelation/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'couponId': this.dataForm.couponId,
                                'categoryId': this.dataForm.categoryId,
                                'categoryName': this.dataForm.categoryName
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
