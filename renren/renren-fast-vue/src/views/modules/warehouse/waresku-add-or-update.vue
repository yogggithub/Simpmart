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
            <el-form-item label="sku_id" prop="skuId">
                <el-input placeholder="sku_id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="warehouse" prop="wareId">
                <el-select clearable placeholder="Select" v-model="dataForm.wareId">
                    <el-option :key="w.id" :label="w.name" :value="w.id" v-for="w in wareList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="stock quantity" prop="stock">
                <el-input placeholder="stock quantity" v-model="dataForm.stock"></el-input>
            </el-form-item>
            <el-form-item label="sku name" prop="skuName">
                <el-input placeholder="sku name" v-model="dataForm.skuName"></el-input>
            </el-form-item>
            <el-form-item label="stock locked" prop="stockLocked">
                <el-input placeholder="stock locked" v-model="dataForm.stockLocked"></el-input>
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
                wareList: [],
                dataForm: {
                    id: 0,
                    skuId: '',
                    wareId: '',
                    stock: 0,
                    skuName: '',
                    stockLocked: 0
                },
                dataRule: {
                    skuId: [{ required: true, message: 'Can not be empty', trigger: 'blur' }],
                    wareId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    stock: [{ required: true, message: 'Can not be empty', trigger: 'blur' }],
                    skuName: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ]
                }
            }
        },
        created () {
            this.getWares()
        },
        methods: {
            getWares () {
                this.$http({
                    url: this.$http.adornUrl('/ware/wareinfo/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: 1,
                        limit: 500
                    })
                }).then(({ data }) => {
                    this.wareList = data.page.list
                })
            },
            init (id) {
                this.dataForm.id = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.id) {
                        this.$http({
                            url: this.$http.adornUrl(`/ware/waresku/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.skuId = data.wareSku.skuId
                                this.dataForm.wareId = data.wareSku.wareId
                                this.dataForm.stock = data.wareSku.stock
                                this.dataForm.skuName = data.wareSku.skuName
                                this.dataForm.stockLocked = data.wareSku.stockLocked
                            }
                        })
                    }
                })
            },
            dataFormSubmit () {
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(
                                `/ware/waresku/${!this.dataForm.id ? 'save' : 'update'}`
                            ),
                            method: 'post',
                            data: this.$http.adornData({
                                id: this.dataForm.id || undefined,
                                skuId: this.dataForm.skuId,
                                wareId: this.dataForm.wareId,
                                stock: this.dataForm.stock,
                                skuName: this.dataForm.skuName,
                                stockLocked: this.dataForm.stockLocked
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
