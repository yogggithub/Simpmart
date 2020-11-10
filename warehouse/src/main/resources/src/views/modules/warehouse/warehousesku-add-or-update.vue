<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
    </el-form-item>
    <el-form-item label="warehouse id" prop="wareId">
      <el-input v-model="dataForm.wareId" placeholder="warehouse id"></el-input>
    </el-form-item>
    <el-form-item label="quantity in stock" prop="stock">
      <el-input v-model="dataForm.stock" placeholder="quantity in stock"></el-input>
    </el-form-item>
    <el-form-item label="sku name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku name"></el-input>
    </el-form-item>
    <el-form-item label="locked inventory" prop="stockLocked">
      <el-input v-model="dataForm.stockLocked" placeholder="locked inventory"></el-input>
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
          wareId: '',
          stock: '',
          skuName: '',
          stockLocked: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'sku id can not be empty', trigger: 'blur' }
          ],
          wareId: [
            { required: true, message: 'warehouse id can not be empty', trigger: 'blur' }
          ],
          stock: [
            { required: true, message: 'quantity in stock can not be empty', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku name can not be empty', trigger: 'blur' }
          ],
          stockLocked: [
            { required: true, message: 'locked inventory can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/warehouse/warehousesku/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.warehouseSku.skuId
                this.dataForm.wareId = data.warehouseSku.wareId
                this.dataForm.stock = data.warehouseSku.stock
                this.dataForm.skuName = data.warehouseSku.skuName
                this.dataForm.stockLocked = data.warehouseSku.stockLocked
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
              url: this.$http.adornUrl(`/warehouse/warehousesku/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'wareId': this.dataForm.wareId,
                'stock': this.dataForm.stock,
                'skuName': this.dataForm.skuName,
                'stockLocked': this.dataForm.stockLocked
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
