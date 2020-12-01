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
            <el-form-item label="sku id" prop="skuId">
                <el-input placeholder="sku id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="sku images link" prop="imgUrl">
                <el-input placeholder="sku images link" v-model="dataForm.imgUrl"></el-input>
            </el-form-item>
            <el-form-item label="sku sequence" prop="imgSort">
                <el-input placeholder="sku sequence" v-model="dataForm.imgSort"></el-input>
            </el-form-item>
            <el-form-item label="is default image [0->no, 1->yes]" prop="defaultImg">
                <el-input
                    placeholder="is default image [0->no, 1->yes]"
                    v-model="dataForm.defaultImg"
                ></el-input>
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
                    skuId: '',
                    imgUrl: '',
                    imgSort: '',
                    defaultImg: ''
                },
                dataRule: {
                    skuId: [
                        { required: true, message: 'sku id can not be empty', trigger: 'blur' }
                    ],
                    imgUrl: [
                        { required: true, message: 'sku images link can not be empty', trigger: 'blur' }
                    ],
                    imgSort: [
                        { required: true, message: 'sku sequence can not be empty', trigger: 'blur' }
                    ],
                    defaultImg: [
                        { required: true, message: 'is default image [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/commodity/skuimages/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.skuId = data.skuImages.skuId
                                this.dataForm.imgUrl = data.skuImages.imgUrl
                                this.dataForm.imgSort = data.skuImages.imgSort
                                this.dataForm.defaultImg = data.skuImages.defaultImg
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
                            url: this.$http.adornUrl(`/commodity/skuimages/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'skuId': this.dataForm.skuId,
                                'imgUrl': this.dataForm.imgUrl,
                                'imgSort': this.dataForm.imgSort,
                                'defaultImg': this.dataForm.defaultImg
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
