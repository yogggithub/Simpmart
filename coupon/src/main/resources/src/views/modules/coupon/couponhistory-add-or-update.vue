<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="coupon id" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="coupon id"></el-input>
    </el-form-item>
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="member name" prop="memberNickName">
      <el-input v-model="dataForm.memberNickName" placeholder="member name"></el-input>
    </el-form-item>
    <el-form-item label="coupon1 collection method [0->passivity, 1->activity]" prop="getType">
      <el-input v-model="dataForm.getType" placeholder="coupon1 collection method [0->passivity, 1->activity]"></el-input>
    </el-form-item>
    <el-form-item label="coupon create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="coupon create time"></el-input>
    </el-form-item>
    <el-form-item label="used status [0->unused, 1->used, 2->expired]" prop="useType">
      <el-input v-model="dataForm.useType" placeholder="used status [0->unused, 1->used, 2->expired]"></el-input>
    </el-form-item>
    <el-form-item label="used time" prop="useTime">
      <el-input v-model="dataForm.useTime" placeholder="used time"></el-input>
    </el-form-item>
    <el-form-item label="order id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="order id"></el-input>
    </el-form-item>
    <el-form-item label="order no." prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="order no."></el-input>
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
          couponId: '',
          memberId: '',
          memberNickName: '',
          getType: '',
          createTime: '',
          useType: '',
          useTime: '',
          orderId: '',
          orderSn: ''
        },
        dataRule: {
          couponId: [
            { required: true, message: 'coupon id can not be empty', trigger: 'blur' }
          ],
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: 'member name can not be empty', trigger: 'blur' }
          ],
          getType: [
            { required: true, message: 'coupon1 collection method [0->passivity, 1->activity] can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'coupon create time can not be empty', trigger: 'blur' }
          ],
          useType: [
            { required: true, message: 'used status [0->unused, 1->used, 2->expired] can not be empty', trigger: 'blur' }
          ],
          useTime: [
            { required: true, message: 'used time can not be empty', trigger: 'blur' }
          ],
          orderId: [
            { required: true, message: 'order id can not be empty', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: 'order no. can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/couponhistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponId = data.couponHistory.couponId
                this.dataForm.memberId = data.couponHistory.memberId
                this.dataForm.memberNickName = data.couponHistory.memberNickName
                this.dataForm.getType = data.couponHistory.getType
                this.dataForm.createTime = data.couponHistory.createTime
                this.dataForm.useType = data.couponHistory.useType
                this.dataForm.useTime = data.couponHistory.useTime
                this.dataForm.orderId = data.couponHistory.orderId
                this.dataForm.orderSn = data.couponHistory.orderSn
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
              url: this.$http.adornUrl(`/coupon/couponhistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponId': this.dataForm.couponId,
                'memberId': this.dataForm.memberId,
                'memberNickName': this.dataForm.memberNickName,
                'getType': this.dataForm.getType,
                'createTime': this.dataForm.createTime,
                'useType': this.dataForm.useType,
                'useTime': this.dataForm.useTime,
                'orderId': this.dataForm.orderId,
                'orderSn': this.dataForm.orderSn
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
