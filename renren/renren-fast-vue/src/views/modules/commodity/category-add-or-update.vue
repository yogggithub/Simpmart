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
            <el-form-item label="category name" prop="name">
                <el-input placeholder="category name" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="parent category id" prop="parentCid">
                <el-input placeholder="parent category id" v-model="dataForm.parentCid"></el-input>
            </el-form-item>
            <el-form-item label="category level" prop="catLevel">
                <el-input placeholder="category level" v-model="dataForm.catLevel"></el-input>
            </el-form-item>
            <el-form-item label="showing status [0->no, 1->yes]" prop="showStatus">
                <el-input
                    placeholder="showing status [0->no, 1->yes]"
                    v-model="dataForm.showStatus"
                ></el-input>
            </el-form-item>
            <el-form-item label="category sequence" prop="sort">
                <el-input placeholder="category sequence" v-model="dataForm.sort"></el-input>
            </el-form-item>
            <el-form-item label="category icon link" prop="icon">
                <el-input placeholder="category icon link" v-model="dataForm.icon"></el-input>
            </el-form-item>
            <el-form-item label="product unit" prop="productUnit">
                <el-input placeholder="product unit" v-model="dataForm.productUnit"></el-input>
            </el-form-item>
            <el-form-item label="product quantity" prop="productCount">
                <el-input placeholder="product quantity" v-model="dataForm.productCount"></el-input>
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
                    catId: 0,
                    name: '',
                    parentCid: '',
                    catLevel: '',
                    showStatus: '',
                    sort: '',
                    icon: '',
                    productUnit: '',
                    productCount: ''
                },
                dataRule: {
                    name: [
                        { required: true, message: 'category name can not be empty', trigger: 'blur' }
                    ],
                    parentCid: [
                        { required: true, message: 'parent category id can not be empty', trigger: 'blur' }
                    ],
                    catLevel: [
                        { required: true, message: 'category level can not be empty', trigger: 'blur' }
                    ],
                    showStatus: [
                        { required: true, message: 'showing status [0->no, 1->yes] can not be empty', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: 'category sequence can not be empty', trigger: 'blur' }
                    ],
                    icon: [
                        { required: true, message: 'category icon link can not be empty', trigger: 'blur' }
                    ],
                    productUnit: [
                        { required: true, message: 'product unit can not be empty', trigger: 'blur' }
                    ],
                    productCount: [
                        { required: true, message: 'product quantity can not be empty', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init (id) {
                this.dataForm.catId = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.catId) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/category/info/${this.dataForm.catId}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.category.name
                                this.dataForm.parentCid = data.category.parentCid
                                this.dataForm.catLevel = data.category.catLevel
                                this.dataForm.showStatus = data.category.showStatus
                                this.dataForm.sort = data.category.sort
                                this.dataForm.icon = data.category.icon
                                this.dataForm.productUnit = data.category.productUnit
                                this.dataForm.productCount = data.category.productCount
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
                            url: this.$http.adornUrl(`/commodity/category/${!this.dataForm.catId ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'catId': this.dataForm.catId || undefined,
                                'name': this.dataForm.name,
                                'parentCid': this.dataForm.parentCid,
                                'catLevel': this.dataForm.catLevel,
                                'showStatus': this.dataForm.showStatus,
                                'sort': this.dataForm.sort,
                                'icon': this.dataForm.icon,
                                'productUnit': this.dataForm.productUnit,
                                'productCount': this.dataForm.productCount
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
