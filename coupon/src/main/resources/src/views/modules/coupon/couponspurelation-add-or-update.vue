<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="coupon id" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="coupon id"></el-input>
    </el-form-item>
    <el-form-item label="spu id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="spu name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu name"></el-input>
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
          spuId: '',
          spuName: ''
        },
        dataRule: {
          couponId: [
            { required: true, message: 'coupon id can not be empty', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: 'spu name can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/couponspurelation/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponId = data.couponSpuRelation.couponId
                this.dataForm.spuId = data.couponSpuRelation.spuId
                this.dataForm.spuName = data.couponSpuRelation.spuName
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
              url: this.$http.adornUrl(`/coupon/couponspurelation/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponId': this.dataForm.couponId,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName
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
