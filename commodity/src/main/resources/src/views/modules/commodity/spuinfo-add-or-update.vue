<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="spu name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu name"></el-input>
    </el-form-item>
    <el-form-item label="spu description" prop="spuDescription">
      <el-input v-model="dataForm.spuDescription" placeholder="spu description"></el-input>
    </el-form-item>
    <el-form-item label="category id" prop="catalogId">
      <el-input v-model="dataForm.catalogId" placeholder="category id"></el-input>
    </el-form-item>
    <el-form-item label="brand id" prop="brandId">
      <el-input v-model="dataForm.brandId" placeholder="brand id"></el-input>
    </el-form-item>
    <el-form-item label="product weight" prop="weight">
      <el-input v-model="dataForm.weight" placeholder="product weight"></el-input>
    </el-form-item>
    <el-form-item label="Is the product on sale [0->no, 1->yes]" prop="publishStatus">
      <el-input v-model="dataForm.publishStatus" placeholder="Is the product on sale [0->no, 1->yes]"></el-input>
    </el-form-item>
    <el-form-item label="spu create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="spu create time"></el-input>
    </el-form-item>
    <el-form-item label="spu update time" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder="spu update time"></el-input>
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
          spuName: '',
          spuDescription: '',
          catalogId: '',
          brandId: '',
          weight: '',
          publishStatus: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          spuName: [
            { required: true, message: 'spu name can not be empty', trigger: 'blur' }
          ],
          spuDescription: [
            { required: true, message: 'spu description can not be empty', trigger: 'blur' }
          ],
          catalogId: [
            { required: true, message: 'category id can not be empty', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: 'brand id can not be empty', trigger: 'blur' }
          ],
          weight: [
            { required: true, message: 'product weight can not be empty', trigger: 'blur' }
          ],
          publishStatus: [
            { required: true, message: 'Is the product on sale [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'spu create time can not be empty', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: 'spu update time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/commodity/spuinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuName = data.spuInfo.spuName
                this.dataForm.spuDescription = data.spuInfo.spuDescription
                this.dataForm.catalogId = data.spuInfo.catalogId
                this.dataForm.brandId = data.spuInfo.brandId
                this.dataForm.weight = data.spuInfo.weight
                this.dataForm.publishStatus = data.spuInfo.publishStatus
                this.dataForm.createTime = data.spuInfo.createTime
                this.dataForm.updateTime = data.spuInfo.updateTime
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
              url: this.$http.adornUrl(`/commodity/spuinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuName': this.dataForm.spuName,
                'spuDescription': this.dataForm.spuDescription,
                'catalogId': this.dataForm.catalogId,
                'brandId': this.dataForm.brandId,
                'weight': this.dataForm.weight,
                'publishStatus': this.dataForm.publishStatus,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
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
