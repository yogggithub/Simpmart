<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="purchase order id" prop="purchaseId">
      <el-input v-model="dataForm.purchaseId" placeholder="purchase order id"></el-input>
    </el-form-item>
    <el-form-item label="purchased goods id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="purchased goods id"></el-input>
    </el-form-item>
    <el-form-item label="purchased quantity" prop="skuNum">
      <el-input v-model="dataForm.skuNum" placeholder="purchased quantity"></el-input>
    </el-form-item>
    <el-form-item label="purchased amount" prop="skuPrice">
      <el-input v-model="dataForm.skuPrice" placeholder="purchased amount"></el-input>
    </el-form-item>
    <el-form-item label="warehouse id" prop="wareId">
      <el-input v-model="dataForm.wareId" placeholder="warehouse id"></el-input>
    </el-form-item>
    <el-form-item label="purchase order status [0 -> new, 1 -> assigned, 2 -> purchasing, 3 -> completed, 4 -> failure]" prop="status">
      <el-input v-model="dataForm.status" placeholder="purchase order status [0 -> new, 1 -> assigned, 2 -> purchasing, 3 -> completed, 4 -> failure]"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">Cancel</el-button>
      <el-button type="primary" @click="dataFormSubmit()">Confirm</el-button>
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
          purchaseId: '',
          skuId: '',
          skuNum: '',
          skuPrice: '',
          wareId: '',
          status: ''
        },
        dataRule: {
          purchaseId: [
            { required: true, message: 'purchase order id can not be empty', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: 'purchased goods id can not be empty', trigger: 'blur' }
          ],
          skuNum: [
            { required: true, message: 'purchased quantity can not be empty', trigger: 'blur' }
          ],
          skuPrice: [
            { required: true, message: 'purchased amount can not be empty', trigger: 'blur' }
          ],
          wareId: [
            { required: true, message: 'warehouse id can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'purchase order status [0 -> new, 1 -> assigned, 2 -> purchasing, 3 -> completed, 4 -> failure] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/warehouse/purchasedetail/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
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
      // Form submission
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/warehouse/purchasedetail/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'purchaseId': this.dataForm.purchaseId,
                'skuId': this.dataForm.skuId,
                'skuNum': this.dataForm.skuNum,
                'skuPrice': this.dataForm.skuPrice,
                'wareId': this.dataForm.wareId,
                'status': this.dataForm.status
              })
            }).then(({data}) => {
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
