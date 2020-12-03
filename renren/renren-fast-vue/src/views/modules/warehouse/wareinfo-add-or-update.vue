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
            <el-form-item label="name" prop="name">
                <el-input placeholder="warehouse name" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="address" prop="address">
                <el-input placeholder="warehouse address" v-model="dataForm.address"></el-input>
            </el-form-item>
            <el-form-item label="area code" prop="areacode">
                <el-input placeholder="area code" v-model="dataForm.areacode"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">Cancel</el-button>
            <el-button @click="dataFormSubmit()" type="primary">Cofirm</el-button>
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
                    name: '',
                    address: '',
                    areacode: ''
                },
                dataRule: {
                    name: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    areacode: [
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
                            url: this.$http.adornUrl(`/ware/warehouseinfo/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.wareInfo.name
                                this.dataForm.address = data.wareInfo.address
                                this.dataForm.areacode = data.wareInfo.areacode
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
                            url: this.$http.adornUrl(`/ware/warehouseinfo/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'name': this.dataForm.name,
                                'address': this.dataForm.address,
                                'areacode': this.dataForm.areacode
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
