<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="member growth point" prop="growBounds">
      <el-input v-model="dataForm.growBounds" placeholder="member growth point"></el-input>
    </el-form-item>
    <el-form-item label="member shopping point" prop="buyBounds">
      <el-input v-model="dataForm.buyBounds" placeholder="member shopping point"></el-input>
    </el-form-item>
    <el-form-item label="promotion status, 4 bits [from left to right, first->no discount, bonus growth point [0->no, 1->yes], second->no discount, bonus shopping point [0->no, 1->yes], third->discounted, bonus growth point [0->no, 1->yes], forth->discounted, bonus shopping point [0->no, 1->yes]" prop="work">
      <el-input v-model="dataForm.work" placeholder="promotion status, 4 bits [from left to right, first->no discount, bonus growth point [0->no, 1->yes], second->no discount, bonus shopping point [0->no, 1->yes], third->discounted, bonus growth point [0->no, 1->yes], forth->discounted, bonus shopping point [0->no, 1->yes]"></el-input>
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
          spuId: '',
          growBounds: '',
          buyBounds: '',
          work: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: ' can not be empty', trigger: 'blur' }
          ],
          growBounds: [
            { required: true, message: 'member growth point can not be empty', trigger: 'blur' }
          ],
          buyBounds: [
            { required: true, message: 'member shopping point can not be empty', trigger: 'blur' }
          ],
          work: [
            { required: true, message: 'promotion status, 4 bits [from left to right, first->no discount, bonus growth point [0->no, 1->yes], second->no discount, bonus shopping point [0->no, 1->yes], third->discounted, bonus growth point [0->no, 1->yes], forth->discounted, bonus shopping point [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/spubounds/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.spuBounds.spuId
                this.dataForm.growBounds = data.spuBounds.growBounds
                this.dataForm.buyBounds = data.spuBounds.buyBounds
                this.dataForm.work = data.spuBounds.work
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
              url: this.$http.adornUrl(`/coupon/spubounds/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuId': this.dataForm.spuId,
                'growBounds': this.dataForm.growBounds,
                'buyBounds': this.dataForm.buyBounds,
                'work': this.dataForm.work
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
