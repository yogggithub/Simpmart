<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="order_id" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="order_id"></el-input>
    </el-form-item>
    <el-form-item label="order_sn" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="order_sn"></el-input>
    </el-form-item>
    <el-form-item label="spu_id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu_id"></el-input>
    </el-form-item>
    <el-form-item label="spu_name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu_name"></el-input>
    </el-form-item>
    <el-form-item label="spu_pic" prop="spuPic">
      <el-input v-model="dataForm.spuPic" placeholder="spu_pic"></el-input>
    </el-form-item>
    <el-form-item label="brand" prop="spuBrand">
      <el-input v-model="dataForm.spuBrand" placeholder="brand"></el-input>
    </el-form-item>
    <el-form-item label="category id" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="category id"></el-input>
    </el-form-item>
    <el-form-item label="sku no." prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku no."></el-input>
    </el-form-item>
    <el-form-item label="sku name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku name"></el-input>
    </el-form-item>
    <el-form-item label="sku picture" prop="skuImg">
      <el-input v-model="dataForm.skuImg" placeholder="sku picture"></el-input>
    </el-form-item>
    <el-form-item label="sku price" prop="skuPrice">
      <el-input v-model="dataForm.skuPrice" placeholder="sku price"></el-input>
    </el-form-item>
    <el-form-item label="item(s) ordered quantity" prop="skuQuantity">
      <el-input v-model="dataForm.skuQuantity" placeholder="item(s) ordered quantity"></el-input>
    </el-form-item>
    <el-form-item label="sku attributes combination (JSON)" prop="skuAttrsVals">
      <el-input v-model="dataForm.skuAttrsVals" placeholder="sku attributes combination (JSON)"></el-input>
    </el-form-item>
    <el-form-item label="promotion amount" prop="promotionAmount">
      <el-input v-model="dataForm.promotionAmount" placeholder="promotion amount"></el-input>
    </el-form-item>
    <el-form-item label="coupon amount" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="coupon amount"></el-input>
    </el-form-item>
    <el-form-item label="member rewards amount" prop="integrationAmount">
      <el-input v-model="dataForm.integrationAmount" placeholder="member rewards amount"></el-input>
    </el-form-item>
    <el-form-item label="grand total after deduction" prop="realAmount">
      <el-input v-model="dataForm.realAmount" placeholder="grand total after deduction"></el-input>
    </el-form-item>
    <el-form-item label="bonus member rewards" prop="giftIntegration">
      <el-input v-model="dataForm.giftIntegration" placeholder="bonus member rewards"></el-input>
    </el-form-item>
    <el-form-item label="bonus growth points" prop="giftGrowth">
      <el-input v-model="dataForm.giftGrowth" placeholder="bonus growth points"></el-input>
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
          orderId: '',
          orderSn: '',
          spuId: '',
          spuName: '',
          spuPic: '',
          spuBrand: '',
          categoryId: '',
          skuId: '',
          skuName: '',
          skuImg: '',
          skuPrice: '',
          skuQuantity: '',
          skuAttrsVals: '',
          promotionAmount: '',
          couponAmount: '',
          integrationAmount: '',
          realAmount: '',
          giftIntegration: '',
          giftGrowth: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: 'order_id can not be empty', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: 'order_sn can not be empty', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu_id can not be empty', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: 'spu_name can not be empty', trigger: 'blur' }
          ],
          spuPic: [
            { required: true, message: 'spu_pic can not be empty', trigger: 'blur' }
          ],
          spuBrand: [
            { required: true, message: 'brand can not be empty', trigger: 'blur' }
          ],
          categoryId: [
            { required: true, message: 'category id can not be empty', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: 'sku no. can not be empty', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku name can not be empty', trigger: 'blur' }
          ],
          skuImg: [
            { required: true, message: 'sku picture can not be empty', trigger: 'blur' }
          ],
          skuPrice: [
            { required: true, message: 'sku price can not be empty', trigger: 'blur' }
          ],
          skuQuantity: [
            { required: true, message: 'item(s) ordered quantity can not be empty', trigger: 'blur' }
          ],
          skuAttrsVals: [
            { required: true, message: 'sku attributes combination (JSON) can not be empty', trigger: 'blur' }
          ],
          promotionAmount: [
            { required: true, message: 'promotion amount can not be empty', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: 'coupon amount can not be empty', trigger: 'blur' }
          ],
          integrationAmount: [
            { required: true, message: 'member rewards amount can not be empty', trigger: 'blur' }
          ],
          realAmount: [
            { required: true, message: 'grand total after deduction can not be empty', trigger: 'blur' }
          ],
          giftIntegration: [
            { required: true, message: 'bonus member rewards can not be empty', trigger: 'blur' }
          ],
          giftGrowth: [
            { required: true, message: 'bonus growth points can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/orderitem/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderItem.orderId
                this.dataForm.orderSn = data.orderItem.orderSn
                this.dataForm.spuId = data.orderItem.spuId
                this.dataForm.spuName = data.orderItem.spuName
                this.dataForm.spuPic = data.orderItem.spuPic
                this.dataForm.spuBrand = data.orderItem.spuBrand
                this.dataForm.categoryId = data.orderItem.categoryId
                this.dataForm.skuId = data.orderItem.skuId
                this.dataForm.skuName = data.orderItem.skuName
                this.dataForm.skuImg = data.orderItem.skuImg
                this.dataForm.skuPrice = data.orderItem.skuPrice
                this.dataForm.skuQuantity = data.orderItem.skuQuantity
                this.dataForm.skuAttrsVals = data.orderItem.skuAttrsVals
                this.dataForm.promotionAmount = data.orderItem.promotionAmount
                this.dataForm.couponAmount = data.orderItem.couponAmount
                this.dataForm.integrationAmount = data.orderItem.integrationAmount
                this.dataForm.realAmount = data.orderItem.realAmount
                this.dataForm.giftIntegration = data.orderItem.giftIntegration
                this.dataForm.giftGrowth = data.orderItem.giftGrowth
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
              url: this.$http.adornUrl(`/order/orderitem/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'orderSn': this.dataForm.orderSn,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'spuPic': this.dataForm.spuPic,
                'spuBrand': this.dataForm.spuBrand,
                'categoryId': this.dataForm.categoryId,
                'skuId': this.dataForm.skuId,
                'skuName': this.dataForm.skuName,
                'skuImg': this.dataForm.skuImg,
                'skuPrice': this.dataForm.skuPrice,
                'skuQuantity': this.dataForm.skuQuantity,
                'skuAttrsVals': this.dataForm.skuAttrsVals,
                'promotionAmount': this.dataForm.promotionAmount,
                'couponAmount': this.dataForm.couponAmount,
                'integrationAmount': this.dataForm.integrationAmount,
                'realAmount': this.dataForm.realAmount,
                'giftIntegration': this.dataForm.giftIntegration,
                'giftGrowth': this.dataForm.giftGrowth
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
