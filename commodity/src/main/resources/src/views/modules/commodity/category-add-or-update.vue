<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="category name" prop="name">
      <el-input v-model="dataForm.name" placeholder="category name"></el-input>
    </el-form-item>
    <el-form-item label="parent category id" prop="parentCid">
      <el-input v-model="dataForm.parentCid" placeholder="parent category id"></el-input>
    </el-form-item>
    <el-form-item label="category level" prop="catLevel">
      <el-input v-model="dataForm.catLevel" placeholder="category level"></el-input>
    </el-form-item>
    <el-form-item label="showing status [0->no, 1->yes]" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="showing status [0->no, 1->yes]"></el-input>
    </el-form-item>
    <el-form-item label="category sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="category sequence"></el-input>
    </el-form-item>
    <el-form-item label="category icon link" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="category icon link"></el-input>
    </el-form-item>
    <el-form-item label="product unit" prop="productUnit">
      <el-input v-model="dataForm.productUnit" placeholder="product unit"></el-input>
    </el-form-item>
    <el-form-item label="product quantity" prop="productCount">
      <el-input v-model="dataForm.productCount" placeholder="product quantity"></el-input>
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
          catId: 0,
          name: '',
          parentCid: '',
          catLevel: '',
          showStatus: '',
          sort: '',
          icon: '',
          productUnit: '',
          productCount: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'category name can not be empty', trigger: 'blur' }
          ],
          parentCid: [
            { required: true, message: 'parent category id can not be empty', trigger: 'blur' }
          ],
          catLevel: [
            { required: true, message: 'category level can not be empty', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: 'showing status [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'category sequence can not be empty', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: 'category icon link can not be empty', trigger: 'blur' }
          ],
          productUnit: [
            { required: true, message: 'product unit can not be empty', trigger: 'blur' }
          ],
          productCount: [
            { required: true, message: 'product quantity can not be empty', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.catId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.catId) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/category/info/${this.dataForm.catId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.category.name
                this.dataForm.parentCid = data.category.parentCid
                this.dataForm.catLevel = data.category.catLevel
                this.dataForm.showStatus = data.category.showStatus
                this.dataForm.sort = data.category.sort
                this.dataForm.icon = data.category.icon
                this.dataForm.productUnit = data.category.productUnit
                this.dataForm.productCount = data.category.productCount
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
              url: this.$http.adornUrl(`/commodity/category/${!this.dataForm.catId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'catId': this.dataForm.catId || undefined,
                'name': this.dataForm.name,
                'parentCid': this.dataForm.parentCid,
                'catLevel': this.dataForm.catLevel,
                'showStatus': this.dataForm.showStatus,
                'sort': this.dataForm.sort,
                'icon': this.dataForm.icon,
                'productUnit': this.dataForm.productUnit,
                'productCount': this.dataForm.productCount
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
