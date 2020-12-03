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
            <el-form-item label="purchase sku id" prop="skuId">
                <el-input placeholder="purchase sku id" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item label="purchase quantity" prop="skuNum">
                <el-input placeholder="purchase quantity" v-model="dataForm.skuNum"></el-input>
            </el-form-item>
            <el-form-item label="warehouse" prop="wareId">
                <el-select clearable placeholder="Select" v-model="dataForm.wareId">
                    <el-option :key="w.id" :label="w.name" :value="w.id" v-for="w in wareList"></el-option>
                </el-select>
            </el-form-item>
            <!-- [0新建，1已分配，2正在采购，3已完成，4采购失败] -->
            <!-- <el-form-item label="状态" prop="status">
        <el-select v-model="dataForm.status" placeholder="请选择状态" clearable>
          <el-option label="新建" :value="0"></el-option>
          <el-option label="已分配" :value="1"></el-option>
          <el-option label="正在采购" :value="2"></el-option>
          <el-option label="已完成" :value="3"></el-option>
          <el-option label="采购失败" :value="4"></el-option>
        </el-select>
            </el-form-item>-->
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
                    purchaseId: '',
                    skuId: '',
                    skuNum: '',
                    skuPrice: '',
                    wareId: '',
                    status: 0
                },
                dataRule: {
                    skuId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    skuNum: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    wareId: [{ required: true, message: 'Can not be empty', trigger: 'blur' }]
                }
            }
        },
        created () {
            this.getWares()
        },
        methods: {
            getWares () {
                this.$http({
                    url: this.$http.adornUrl('/ware/warehouseinfo/list'),
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
                            url: this.$http.adornUrl(
                                `/ware/purchasedetail/info/${this.dataForm.id}`
                            ),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.purchaseId = data.purchaseDetail.purchaseId
                                this.dataForm.skuId = data.purchaseDetail.skuId
                                this.dataForm.skuNum = data.purchaseDetail.skuNum
                                this.dataForm.skuPrice = data.purchaseDetail.skuPrice
                                this.dataForm.wareId = data.purchaseDetail.wareId
                                this.dataForm.status = data.purchaseDetail.status
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
                                `/ware/purchasedetail/${!this.dataForm.id ? 'save' : 'update'}`
                            ),
                            method: 'post',
                            data: this.$http.adornData({
                                id: this.dataForm.id || undefined,
                                purchaseId: this.dataForm.purchaseId,
                                skuId: this.dataForm.skuId,
                                skuNum: this.dataForm.skuNum,
                                skuPrice: this.dataForm.skuPrice,
                                wareId: this.dataForm.wareId,
                                status: this.dataForm.status
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
