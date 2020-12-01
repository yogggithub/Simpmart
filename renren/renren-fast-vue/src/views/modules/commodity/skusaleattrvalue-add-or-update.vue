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
            <el-form-item label="attribute id" prop="attrId">
                <el-input placeholder="attribute id" v-model="dataForm.attrId"></el-input>
            </el-form-item>
            <el-form-item label="attribute name" prop="attrName">
                <el-input placeholder="attribute name" v-model="dataForm.attrName"></el-input>
            </el-form-item>
            <el-form-item label="attribute value" prop="attrValue">
                <el-input placeholder="attribute value" v-model="dataForm.attrValue"></el-input>
            </el-form-item>
            <el-form-item label="attribute sequence" prop="attrSort">
                <el-input placeholder="attribute sequence" v-model="dataForm.attrSort"></el-input>
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
                    attrId: '',
                    attrName: '',
                    attrValue: '',
                    attrSort: ''
                },
                dataRule: {
                    skuId: [
                        { required: true, message: 'sku id can not be empty', trigger: 'blur' }
                    ],
                    attrId: [
                        { required: true, message: 'attribute id can not be empty', trigger: 'blur' }
                    ],
                    attrName: [
                        { required: true, message: 'attribute name can not be empty', trigger: 'blur' }
                    ],
                    attrValue: [
                        { required: true, message: 'attribute value can not be empty', trigger: 'blur' }
                    ],
                    attrSort: [
                        { required: true, message: 'attribute sequence can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/commodity/skusaleattrvalue/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.skuId = data.skuSaleAttrValue.skuId
                                this.dataForm.attrId = data.skuSaleAttrValue.attrId
                                this.dataForm.attrName = data.skuSaleAttrValue.attrName
                                this.dataForm.attrValue = data.skuSaleAttrValue.attrValue
                                this.dataForm.attrSort = data.skuSaleAttrValue.attrSort
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
                            url: this.$http.adornUrl(`/commodity/skusaleattrvalue/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'skuId': this.dataForm.skuId,
                                'attrId': this.dataForm.attrId,
                                'attrName': this.dataForm.attrName,
                                'attrValue': this.dataForm.attrValue,
                                'attrSort': this.dataForm.attrSort
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
