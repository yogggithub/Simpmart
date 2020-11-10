<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="level name" prop="name">
      <el-input v-model="dataForm.name" placeholder="level name"></el-input>
    </el-form-item>
    <el-form-item label="required growth point" prop="growthPoint">
      <el-input v-model="dataForm.growthPoint" placeholder="required growth point"></el-input>
    </el-form-item>
    <el-form-item label="default level [0 -> no, 1 -> yes]" prop="defaultStatus">
      <el-input v-model="dataForm.defaultStatus" placeholder="default level [0 -> no, 1 -> yes]"></el-input>
    </el-form-item>
    <el-form-item label="standard for free shipping" prop="freeFreightPoint">
      <el-input v-model="dataForm.freeFreightPoint" placeholder="standard for free shipping"></el-input>
    </el-form-item>
    <el-form-item label="growth point gained per review" prop="commentGrowthPoint">
      <el-input v-model="dataForm.commentGrowthPoint" placeholder="growth point gained per review"></el-input>
    </el-form-item>
    <el-form-item label="free shipping or not [0 -> no, 1 -> yes]" prop="priviledgeFreeFreight">
      <el-input v-model="dataForm.priviledgeFreeFreight" placeholder="free shipping or not [0 -> no, 1 -> yes]"></el-input>
    </el-form-item>
    <el-form-item label="member specific price or not [0 -> no, 1 -> yes]" prop="priviledgeMemberPrice">
      <el-input v-model="dataForm.priviledgeMemberPrice" placeholder="member specific price or not [0 -> no, 1 -> yes]"></el-input>
    </el-form-item>
    <el-form-item label="birth day specific discount or not [0 -> no, 1 -> yes]" prop="priviledgeBirthday">
      <el-input v-model="dataForm.priviledgeBirthday" placeholder="birth day specific discount or not [0 -> no, 1 -> yes]"></el-input>
    </el-form-item>
    <el-form-item label="not" prop="note">
      <el-input v-model="dataForm.note" placeholder="not"></el-input>
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
          growthPoint: '',
          defaultStatus: '',
          freeFreightPoint: '',
          commentGrowthPoint: '',
          priviledgeFreeFreight: '',
          priviledgeMemberPrice: '',
          priviledgeBirthday: '',
          note: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'level name can not be empty', trigger: 'blur' }
          ],
          growthPoint: [
            { required: true, message: 'required growth point can not be empty', trigger: 'blur' }
          ],
          defaultStatus: [
            { required: true, message: 'default level [0 -> no, 1 -> yes] can not be empty', trigger: 'blur' }
          ],
          freeFreightPoint: [
            { required: true, message: 'standard for free shipping can not be empty', trigger: 'blur' }
          ],
          commentGrowthPoint: [
            { required: true, message: 'growth point gained per review can not be empty', trigger: 'blur' }
          ],
          priviledgeFreeFreight: [
            { required: true, message: 'free shipping or not [0 -> no, 1 -> yes] can not be empty', trigger: 'blur' }
          ],
          priviledgeMemberPrice: [
            { required: true, message: 'member specific price or not [0 -> no, 1 -> yes] can not be empty', trigger: 'blur' }
          ],
          priviledgeBirthday: [
            { required: true, message: 'birth day specific discount or not [0 -> no, 1 -> yes] can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'not can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberlevel/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.memberLevel.name
                this.dataForm.growthPoint = data.memberLevel.growthPoint
                this.dataForm.defaultStatus = data.memberLevel.defaultStatus
                this.dataForm.freeFreightPoint = data.memberLevel.freeFreightPoint
                this.dataForm.commentGrowthPoint = data.memberLevel.commentGrowthPoint
                this.dataForm.priviledgeFreeFreight = data.memberLevel.priviledgeFreeFreight
                this.dataForm.priviledgeMemberPrice = data.memberLevel.priviledgeMemberPrice
                this.dataForm.priviledgeBirthday = data.memberLevel.priviledgeBirthday
                this.dataForm.note = data.memberLevel.note
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
              url: this.$http.adornUrl(`/member/memberlevel/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'growthPoint': this.dataForm.growthPoint,
                'defaultStatus': this.dataForm.defaultStatus,
                'freeFreightPoint': this.dataForm.freeFreightPoint,
                'commentGrowthPoint': this.dataForm.commentGrowthPoint,
                'priviledgeFreeFreight': this.dataForm.priviledgeFreeFreight,
                'priviledgeMemberPrice': this.dataForm.priviledgeMemberPrice,
                'priviledgeBirthday': this.dataForm.priviledgeBirthday,
                'note': this.dataForm.note
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
