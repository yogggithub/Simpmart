<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member level id" prop="levelId">
      <el-input v-model="dataForm.levelId" placeholder="member level id"></el-input>
    </el-form-item>
    <el-form-item label="username" prop="username">
      <el-input v-model="dataForm.username" placeholder="username"></el-input>
    </el-form-item>
    <el-form-item label="password" prop="password">
      <el-input v-model="dataForm.password" placeholder="password"></el-input>
    </el-form-item>
    <el-form-item label="nickname" prop="nickname">
      <el-input v-model="dataForm.nickname" placeholder="nickname"></el-input>
    </el-form-item>
    <el-form-item label="phone number" prop="mobile">
      <el-input v-model="dataForm.mobile" placeholder="phone number"></el-input>
    </el-form-item>
    <el-form-item label="email" prop="email">
      <el-input v-model="dataForm.email" placeholder="email"></el-input>
    </el-form-item>
    <el-form-item label="profile image" prop="header">
      <el-input v-model="dataForm.header" placeholder="profile image"></el-input>
    </el-form-item>
    <el-form-item label="gender" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="gender"></el-input>
    </el-form-item>
    <el-form-item label="day of birth" prop="birth">
      <el-input v-model="dataForm.birth" placeholder="day of birth"></el-input>
    </el-form-item>
    <el-form-item label="city" prop="city">
      <el-input v-model="dataForm.city" placeholder="city"></el-input>
    </el-form-item>
    <el-form-item label="occupation" prop="job">
      <el-input v-model="dataForm.job" placeholder="occupation"></el-input>
    </el-form-item>
    <el-form-item label="personalized signature" prop="sign">
      <el-input v-model="dataForm.sign" placeholder="personalized signature"></el-input>
    </el-form-item>
    <el-form-item label="member source" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="member source"></el-input>
    </el-form-item>
    <el-form-item label="member rewards" prop="integration">
      <el-input v-model="dataForm.integration" placeholder="member rewards"></el-input>
    </el-form-item>
    <el-form-item label="member growth point" prop="growth">
      <el-input v-model="dataForm.growth" placeholder="member growth point"></el-input>
    </el-form-item>
    <el-form-item label="member status" prop="status">
      <el-input v-model="dataForm.status" placeholder="member status"></el-input>
    </el-form-item>
    <el-form-item label="register time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="register time"></el-input>
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
          levelId: '',
          username: '',
          password: '',
          nickname: '',
          mobile: '',
          email: '',
          header: '',
          gender: '',
          birth: '',
          city: '',
          job: '',
          sign: '',
          sourceType: '',
          integration: '',
          growth: '',
          status: '',
          createTime: ''
        },
        dataRule: {
          levelId: [
            { required: true, message: 'member level id can not be empty', trigger: 'blur' }
          ],
          username: [
            { required: true, message: 'username can not be empty', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'password can not be empty', trigger: 'blur' }
          ],
          nickname: [
            { required: true, message: 'nickname can not be empty', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: 'phone number can not be empty', trigger: 'blur' }
          ],
          email: [
            { required: true, message: 'email can not be empty', trigger: 'blur' }
          ],
          header: [
            { required: true, message: 'profile image can not be empty', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: 'gender can not be empty', trigger: 'blur' }
          ],
          birth: [
            { required: true, message: 'day of birth can not be empty', trigger: 'blur' }
          ],
          city: [
            { required: true, message: 'city can not be empty', trigger: 'blur' }
          ],
          job: [
            { required: true, message: 'occupation can not be empty', trigger: 'blur' }
          ],
          sign: [
            { required: true, message: 'personalized signature can not be empty', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: 'member source can not be empty', trigger: 'blur' }
          ],
          integration: [
            { required: true, message: 'member rewards can not be empty', trigger: 'blur' }
          ],
          growth: [
            { required: true, message: 'member growth point can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'member status can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'register time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/member/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.levelId = data.member.levelId
                this.dataForm.username = data.member.username
                this.dataForm.password = data.member.password
                this.dataForm.nickname = data.member.nickname
                this.dataForm.mobile = data.member.mobile
                this.dataForm.email = data.member.email
                this.dataForm.header = data.member.header
                this.dataForm.gender = data.member.gender
                this.dataForm.birth = data.member.birth
                this.dataForm.city = data.member.city
                this.dataForm.job = data.member.job
                this.dataForm.sign = data.member.sign
                this.dataForm.sourceType = data.member.sourceType
                this.dataForm.integration = data.member.integration
                this.dataForm.growth = data.member.growth
                this.dataForm.status = data.member.status
                this.dataForm.createTime = data.member.createTime
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
              url: this.$http.adornUrl(`/member/member/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'levelId': this.dataForm.levelId,
                'username': this.dataForm.username,
                'password': this.dataForm.password,
                'nickname': this.dataForm.nickname,
                'mobile': this.dataForm.mobile,
                'email': this.dataForm.email,
                'header': this.dataForm.header,
                'gender': this.dataForm.gender,
                'birth': this.dataForm.birth,
                'city': this.dataForm.city,
                'job': this.dataForm.job,
                'sign': this.dataForm.sign,
                'sourceType': this.dataForm.sourceType,
                'integration': this.dataForm.integration,
                'growth': this.dataForm.growth,
                'status': this.dataForm.status,
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
