<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="topic name" prop="name">
      <el-input v-model="dataForm.name" placeholder="topic name"></el-input>
    </el-form-item>
    <el-form-item label="topic title" prop="title">
      <el-input v-model="dataForm.title" placeholder="topic title"></el-input>
    </el-form-item>
    <el-form-item label="topic sub title" prop="subTitle">
      <el-input v-model="dataForm.subTitle" placeholder="topic sub title"></el-input>
    </el-form-item>
    <el-form-item label="topic showing status" prop="status">
      <el-input v-model="dataForm.status" placeholder="topic showing status"></el-input>
    </el-form-item>
    <el-form-item label="topic content link" prop="url">
      <el-input v-model="dataForm.url" placeholder="topic content link"></el-input>
    </el-form-item>
    <el-form-item label="topic sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="topic sequence"></el-input>
    </el-form-item>
    <el-form-item label="topic image link" prop="img">
      <el-input v-model="dataForm.img" placeholder="topic image link"></el-input>
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
          title: '',
          subTitle: '',
          status: '',
          url: '',
          sort: '',
          img: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'topic name can not be empty', trigger: 'blur' }
          ],
          title: [
            { required: true, message: 'topic title can not be empty', trigger: 'blur' }
          ],
          subTitle: [
            { required: true, message: 'topic sub title can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'topic showing status can not be empty', trigger: 'blur' }
          ],
          url: [
            { required: true, message: 'topic content link can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'topic sequence can not be empty', trigger: 'blur' }
          ],
          img: [
            { required: true, message: 'topic image link can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/homesubject/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.homeSubject.name
                this.dataForm.title = data.homeSubject.title
                this.dataForm.subTitle = data.homeSubject.subTitle
                this.dataForm.status = data.homeSubject.status
                this.dataForm.url = data.homeSubject.url
                this.dataForm.sort = data.homeSubject.sort
                this.dataForm.img = data.homeSubject.img
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
              url: this.$http.adornUrl(`/coupon/homesubject/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'title': this.dataForm.title,
                'subTitle': this.dataForm.subTitle,
                'status': this.dataForm.status,
                'url': this.dataForm.url,
                'sort': this.dataForm.sort,
                'img': this.dataForm.img
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
