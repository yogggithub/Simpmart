<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="create time"></el-input>
    </el-form-item>
    <el-form-item label="changd value (postive / negatvie)" prop="changeCount">
      <el-input v-model="dataForm.changeCount" placeholder="changd value (postive / negatvie)"></el-input>
    </el-form-item>
    <el-form-item label="member note" prop="note">
      <el-input v-model="dataForm.note" placeholder="member note"></el-input>
    </el-form-item>
    <el-form-item label="point source [0 -> shopping, 1 -> manual addition, 2 -> promotion activity]" prop="sourceTyoe">
      <el-input v-model="dataForm.sourceTyoe" placeholder="point source [0 -> shopping, 1 -> manual addition, 2 -> promotion activity]"></el-input>
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
          createTime: '',
          changeCount: '',
          note: '',
          sourceTyoe: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'create time can not be empty', trigger: 'blur' }
          ],
          changeCount: [
            { required: true, message: 'changd value (postive / negatvie) can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'member note can not be empty', trigger: 'blur' }
          ],
          sourceTyoe: [
            { required: true, message: 'point source [0 -> shopping, 1 -> manual addition, 2 -> promotion activity] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/integrationchangehistory/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.integrationChangeHistory.memberId
                this.dataForm.createTime = data.integrationChangeHistory.createTime
                this.dataForm.changeCount = data.integrationChangeHistory.changeCount
                this.dataForm.note = data.integrationChangeHistory.note
                this.dataForm.sourceTyoe = data.integrationChangeHistory.sourceTyoe
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
              url: this.$http.adornUrl(`/member/integrationchangehistory/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'createTime': this.dataForm.createTime,
                'changeCount': this.dataForm.changeCount,
                'note': this.dataForm.note,
                'sourceTyoe': this.dataForm.sourceTyoe
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
