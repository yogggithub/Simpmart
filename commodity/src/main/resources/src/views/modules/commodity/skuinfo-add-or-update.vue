<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spu Id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu Id"></el-input>
    </el-form-item>
    <el-form-item label="sku name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku name"></el-input>
    </el-form-item>
    <el-form-item label="sku description" prop="skuDesc">
      <el-input v-model="dataForm.skuDesc" placeholder="sku description"></el-input>
    </el-form-item>
    <el-form-item label="category id" prop="catalogId">
      <el-input v-model="dataForm.catalogId" placeholder="category id"></el-input>
    </el-form-item>
    <el-form-item label="brand id" prop="brandId">
      <el-input v-model="dataForm.brandId" placeholder="brand id"></el-input>
    </el-form-item>
    <el-form-item label="default image" prop="skuDefaultImg">
      <el-input v-model="dataForm.skuDefaultImg" placeholder="default image"></el-input>
    </el-form-item>
    <el-form-item label="sku title" prop="skuTitle">
      <el-input v-model="dataForm.skuTitle" placeholder="sku title"></el-input>
    </el-form-item>
    <el-form-item label="sku sub title" prop="skuSubtitle">
      <el-input v-model="dataForm.skuSubtitle" placeholder="sku sub title"></el-input>
    </el-form-item>
    <el-form-item label="price" prop="price">
      <el-input v-model="dataForm.price" placeholder="price"></el-input>
    </el-form-item>
    <el-form-item label="sales volume" prop="saleCount">
      <el-input v-model="dataForm.saleCount" placeholder="sales volume"></el-input>
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
          skuId: 0,
          spuId: '',
          skuName: '',
          skuDesc: '',
          catalogId: '',
          brandId: '',
          skuDefaultImg: '',
          skuTitle: '',
          skuSubtitle: '',
          price: '',
          saleCount: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: 'spu Id can not be empty', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku name can not be empty', trigger: 'blur' }
          ],
          skuDesc: [
            { required: true, message: 'sku description can not be empty', trigger: 'blur' }
          ],
          catalogId: [
            { required: true, message: 'category id can not be empty', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: 'brand id can not be empty', trigger: 'blur' }
          ],
          skuDefaultImg: [
            { required: true, message: 'default image can not be empty', trigger: 'blur' }
          ],
          skuTitle: [
            { required: true, message: 'sku title can not be empty', trigger: 'blur' }
          ],
          skuSubtitle: [
            { required: true, message: 'sku sub title can not be empty', trigger: 'blur' }
          ],
          price: [
            { required: true, message: 'price can not be empty', trigger: 'blur' }
          ],
          saleCount: [
            { required: true, message: 'sales volume can not be empty', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.skuId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.skuId) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/skuinfo/info/${this.dataForm.skuId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.skuInfo.spuId
                this.dataForm.skuName = data.skuInfo.skuName
                this.dataForm.skuDesc = data.skuInfo.skuDesc
                this.dataForm.catalogId = data.skuInfo.catalogId
                this.dataForm.brandId = data.skuInfo.brandId
                this.dataForm.skuDefaultImg = data.skuInfo.skuDefaultImg
                this.dataForm.skuTitle = data.skuInfo.skuTitle
                this.dataForm.skuSubtitle = data.skuInfo.skuSubtitle
                this.dataForm.price = data.skuInfo.price
                this.dataForm.saleCount = data.skuInfo.saleCount
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
              url: this.$http.adornUrl(`/commodity/skuinfo/${!this.dataForm.skuId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'skuId': this.dataForm.skuId || undefined,
                'spuId': this.dataForm.spuId,
                'skuName': this.dataForm.skuName,
                'skuDesc': this.dataForm.skuDesc,
                'catalogId': this.dataForm.catalogId,
                'brandId': this.dataForm.brandId,
                'skuDefaultImg': this.dataForm.skuDefaultImg,
                'skuTitle': this.dataForm.skuTitle,
                'skuSubtitle': this.dataForm.skuSubtitle,
                'price': this.dataForm.price,
                'saleCount': this.dataForm.saleCount
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
