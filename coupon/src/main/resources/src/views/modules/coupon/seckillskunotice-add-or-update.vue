<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
    </el-form-item>
    <el-form-item label="seckill section id" prop="sessionId">
      <el-input v-model="dataForm.sessionId" placeholder="seckill section id"></el-input>
    </el-form-item>
    <el-form-item label="member subcribe time" prop="subcribeTime">
      <el-input v-model="dataForm.subcribeTime" placeholder="member subcribe time"></el-input>
    </el-form-item>
    <el-form-item label="subcribe notify time" prop="sendTime">
      <el-input v-model="dataForm.sendTime" placeholder="subcribe notify time"></el-input>
    </el-form-item>
    <el-form-item label="notify method [0->text, 1->email]" prop="noticeType">
      <el-input v-model="dataForm.noticeType" placeholder="notify method [0->text, 1->email]"></el-input>
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
          memberId: '',
          skuId: '',
          sessionId: '',
          subcribeTime: '',
          sendTime: '',
          noticeType: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: 'sku id can not be empty', trigger: 'blur' }
          ],
          sessionId: [
            { required: true, message: 'seckill section id can not be empty', trigger: 'blur' }
          ],
          subcribeTime: [
            { required: true, message: 'member subcribe time can not be empty', trigger: 'blur' }
          ],
          sendTime: [
            { required: true, message: 'subcribe notify time can not be empty', trigger: 'blur' }
          ],
          noticeType: [
            { required: true, message: 'notify method [0->text, 1->email] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/seckillskunotice/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.seckillSkuNotice.memberId
                this.dataForm.skuId = data.seckillSkuNotice.skuId
                this.dataForm.sessionId = data.seckillSkuNotice.sessionId
                this.dataForm.subcribeTime = data.seckillSkuNotice.subcribeTime
                this.dataForm.sendTime = data.seckillSkuNotice.sendTime
                this.dataForm.noticeType = data.seckillSkuNotice.noticeType
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
              url: this.$http.adornUrl(`/coupon/seckillskunotice/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'skuId': this.dataForm.skuId,
                'sessionId': this.dataForm.sessionId,
                'subcribeTime': this.dataForm.subcribeTime,
                'sendTime': this.dataForm.sendTime,
                'noticeType': this.dataForm.noticeType
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
