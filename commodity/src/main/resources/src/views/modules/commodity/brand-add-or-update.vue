<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="brand name" prop="name">
      <el-input v-model="dataForm.name" placeholder="brand name"></el-input>
    </el-form-item>
    <el-form-item label="brand logo link" prop="logo">
      <el-input v-model="dataForm.logo" placeholder="brand logo link"></el-input>
    </el-form-item>
    <el-form-item label="brand description" prop="descript">
      <el-input v-model="dataForm.descript" placeholder="brand description"></el-input>
    </el-form-item>
    <el-form-item label="showing status [0->no, 1->yes]" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="showing status [0->no, 1->yes]"></el-input>
    </el-form-item>
    <el-form-item label="search for initial letter" prop="firstLetter">
      <el-input v-model="dataForm.firstLetter" placeholder="search for initial letter"></el-input>
    </el-form-item>
    <el-form-item label="brand sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="brand sequence"></el-input>
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
          brandId: 0,
          name: '',
          logo: '',
          descript: '',
          showStatus: '',
          firstLetter: '',
          sort: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'brand name can not be empty', trigger: 'blur' }
          ],
          logo: [
            { required: true, message: 'brand logo link can not be empty', trigger: 'blur' }
          ],
          descript: [
            { required: true, message: 'brand description can not be empty', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: 'showing status [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ],
          firstLetter: [
            { required: true, message: 'search for initial letter can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'brand sequence can not be empty', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.brandId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.brandId) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/brand/info/${this.dataForm.brandId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.brand.name
                this.dataForm.logo = data.brand.logo
                this.dataForm.descript = data.brand.descript
                this.dataForm.showStatus = data.brand.showStatus
                this.dataForm.firstLetter = data.brand.firstLetter
                this.dataForm.sort = data.brand.sort
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
              url: this.$http.adornUrl(`/commodity/brand/${!this.dataForm.brandId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'brandId': this.dataForm.brandId || undefined,
                'name': this.dataForm.name,
                'logo': this.dataForm.logo,
                'descript': this.dataForm.descript,
                'showStatus': this.dataForm.showStatus,
                'firstLetter': this.dataForm.firstLetter,
                'sort': this.dataForm.sort
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
