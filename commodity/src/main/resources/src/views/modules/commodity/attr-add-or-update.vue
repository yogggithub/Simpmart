<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="attribute name" prop="attrName">
      <el-input v-model="dataForm.attrName" placeholder="attribute name"></el-input>
    </el-form-item>
    <el-form-item label="need to search [0->no, 1->yes]" prop="searchType">
      <el-input v-model="dataForm.searchType" placeholder="need to search [0->no, 1->yes]"></el-input>
    </el-form-item>
    <el-form-item label="attribute icon" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="attribute icon"></el-input>
    </el-form-item>
    <el-form-item label="List of available values, seperated by comma" prop="valueSelect">
      <el-input v-model="dataForm.valueSelect" placeholder="List of available values, seperated by comma"></el-input>
    </el-form-item>
    <el-form-item label="attribute type [0->sales, 1->basic, 2->both]" prop="attrType">
      <el-input v-model="dataForm.attrType" placeholder="attribute type [0->sales, 1->basic, 2->both]"></el-input>
    </el-form-item>
    <el-form-item label="attribute status [0->disable, 1->enable]" prop="enable">
      <el-input v-model="dataForm.enable" placeholder="attribute status [0->disable, 1->enable]"></el-input>
    </el-form-item>
    <el-form-item label="attribute category id" prop="catelogId">
      <el-input v-model="dataForm.catelogId" placeholder="attribute category id"></el-input>
    </el-form-item>
    <el-form-item label="displayed on the product, can also be adjusted in spu [0->no, 1->yes]" prop="showDesc">
      <el-input v-model="dataForm.showDesc" placeholder="displayed on the product, can also be adjusted in spu [0->no, 1->yes]"></el-input>
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
          attrId: 0,
          attrName: '',
          searchType: '',
          icon: '',
          valueSelect: '',
          attrType: '',
          enable: '',
          catelogId: '',
          showDesc: ''
        },
        dataRule: {
          attrName: [
            { required: true, message: 'attribute name can not be empty', trigger: 'blur' }
          ],
          searchType: [
            { required: true, message: 'need to search [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: 'attribute icon can not be empty', trigger: 'blur' }
          ],
          valueSelect: [
            { required: true, message: 'List of available values, seperated by comma can not be empty', trigger: 'blur' }
          ],
          attrType: [
            { required: true, message: 'attribute type [0->sales, 1->basic, 2->both] can not be empty', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: 'attribute status [0->disable, 1->enable] can not be empty', trigger: 'blur' }
          ],
          catelogId: [
            { required: true, message: 'attribute category id can not be empty', trigger: 'blur' }
          ],
          showDesc: [
            { required: true, message: 'displayed on the product, can also be adjusted in spu [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.attrId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.attrId) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/attr/info/${this.dataForm.attrId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.attrName = data.attr.attrName
                this.dataForm.searchType = data.attr.searchType
                this.dataForm.icon = data.attr.icon
                this.dataForm.valueSelect = data.attr.valueSelect
                this.dataForm.attrType = data.attr.attrType
                this.dataForm.enable = data.attr.enable
                this.dataForm.catelogId = data.attr.catelogId
                this.dataForm.showDesc = data.attr.showDesc
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
              url: this.$http.adornUrl(`/commodity/attr/${!this.dataForm.attrId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'attrId': this.dataForm.attrId || undefined,
                'attrName': this.dataForm.attrName,
                'searchType': this.dataForm.searchType,
                'icon': this.dataForm.icon,
                'valueSelect': this.dataForm.valueSelect,
                'attrType': this.dataForm.attrType,
                'enable': this.dataForm.enable,
                'catelogId': this.dataForm.catelogId,
                'showDesc': this.dataForm.showDesc
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
