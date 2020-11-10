<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="order no." prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="order no."></el-input>
    </el-form-item>
    <el-form-item label="order id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="order id"></el-input>
    </el-form-item>
    <el-form-item label="transaction no." prop="paymentTradeNo">
      <el-input v-model="dataForm.paymentTradeNo" placeholder="transaction no."></el-input>
    </el-form-item>
    <el-form-item label="transaction total amount" prop="totalAmount">
      <el-input v-model="dataForm.totalAmount" placeholder="transaction total amount"></el-input>
    </el-form-item>
    <el-form-item label="transaction content" prop="subject">
      <el-input v-model="dataForm.subject" placeholder="transaction content"></el-input>
    </el-form-item>
    <el-form-item label="transaction status" prop="paymentStatus">
      <el-input v-model="dataForm.paymentStatus" placeholder="transaction status"></el-input>
    </el-form-item>
    <el-form-item label="transaction create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="transaction create time"></el-input>
    </el-form-item>
    <el-form-item label="transaction confirmation time" prop="confirmTime">
      <el-input v-model="dataForm.confirmTime" placeholder="transaction confirmation time"></el-input>
    </el-form-item>
    <el-form-item label="callback content" prop="callbackContent">
      <el-input v-model="dataForm.callbackContent" placeholder="callback content"></el-input>
    </el-form-item>
    <el-form-item label="callback time" prop="callbackTime">
      <el-input v-model="dataForm.callbackTime" placeholder="callback time"></el-input>
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
          orderSn: '',
          orderId: '',
          paymentTradeNo: '',
          totalAmount: '',
          subject: '',
          paymentStatus: '',
          createTime: '',
          confirmTime: '',
          callbackContent: '',
          callbackTime: ''
        },
        dataRule: {
          orderSn: [
            { required: true, message: 'order no. can not be empty', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: 'order id can not be empty', trigger: 'blur' }
          ],
          paymentTradeNo: [
            { required: true, message: 'transaction no. can not be empty', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: 'transaction total amount can not be empty', trigger: 'blur' }
          ],
          subject: [
            { required: true, message: 'transaction content can not be empty', trigger: 'blur' }
          ],
          paymentStatus: [
            { required: true, message: 'transaction status can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'transaction create time can not be empty', trigger: 'blur' }
          ],
          confirmTime: [
            { required: true, message: 'transaction confirmation time can not be empty', trigger: 'blur' }
          ],
          callbackContent: [
            { required: true, message: 'callback content can not be empty', trigger: 'blur' }
          ],
          callbackTime: [
            { required: true, message: 'callback time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/paymentinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderSn = data.paymentInfo.orderSn
                this.dataForm.orderId = data.paymentInfo.orderId
                this.dataForm.paymentTradeNo = data.paymentInfo.paymentTradeNo
                this.dataForm.totalAmount = data.paymentInfo.totalAmount
                this.dataForm.subject = data.paymentInfo.subject
                this.dataForm.paymentStatus = data.paymentInfo.paymentStatus
                this.dataForm.createTime = data.paymentInfo.createTime
                this.dataForm.confirmTime = data.paymentInfo.confirmTime
                this.dataForm.callbackContent = data.paymentInfo.callbackContent
                this.dataForm.callbackTime = data.paymentInfo.callbackTime
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
              url: this.$http.adornUrl(`/order/paymentinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderSn': this.dataForm.orderSn,
                'orderId': this.dataForm.orderId,
                'paymentTradeNo': this.dataForm.paymentTradeNo,
                'totalAmount': this.dataForm.totalAmount,
                'subject': this.dataForm.subject,
                'paymentStatus': this.dataForm.paymentStatus,
                'createTime': this.dataForm.createTime,
                'confirmTime': this.dataForm.confirmTime,
                'callbackContent': this.dataForm.callbackContent,
                'callbackTime': this.dataForm.callbackTime
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
