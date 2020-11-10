<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="ad name" prop="name">
      <el-input v-model="dataForm.name" placeholder="ad name"></el-input>
    </el-form-item>
    <el-form-item label="ad image link" prop="pic">
      <el-input v-model="dataForm.pic" placeholder="ad image link"></el-input>
    </el-form-item>
    <el-form-item label="ad start time" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="ad start time"></el-input>
    </el-form-item>
    <el-form-item label="ad end time" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="ad end time"></el-input>
    </el-form-item>
    <el-form-item label="ad status" prop="status">
      <el-input v-model="dataForm.status" placeholder="ad status"></el-input>
    </el-form-item>
    <el-form-item label="ad click count" prop="clickCount">
      <el-input v-model="dataForm.clickCount" placeholder="ad click count"></el-input>
    </el-form-item>
    <el-form-item label="ad hyperlink" prop="url">
      <el-input v-model="dataForm.url" placeholder="ad hyperlink"></el-input>
    </el-form-item>
    <el-form-item label="ad note" prop="note">
      <el-input v-model="dataForm.note" placeholder="ad note"></el-input>
    </el-form-item>
    <el-form-item label="ad sequence" prop="sort">
      <el-input v-model="dataForm.sort" placeholder="ad sequence"></el-input>
    </el-form-item>
    <el-form-item label="advertiser" prop="publisherId">
      <el-input v-model="dataForm.publisherId" placeholder="advertiser"></el-input>
    </el-form-item>
    <el-form-item label="ad authorization" prop="authId">
      <el-input v-model="dataForm.authId" placeholder="ad authorization"></el-input>
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
          pic: '',
          startTime: '',
          endTime: '',
          status: '',
          clickCount: '',
          url: '',
          note: '',
          sort: '',
          publisherId: '',
          authId: ''
        },
        dataRule: {
          name: [
            { required: true, message: 'ad name can not be empty', trigger: 'blur' }
          ],
          pic: [
            { required: true, message: 'ad image link can not be empty', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: 'ad start time can not be empty', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: 'ad end time can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'ad status can not be empty', trigger: 'blur' }
          ],
          clickCount: [
            { required: true, message: 'ad click count can not be empty', trigger: 'blur' }
          ],
          url: [
            { required: true, message: 'ad hyperlink can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'ad note can not be empty', trigger: 'blur' }
          ],
          sort: [
            { required: true, message: 'ad sequence can not be empty', trigger: 'blur' }
          ],
          publisherId: [
            { required: true, message: 'advertiser can not be empty', trigger: 'blur' }
          ],
          authId: [
            { required: true, message: 'ad authorization can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/homeadv/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.homeAdv.name
                this.dataForm.pic = data.homeAdv.pic
                this.dataForm.startTime = data.homeAdv.startTime
                this.dataForm.endTime = data.homeAdv.endTime
                this.dataForm.status = data.homeAdv.status
                this.dataForm.clickCount = data.homeAdv.clickCount
                this.dataForm.url = data.homeAdv.url
                this.dataForm.note = data.homeAdv.note
                this.dataForm.sort = data.homeAdv.sort
                this.dataForm.publisherId = data.homeAdv.publisherId
                this.dataForm.authId = data.homeAdv.authId
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
              url: this.$http.adornUrl(`/coupon/homeadv/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'pic': this.dataForm.pic,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'status': this.dataForm.status,
                'clickCount': this.dataForm.clickCount,
                'url': this.dataForm.url,
                'note': this.dataForm.note,
                'sort': this.dataForm.sort,
                'publisherId': this.dataForm.publisherId,
                'authId': this.dataForm.authId
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
