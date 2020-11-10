<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="purchasing agent id" prop="assigneeId">
      <el-input v-model="dataForm.assigneeId" placeholder="purchasing agent id"></el-input>
    </el-form-item>
    <el-form-item label="purchasing agent name" prop="assigneeName">
      <el-input v-model="dataForm.assigneeName" placeholder="purchasing agent name"></el-input>
    </el-form-item>
    <el-form-item label="purchasing agent phone number" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="purchasing agent phone number"></el-input>
    </el-form-item>
    <el-form-item label="priority" prop="priority">
      <el-input v-model="dataForm.priority" placeholder="priority"></el-input>
    </el-form-item>
    <el-form-item label="status" prop="status">
      <el-input v-model="dataForm.status" placeholder="status"></el-input>
    </el-form-item>
    <el-form-item label="warehouse id" prop="wareId">
      <el-input v-model="dataForm.wareId" placeholder="warehouse id"></el-input>
    </el-form-item>
    <el-form-item label="total purchasing amount" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="total purchasing amount"></el-input>
    </el-form-item>
    <el-form-item label="create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="create time"></el-input>
    </el-form-item>
    <el-form-item label="update time" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="update time"></el-input>
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
          assigneeId: '',
          assigneeName: '',
          phone: '',
          priority: '',
          status: '',
          wareId: '',
          amount: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          assigneeId: [
            { required: true, message: 'purchasing agent id can not be empty', trigger: 'blur' }
          ],
          assigneeName: [
            { required: true, message: 'purchasing agent name can not be empty', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: 'purchasing agent phone number can not be empty', trigger: 'blur' }
          ],
          priority: [
            { required: true, message: 'priority can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'status can not be empty', trigger: 'blur' }
          ],
          wareId: [
            { required: true, message: 'warehouse id can not be empty', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: 'total purchasing amount can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'create time can not be empty', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: 'update time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/warehouse/purchase/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.assigneeId = data.purchase.assigneeId
                this.dataForm.assigneeName = data.purchase.assigneeName
                this.dataForm.phone = data.purchase.phone
                this.dataForm.priority = data.purchase.priority
                this.dataForm.status = data.purchase.status
                this.dataForm.wareId = data.purchase.wareId
                this.dataForm.amount = data.purchase.amount
                this.dataForm.createTime = data.purchase.createTime
                this.dataForm.updateTime = data.purchase.updateTime
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
              url: this.$http.adornUrl(`/warehouse/purchase/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'assigneeId': this.dataForm.assigneeId,
                'assigneeName': this.dataForm.assigneeName,
                'phone': this.dataForm.phone,
                'priority': this.dataForm.priority,
                'status': this.dataForm.status,
                'wareId': this.dataForm.wareId,
                'amount': this.dataForm.amount,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
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
