<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="order no." prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="order no."></el-input>
    </el-form-item>
    <el-form-item label="operator[member, system, administrator]" prop="operateMan">
      <el-input v-model="dataForm.operateMan" placeholder="operator[member, system, administrator]"></el-input>
    </el-form-item>
    <el-form-item label="operating time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="operating time"></el-input>
    </el-form-item>
    <el-form-item label="order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]"></el-input>
    </el-form-item>
    <el-form-item label="order comments" prop="note">
      <el-input v-model="dataForm.note" placeholder="order comments"></el-input>
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
          orderId: '',
          operateMan: '',
          createTime: '',
          orderStatus: '',
          note: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: 'order no. can not be empty', trigger: 'blur' }
          ],
          operateMan: [
            { required: true, message: 'operator[member, system, administrator] can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'operating time can not be empty', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: 'order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid] can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'order comments can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/orderoperatehistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderOperateHistory.orderId
                this.dataForm.operateMan = data.orderOperateHistory.operateMan
                this.dataForm.createTime = data.orderOperateHistory.createTime
                this.dataForm.orderStatus = data.orderOperateHistory.orderStatus
                this.dataForm.note = data.orderOperateHistory.note
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
              url: this.$http.adornUrl(`/order/orderoperatehistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'operateMan': this.dataForm.operateMan,
                'createTime': this.dataForm.createTime,
                'orderStatus': this.dataForm.orderStatus,
                'note': this.dataForm.note
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
