<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="seckill section name" prop="name">
      <el-input v-model="dataForm.name" placeholder="seckill section name"></el-input>
    </el-form-item>
    <el-form-item label="seckill section start time" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="seckill section start time"></el-input>
    </el-form-item>
    <el-form-item label="seckill section end time" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="seckill section end time"></el-input>
    </el-form-item>
    <el-form-item label="seckill section status" prop="status">
      <el-input v-model="dataForm.status" placeholder="seckill section status"></el-input>
    </el-form-item>
    <el-form-item label="seckill section create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="seckill section create time"></el-input>
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
          name: '',
          startTime: '',
          endTime: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'seckill section name can not be empty', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: 'seckill section start time can not be empty', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: 'seckill section end time can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'seckill section status can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'seckill section create time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/seckillsession/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.seckillSession.name
                this.dataForm.startTime = data.seckillSession.startTime
                this.dataForm.endTime = data.seckillSession.endTime
                this.dataForm.status = data.seckillSession.status
                this.dataForm.createTime = data.seckillSession.createTime
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
              url: this.$http.adornUrl(`/coupon/seckillsession/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime
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
