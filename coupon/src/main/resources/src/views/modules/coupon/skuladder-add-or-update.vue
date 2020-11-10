<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spu id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="bought quantity" prop="fullCount">
      <el-input v-model="dataForm.fullCount" placeholder="bought quantity"></el-input>
    </el-form-item>
    <el-form-item label="discount" prop="discount">
      <el-input v-model="dataForm.discount" placeholder="discount"></el-input>
    </el-form-item>
    <el-form-item label="price after discount" prop="price">
      <el-input v-model="dataForm.price" placeholder="price after discount"></el-input>
    </el-form-item>
    <el-form-item label="eligible for other offers [0->no, 1->yes]" prop="addOther">
      <el-input v-model="dataForm.addOther" placeholder="eligible for other offers [0->no, 1->yes]"></el-input>
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
          fullCount: '',
          discount: '',
          price: '',
          addOther: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          fullCount: [
            { required: true, message: 'bought quantity can not be empty', trigger: 'blur' }
          ],
          discount: [
            { required: true, message: 'discount can not be empty', trigger: 'blur' }
          ],
          price: [
            { required: true, message: 'price after discount can not be empty', trigger: 'blur' }
          ],
          addOther: [
            { required: true, message: 'eligible for other offers [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/skuladder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.skuLadder.skuId
                this.dataForm.fullCount = data.skuLadder.fullCount
                this.dataForm.discount = data.skuLadder.discount
                this.dataForm.price = data.skuLadder.price
                this.dataForm.addOther = data.skuLadder.addOther
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
              url: this.$http.adornUrl(`/coupon/skuladder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'fullCount': this.dataForm.fullCount,
                'discount': this.dataForm.discount,
                'price': this.dataForm.price,
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
