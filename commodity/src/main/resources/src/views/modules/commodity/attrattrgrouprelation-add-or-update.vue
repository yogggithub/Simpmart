<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="attribute id" prop="attrId">
      <el-input v-model="dataForm.attrId" placeholder="attribute id"></el-input>
    </el-form-item>
    <el-form-item label="attribute grouping id" prop="attrGroupId">
      <el-input v-model="dataForm.attrGroupId" placeholder="attribute grouping id"></el-input>
    </el-form-item>
    <el-form-item label="attribute sequence" prop="attrSort">
      <el-input v-model="dataForm.attrSort" placeholder="attribute sequence"></el-input>
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
          attrId: '',
          attrGroupId: '',
          attrSort: ''
        },
        dataRule: {
          attrId: [
            { required: true, message: 'attribute id can not be empty', trigger: 'blur' }
          ],
          attrGroupId: [
            { required: true, message: 'attribute grouping id can not be empty', trigger: 'blur' }
          ],
          attrSort: [
            { required: true, message: 'attribute sequence can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/commodity/attrattrgrouprelation/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.attrId = data.attrAttrgroupRelation.attrId
                this.dataForm.attrGroupId = data.attrAttrgroupRelation.attrGroupId
                this.dataForm.attrSort = data.attrAttrgroupRelation.attrSort
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
              url: this.$http.adornUrl(`/commodity/attrattrgrouprelation/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'attrId': this.dataForm.attrId,
                'attrGroupId': this.dataForm.attrGroupId,
                'attrSort': this.dataForm.attrSort
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
