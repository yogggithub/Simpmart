<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="log create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="log create time"></el-input>
    </el-form-item>
    <el-form-item label="login ip address" prop="ip">
      <el-input v-model="dataForm.ip" placeholder="login ip address"></el-input>
    </el-form-item>
    <el-form-item label="login city" prop="city">
      <el-input v-model="dataForm.city" placeholder="login city"></el-input>
    </el-form-item>
    <el-form-item label="login type [1 -> pc, 2 -> mobile]" prop="loginType">
      <el-input v-model="dataForm.loginType" placeholder="login type [1 -> pc, 2 -> mobile]"></el-input>
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
          createTime: '',
          ip: '',
          city: '',
          loginType: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'log create time can not be empty', trigger: 'blur' }
          ],
          ip: [
            { required: true, message: 'login ip address can not be empty', trigger: 'blur' }
          ],
          city: [
            { required: true, message: 'login city can not be empty', trigger: 'blur' }
          ],
          loginType: [
            { required: true, message: 'login type [1 -> pc, 2 -> mobile] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberloginlog/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberLoginLog.memberId
                this.dataForm.createTime = data.memberLoginLog.createTime
                this.dataForm.ip = data.memberLoginLog.ip
                this.dataForm.city = data.memberLoginLog.city
                this.dataForm.loginType = data.memberLoginLog.loginType
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
              url: this.$http.adornUrl(`/member/memberloginlog/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'createTime': this.dataForm.createTime,
                'ip': this.dataForm.ip,
                'city': this.dataForm.city,
                'loginType': this.dataForm.loginType
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
