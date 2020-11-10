<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="return id" prop="orderReturnId">
      <el-input v-model="dataForm.orderReturnId" placeholder="return id"></el-input>
    </el-form-item>
    <el-form-item label="refund amount" prop="refund">
      <el-input v-model="dataForm.refund" placeholder="refund amount"></el-input>
    </el-form-item>
    <el-form-item label="refund transaction no." prop="refundSn">
      <el-input v-model="dataForm.refundSn" placeholder="refund transaction no."></el-input>
    </el-form-item>
    <el-form-item label="refund status" prop="refundStatus">
      <el-input v-model="dataForm.refundStatus" placeholder="refund status"></el-input>
    </el-form-item>
    <el-form-item label="refund method [1->credit card, 2->Paypal, 3->debit card, 4->cheque]" prop="refundChannel">
      <el-input v-model="dataForm.refundChannel" placeholder="refund method [1->credit card, 2->Paypal, 3->debit card, 4->cheque]"></el-input>
    </el-form-item>
    <el-form-item label="refund content" prop="refundContent">
      <el-input v-model="dataForm.refundContent" placeholder="refund content"></el-input>
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
          orderReturnId: '',
          refund: '',
          refundSn: '',
          refundStatus: '',
          refundChannel: '',
          refundContent: ''
        },
        dataRule: {
          orderReturnId: [
            { required: true, message: 'return id can not be empty', trigger: 'blur' }
          ],
          refund: [
            { required: true, message: 'refund amount can not be empty', trigger: 'blur' }
          ],
          refundSn: [
            { required: true, message: 'refund transaction no. can not be empty', trigger: 'blur' }
          ],
          refundStatus: [
            { required: true, message: 'refund status can not be empty', trigger: 'blur' }
          ],
          refundChannel: [
            { required: true, message: 'refund method [1->credit card, 2->Paypal, 3->debit card, 4->cheque] can not be empty', trigger: 'blur' }
          ],
          refundContent: [
            { required: true, message: 'refund content can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/refundinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderReturnId = data.refundInfo.orderReturnId
                this.dataForm.refund = data.refundInfo.refund
                this.dataForm.refundSn = data.refundInfo.refundSn
                this.dataForm.refundStatus = data.refundInfo.refundStatus
                this.dataForm.refundChannel = data.refundInfo.refundChannel
                this.dataForm.refundContent = data.refundInfo.refundContent
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
              url: this.$http.adornUrl(`/order/refundinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderReturnId': this.dataForm.orderReturnId,
                'refund': this.dataForm.refund,
                'refundSn': this.dataForm.refundSn,
                'refundStatus': this.dataForm.refundStatus,
                'refundChannel': this.dataForm.refundChannel,
                'refundContent': this.dataForm.refundContent
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
