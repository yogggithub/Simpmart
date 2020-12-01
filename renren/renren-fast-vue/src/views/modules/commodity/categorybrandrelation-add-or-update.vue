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
            label-width="80px"
            ref="dataForm"
        >
            <el-form-item label="brand id" prop="brandId">
                <el-input placeholder="brand id" v-model="dataForm.brandId"></el-input>
            </el-form-item>
            <el-form-item label="category id" prop="catelogId">
                <el-input placeholder="category id" v-model="dataForm.catelogId"></el-input>
            </el-form-item>
            <el-form-item label prop="brandName">
                <el-input placeholder v-model="dataForm.brandName"></el-input>
            </el-form-item>
            <el-form-item label prop="catelogName">
                <el-input placeholder v-model="dataForm.catelogName"></el-input>
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
                    brandId: '',
                    catelogId: '',
                    brandName: '',
                    catelogName: ''
                },
                dataRule: {
                    brandId: [
                        { required: true, message: 'brand id can not be empty', trigger: 'blur' }
                    ],
                    catelogId: [
                        { required: true, message: 'category id can not be empty', trigger: 'blur' }
                    ],
                    brandName: [
                        { required: true, message: ' can not be empty', trigger: 'blur' }
                    ],
                    catelogName: [
                        { required: true, message: ' can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/commodity/categorybrandrelation/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.brandId = data.categoryBrandRelation.brandId
                                this.dataForm.catelogId = data.categoryBrandRelation.catelogId
                                this.dataForm.brandName = data.categoryBrandRelation.brandName
                                this.dataForm.catelogName = data.categoryBrandRelation.catelogName
                            }
                        })
                    }
                })
            },
            // Form submission
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/categorybrandrelation/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'brandId': this.dataForm.brandId,
                                'catelogId': this.dataForm.catelogId,
                                'brandName': this.dataForm.brandName,
                                'catelogName': this.dataForm.catelogName
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: 'Completed',
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
