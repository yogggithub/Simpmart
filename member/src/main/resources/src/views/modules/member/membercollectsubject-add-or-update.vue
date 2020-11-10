<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="topic id" prop="subjectId">
      <el-input v-model="dataForm.subjectId" placeholder="topic id"></el-input>
    </el-form-item>
    <el-form-item label="topic name" prop="subjectName">
      <el-input v-model="dataForm.subjectName" placeholder="topic name"></el-input>
    </el-form-item>
    <el-form-item label="topic image link" prop="subjectImg">
      <el-input v-model="dataForm.subjectImg" placeholder="topic image link"></el-input>
    </el-form-item>
    <el-form-item label="topic content link" prop="subjectUrl">
      <el-input v-model="dataForm.subjectUrl" placeholder="topic content link"></el-input>
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
          subjectId: '',
          subjectName: '',
          subjectImg: '',
          subjectUrl: ''
        },
        dataRule: {
          subjectId: [
            { required: true, message: 'topic id can not be empty', trigger: 'blur' }
          ],
          subjectName: [
            { required: true, message: 'topic name can not be empty', trigger: 'blur' }
          ],
          subjectImg: [
            { required: true, message: 'topic image link can not be empty', trigger: 'blur' }
          ],
          subjectUrl: [
            { required: true, message: 'topic content link can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/membercollectsubject/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.subjectId = data.memberCollectSubject.subjectId
                this.dataForm.subjectName = data.memberCollectSubject.subjectName
                this.dataForm.subjectImg = data.memberCollectSubject.subjectImg
                this.dataForm.subjectUrl = data.memberCollectSubject.subjectUrl
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
              url: this.$http.adornUrl(`/member/membercollectsubject/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'subjectId': this.dataForm.subjectId,
                'subjectName': this.dataForm.subjectName,
                'subjectImg': this.dataForm.subjectImg,
                'subjectUrl': this.dataForm.subjectUrl
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
