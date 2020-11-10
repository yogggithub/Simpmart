<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="lightning deal timeout (minutes)" prop="flashOrderOvertime">
      <el-input v-model="dataForm.flashOrderOvertime" placeholder="lightning deal timeout (minutes)"></el-input>
    </el-form-item>
    <el-form-item label="regular timeout (minutes)" prop="normalOrderOvertime">
      <el-input v-model="dataForm.normalOrderOvertime" placeholder="regular timeout (minutes)"></el-input>
    </el-form-item>
    <el-form-item label="automatic confirmation time (days)" prop="confirmOvertime">
      <el-input v-model="dataForm.confirmOvertime" placeholder="automatic confirmation time (days)"></el-input>
    </el-form-item>
    <el-form-item label="auto completion time, return window closed time (days)" prop="finishOvertime">
      <el-input v-model="dataForm.finishOvertime" placeholder="auto completion time, return window closed time (days)"></el-input>
    </el-form-item>
    <el-form-item label="automatically review time after order completion (days)" prop="commentOvertime">
      <el-input v-model="dataForm.commentOvertime" placeholder="automatically review time after order completion (days)"></el-input>
    </el-form-item>
    <el-form-item label="required member level[0->unlimited, other->corresponding member level]" prop="memberLevel">
      <el-input v-model="dataForm.memberLevel" placeholder="required member level[0->unlimited, other->corresponding member level]"></el-input>
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
          flashOrderOvertime: '',
          normalOrderOvertime: '',
          confirmOvertime: '',
          finishOvertime: '',
          commentOvertime: '',
          memberLevel: ''
        },
        dataRule: {
          flashOrderOvertime: [
            { required: true, message: 'lightning deal timeout (minutes) can not be empty', trigger: 'blur' }
          ],
          normalOrderOvertime: [
            { required: true, message: 'regular timeout (minutes) can not be empty', trigger: 'blur' }
          ],
          confirmOvertime: [
            { required: true, message: 'automatic confirmation time (days) can not be empty', trigger: 'blur' }
          ],
          finishOvertime: [
            { required: true, message: 'auto completion time, return window closed time (days) can not be empty', trigger: 'blur' }
          ],
          commentOvertime: [
            { required: true, message: 'automatically review time after order completion (days) can not be empty', trigger: 'blur' }
          ],
          memberLevel: [
            { required: true, message: 'required member level[0->unlimited, other->corresponding member level] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/ordersetting/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.flashOrderOvertime = data.orderSetting.flashOrderOvertime
                this.dataForm.normalOrderOvertime = data.orderSetting.normalOrderOvertime
                this.dataForm.confirmOvertime = data.orderSetting.confirmOvertime
                this.dataForm.finishOvertime = data.orderSetting.finishOvertime
                this.dataForm.commentOvertime = data.orderSetting.commentOvertime
                this.dataForm.memberLevel = data.orderSetting.memberLevel
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
              url: this.$http.adornUrl(`/order/ordersetting/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'flashOrderOvertime': this.dataForm.flashOrderOvertime,
                'normalOrderOvertime': this.dataForm.normalOrderOvertime,
                'confirmOvertime': this.dataForm.confirmOvertime,
                'finishOvertime': this.dataForm.finishOvertime,
                'commentOvertime': this.dataForm.commentOvertime,
                'memberLevel': this.dataForm.memberLevel
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
