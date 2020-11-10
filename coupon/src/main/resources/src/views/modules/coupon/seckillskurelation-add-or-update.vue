<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="seckill activity id" prop="promotionId">
      <el-input v-model="dataForm.promotionId" placeholder="seckill activity id"></el-input>
    </el-form-item>
    <el-form-item label="seckill activity section id" prop="promotionSessionId">
      <el-input v-model="dataForm.promotionSessionId" placeholder="seckill activity section id"></el-input>
    </el-form-item>
    <el-form-item label="product id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="product id"></el-input>
    </el-form-item>
    <el-form-item label="seckill price" prop="seckillPrice">
      <el-input v-model="dataForm.seckillPrice" placeholder="seckill price"></el-input>
    </el-form-item>
    <el-form-item label="seckill quantity" prop="seckillCount">
      <el-input v-model="dataForm.seckillCount" placeholder="seckill quantity"></el-input>
    </el-form-item>
    <el-form-item label="purchase limit per person" prop="seckillLimit">
      <el-input v-model="dataForm.seckillLimit" placeholder="purchase limit per person"></el-input>
    </el-form-item>
    <el-form-item label="seckill sequence" prop="seckillSort">
      <el-input v-model="dataForm.seckillSort" placeholder="seckill sequence"></el-input>
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
          promotionId: '',
          promotionSessionId: '',
          skuId: '',
          seckillPrice: '',
          seckillCount: '',
          seckillLimit: '',
          seckillSort: ''
        },
        dataRule: {
          promotionId: [
            { required: true, message: 'seckill activity id can not be empty', trigger: 'blur' }
          ],
          promotionSessionId: [
            { required: true, message: 'seckill activity section id can not be empty', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: 'product id can not be empty', trigger: 'blur' }
          ],
          seckillPrice: [
            { required: true, message: 'seckill price can not be empty', trigger: 'blur' }
          ],
          seckillCount: [
            { required: true, message: 'seckill quantity can not be empty', trigger: 'blur' }
          ],
          seckillLimit: [
            { required: true, message: 'purchase limit per person can not be empty', trigger: 'blur' }
          ],
          seckillSort: [
            { required: true, message: 'seckill sequence can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/seckillskurelation/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.promotionId = data.seckillSkuRelation.promotionId
                this.dataForm.promotionSessionId = data.seckillSkuRelation.promotionSessionId
                this.dataForm.skuId = data.seckillSkuRelation.skuId
                this.dataForm.seckillPrice = data.seckillSkuRelation.seckillPrice
                this.dataForm.seckillCount = data.seckillSkuRelation.seckillCount
                this.dataForm.seckillLimit = data.seckillSkuRelation.seckillLimit
                this.dataForm.seckillSort = data.seckillSkuRelation.seckillSort
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
              url: this.$http.adornUrl(`/coupon/seckillskurelation/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'promotionId': this.dataForm.promotionId,
                'promotionSessionId': this.dataForm.promotionSessionId,
                'skuId': this.dataForm.skuId,
                'seckillPrice': this.dataForm.seckillPrice,
                'seckillCount': this.dataForm.seckillCount,
                'seckillLimit': this.dataForm.seckillLimit,
                'seckillSort': this.dataForm.seckillSort
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
