<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="spu id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="spu name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="spu name"></el-input>
    </el-form-item>
    <el-form-item label="spu image" prop="spuImg">
      <el-input v-model="dataForm.spuImg" placeholder="spu image"></el-input>
    </el-form-item>
    <el-form-item label="create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="create time"></el-input>
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
          memberId: '',
          spuId: '',
          spuName: '',
          spuImg: '',
          createTime: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: 'spu name can not be empty', trigger: 'blur' }
          ],
          spuImg: [
            { required: true, message: 'spu image can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'create time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/membercollectspu/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberCollectSpu.memberId
                this.dataForm.spuId = data.memberCollectSpu.spuId
                this.dataForm.spuName = data.memberCollectSpu.spuName
                this.dataForm.spuImg = data.memberCollectSpu.spuImg
                this.dataForm.createTime = data.memberCollectSpu.createTime
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
              url: this.$http.adornUrl(`/member/membercollectspu/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'spuImg': this.dataForm.spuImg,
                'createTime': this.dataForm.createTime
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
