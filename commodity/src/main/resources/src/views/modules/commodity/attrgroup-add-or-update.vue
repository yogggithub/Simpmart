<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="group name" prop="attrGroupName">
      <el-input v-model="dataForm.attrGroupName" placeholder="group name"></el-input>
    </el-form-item>
    <el-form-item label="group sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="group sequence"></el-input>
    </el-form-item>
    <el-form-item label="group name" prop="descript">
      <el-input v-model="dataForm.descript" placeholder="group name"></el-input>
    </el-form-item>
    <el-form-item label="group icon" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="group icon"></el-input>
    </el-form-item>
    <el-form-item label="attribute category id" prop="catelogId">
      <el-input v-model="dataForm.catelogId" placeholder="attribute category id"></el-input>
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
          attrGroupId: 0,
          attrGroupName: '',
          sort: '',
          descript: '',
          icon: '',
          catelogId: ''
        },
        dataRule: {
          attrGroupName: [
            { required: true, message: 'group name can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'group sequence can not be empty', trigger: 'blur' }
          ],
          descript: [
            { required: true, message: 'group name can not be empty', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: 'group icon can not be empty', trigger: 'blur' }
          ],
          catelogId: [
            { required: true, message: 'attribute category id can not be empty', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.attrGroupId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.attrGroupId) {
            this.$http({
              url: this.$http.adornUrl(`/commodity/attrgroup/info/${this.dataForm.attrGroupId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.attrGroupName = data.attrGroup.attrGroupName
                this.dataForm.sort = data.attrGroup.sort
                this.dataForm.descript = data.attrGroup.descript
                this.dataForm.icon = data.attrGroup.icon
                this.dataForm.catelogId = data.attrGroup.catelogId
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
              url: this.$http.adornUrl(`/commodity/attrgroup/${!this.dataForm.attrGroupId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'attrGroupId': this.dataForm.attrGroupId || undefined,
                'attrGroupName': this.dataForm.attrGroupName,
                'sort': this.dataForm.sort,
                'descript': this.dataForm.descript,
                'icon': this.dataForm.icon,
                'catelogId': this.dataForm.catelogId
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
