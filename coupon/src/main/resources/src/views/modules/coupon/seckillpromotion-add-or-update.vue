<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="seckill activity title" prop="title">
      <el-input v-model="dataForm.title" placeholder="seckill activity title"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity start time" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="seckill activity start time"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity end time" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="seckill activity end time"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity status" prop="status">
      <el-input v-model="dataForm.status" placeholder="seckill activity status"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="seckill activity create time"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity creator" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="seckill activity creator"></el-input>
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
          title: '',
          startTime: '',
          endTime: '',
          status: '',
          createTime: '',
          userId: ''
        },
        dataRule: {
          title: [
            { required: true, message: 'seckill activity title can not be empty', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: 'seckill activity start time can not be empty', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: 'seckill activity end time can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'seckill activity status can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'seckill activity create time can not be empty', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: 'seckill activity creator can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/seckillpromotion/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.seckillPromotion.title
                this.dataForm.startTime = data.seckillPromotion.startTime
                this.dataForm.endTime = data.seckillPromotion.endTime
                this.dataForm.status = data.seckillPromotion.status
                this.dataForm.createTime = data.seckillPromotion.createTime
                this.dataForm.userId = data.seckillPromotion.userId
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
              url: this.$http.adornUrl(`/coupon/seckillpromotion/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'title': this.dataForm.title,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'status': this.dataForm.status,
                'createTime': this.dataForm.createTime,
                'userId': this.dataForm.userId
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
