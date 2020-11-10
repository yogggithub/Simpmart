<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="topic name" prop="name">
      <el-input v-model="dataForm.name" placeholder="topic name"></el-input>
    </el-form-item>
    <el-form-item label="topic id" prop="subjectId">
      <el-input v-model="dataForm.subjectId" placeholder="topic id"></el-input>
    </el-form-item>
    <el-form-item label="spu id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="spu sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="spu sequence"></el-input>
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
          name: '',
          subjectId: '',
          spuId: '',
          sort: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'topic name can not be empty', trigger: 'blur' }
          ],
          subjectId: [
            { required: true, message: 'topic id can not be empty', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'spu sequence can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/homesubjectspu/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.homeSubjectSpu.name
                this.dataForm.subjectId = data.homeSubjectSpu.subjectId
                this.dataForm.spuId = data.homeSubjectSpu.spuId
                this.dataForm.sort = data.homeSubjectSpu.sort
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
              url: this.$http.adornUrl(`/coupon/homesubjectspu/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'subjectId': this.dataForm.subjectId,
                'spuId': this.dataForm.spuId,
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
