<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spu id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="image name" prop="imgName">
      <el-input v-model="dataForm.imgName" placeholder="image name"></el-input>
    </el-form-item>
    <el-form-item label="image link" prop="imgUrl">
      <el-input v-model="dataForm.imgUrl" placeholder="image link"></el-input>
    </el-form-item>
    <el-form-item label="image sequence" prop="imgSort">
      <el-input v-model="dataForm.imgSort" placeholder="image sequence"></el-input>
    </el-form-item>
    <el-form-item label="is default image [0->no, 1->yes]" prop="defaultImg">
      <el-input v-model="dataForm.defaultImg" placeholder="is default image [0->no, 1->yes]"></el-input>
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
          imgName: '',
          imgUrl: '',
          imgSort: '',
          defaultImg: ''
        },
        dataRule: {
          spuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          imgName: [
            { required: true, message: 'image name can not be empty', trigger: 'blur' }
          ],
          imgUrl: [
            { required: true, message: 'image link can not be empty', trigger: 'blur' }
          ],
          imgSort: [
            { required: true, message: 'image sequence can not be empty', trigger: 'blur' }
          ],
          defaultImg: [
            { required: true, message: 'is default image [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/commodity/spuimages/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuId = data.spuImages.spuId
                this.dataForm.imgName = data.spuImages.imgName
                this.dataForm.imgUrl = data.spuImages.imgUrl
                this.dataForm.imgSort = data.spuImages.imgSort
                this.dataForm.defaultImg = data.spuImages.defaultImg
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
              url: this.$http.adornUrl(`/commodity/spuimages/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuId': this.dataForm.spuId,
                'imgName': this.dataForm.imgName,
                'imgUrl': this.dataForm.imgUrl,
                'imgSort': this.dataForm.imgSort,
                'defaultImg': this.dataForm.defaultImg
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
