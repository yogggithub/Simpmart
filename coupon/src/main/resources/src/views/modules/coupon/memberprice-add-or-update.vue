<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
    </el-form-item>
    <el-form-item label="member level id" prop="memberLevelId">
      <el-input v-model="dataForm.memberLevelId" placeholder="member level id"></el-input>
    </el-form-item>
    <el-form-item label="member level name" prop="memberLevelName">
      <el-input v-model="dataForm.memberLevelName" placeholder="member level name"></el-input>
    </el-form-item>
    <el-form-item label="member price" prop="memberPrice">
      <el-input v-model="dataForm.memberPrice" placeholder="member price"></el-input>
    </el-form-item>
    <el-form-item label="can used with other bargains [0->no, 1->yes]" prop="addOther">
      <el-input v-model="dataForm.addOther" placeholder="can used with other bargains [0->no, 1->yes]"></el-input>
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
          skuId: '',
          memberLevelId: '',
          memberLevelName: '',
          memberPrice: '',
          addOther: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'sku id can not be empty', trigger: 'blur' }
          ],
          memberLevelId: [
            { required: true, message: 'member level id can not be empty', trigger: 'blur' }
          ],
          memberLevelName: [
            { required: true, message: 'member level name can not be empty', trigger: 'blur' }
          ],
          memberPrice: [
            { required: true, message: 'member price can not be empty', trigger: 'blur' }
          ],
          addOther: [
            { required: true, message: 'can used with other bargains [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/memberprice/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.memberPrice.skuId
                this.dataForm.memberLevelId = data.memberPrice.memberLevelId
                this.dataForm.memberLevelName = data.memberPrice.memberLevelName
                this.dataForm.memberPrice = data.memberPrice.memberPrice
                this.dataForm.addOther = data.memberPrice.addOther
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
              url: this.$http.adornUrl(`/coupon/memberprice/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'memberLevelId': this.dataForm.memberLevelId,
                'memberLevelName': this.dataForm.memberLevelName,
                'memberPrice': this.dataForm.memberPrice,
                'addOther': this.dataForm.addOther
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
