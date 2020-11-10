<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="product id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="product id"></el-input>
    </el-form-item>
    <el-form-item label="attribute id" prop="attrId">
      <el-input v-model="dataForm.attrId" placeholder="attribute id"></el-input>
    </el-form-item>
    <el-form-item label="attribute name" prop="attrName">
      <el-input v-model="dataForm.attrName" placeholder="attribute name"></el-input>
    </el-form-item>
    <el-form-item label="attribute value" prop="attrValue">
      <el-input v-model="dataForm.attrValue" placeholder="attribute value"></el-input>
    </el-form-item>
    <el-form-item label="attribute sequence" prop="attrSort">
      <el-input v-model="dataForm.attrSort" placeholder="attribute sequence"></el-input>
    </el-form-item>
    <el-form-item label="displayed on the product [0->no, 1->yes]" prop="quickShow">
      <el-input v-model="dataForm.quickShow" placeholder="displayed on the product [0->no, 1->yes]"></el-input>
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
          attrId: '',
          attrName: '',
          attrValue: '',
          attrSort: '',
          quickShow: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: 'product id can not be empty', trigger: 'blur' }
          ],
          attrId: [
            { required: true, message: 'attribute id can not be empty', trigger: 'blur' }
          ],
          attrName: [
            { required: true, message: 'attribute name can not be empty', trigger: 'blur' }
          ],
          attrValue: [
            { required: true, message: 'attribute value can not be empty', trigger: 'blur' }
          ],
          attrSort: [
            { required: true, message: 'attribute sequence can not be empty', trigger: 'blur' }
          ],
          quickShow: [
            { required: true, message: 'displayed on the product [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/commodity/productattrvalue/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.productAttrValue.spuId
                this.dataForm.attrId = data.productAttrValue.attrId
                this.dataForm.attrName = data.productAttrValue.attrName
                this.dataForm.attrValue = data.productAttrValue.attrValue
                this.dataForm.attrSort = data.productAttrValue.attrSort
                this.dataForm.quickShow = data.productAttrValue.quickShow
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
              url: this.$http.adornUrl(`/commodity/productattrvalue/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuId': this.dataForm.spuId,
                'attrId': this.dataForm.attrId,
                'attrName': this.dataForm.attrName,
                'attrValue': this.dataForm.attrValue,
                'attrSort': this.dataForm.attrSort,
                'quickShow': this.dataForm.quickShow
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
