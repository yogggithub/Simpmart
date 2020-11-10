<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="total spent amount" prop="consumeAmount">
      <el-input v-model="dataForm.consumeAmount" placeholder="total spent amount"></el-input>
    </el-form-item>
    <el-form-item label="total discount amount" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="total discount amount"></el-input>
    </el-form-item>
    <el-form-item label="order quantity" prop="orderCount">
      <el-input v-model="dataForm.orderCount" placeholder="order quantity"></el-input>
    </el-form-item>
    <el-form-item label="coupon quantity" prop="couponCount">
      <el-input v-model="dataForm.couponCount" placeholder="coupon quantity"></el-input>
    </el-form-item>
    <el-form-item label="review quantity" prop="commentCount">
      <el-input v-model="dataForm.commentCount" placeholder="review quantity"></el-input>
    </el-form-item>
    <el-form-item label="return quantity" prop="returnOrderCount">
      <el-input v-model="dataForm.returnOrderCount" placeholder="return quantity"></el-input>
    </el-form-item>
    <el-form-item label="login quantity" prop="loginCount">
      <el-input v-model="dataForm.loginCount" placeholder="login quantity"></el-input>
    </el-form-item>
    <el-form-item label="favorite quantity" prop="attendCount">
      <el-input v-model="dataForm.attendCount" placeholder="favorite quantity"></el-input>
    </el-form-item>
    <el-form-item label="fans quantity" prop="fansCount">
      <el-input v-model="dataForm.fansCount" placeholder="fans quantity"></el-input>
    </el-form-item>
    <el-form-item label="production favorite quantity" prop="collectProductCount">
      <el-input v-model="dataForm.collectProductCount" placeholder="production favorite quantity"></el-input>
    </el-form-item>
    <el-form-item label="topic favorite quantity" prop="collectSubjectCount">
      <el-input v-model="dataForm.collectSubjectCount" placeholder="topic favorite quantity"></el-input>
    </el-form-item>
    <el-form-item label="review favorite quantity" prop="collectCommentCount">
      <el-input v-model="dataForm.collectCommentCount" placeholder="review favorite quantity"></el-input>
    </el-form-item>
    <el-form-item label="refer friends quantity" prop="inviteFriendCount">
      <el-input v-model="dataForm.inviteFriendCount" placeholder="refer friends quantity"></el-input>
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
          consumeAmount: '',
          couponAmount: '',
          orderCount: '',
          couponCount: '',
          commentCount: '',
          returnOrderCount: '',
          loginCount: '',
          attendCount: '',
          fansCount: '',
          collectProductCount: '',
          collectSubjectCount: '',
          collectCommentCount: '',
          inviteFriendCount: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          consumeAmount: [
            { required: true, message: 'total spent amount can not be empty', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: 'total discount amount can not be empty', trigger: 'blur' }
          ],
          orderCount: [
            { required: true, message: 'order quantity can not be empty', trigger: 'blur' }
          ],
          couponCount: [
            { required: true, message: 'coupon quantity can not be empty', trigger: 'blur' }
          ],
          commentCount: [
            { required: true, message: 'review quantity can not be empty', trigger: 'blur' }
          ],
          returnOrderCount: [
            { required: true, message: 'return quantity can not be empty', trigger: 'blur' }
          ],
          loginCount: [
            { required: true, message: 'login quantity can not be empty', trigger: 'blur' }
          ],
          attendCount: [
            { required: true, message: 'favorite quantity can not be empty', trigger: 'blur' }
          ],
          fansCount: [
            { required: true, message: 'fans quantity can not be empty', trigger: 'blur' }
          ],
          collectProductCount: [
            { required: true, message: 'production favorite quantity can not be empty', trigger: 'blur' }
          ],
          collectSubjectCount: [
            { required: true, message: 'topic favorite quantity can not be empty', trigger: 'blur' }
          ],
          collectCommentCount: [
            { required: true, message: 'review favorite quantity can not be empty', trigger: 'blur' }
          ],
          inviteFriendCount: [
            { required: true, message: 'refer friends quantity can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberstatisticsinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberStatisticsInfo.memberId
                this.dataForm.consumeAmount = data.memberStatisticsInfo.consumeAmount
                this.dataForm.couponAmount = data.memberStatisticsInfo.couponAmount
                this.dataForm.orderCount = data.memberStatisticsInfo.orderCount
                this.dataForm.couponCount = data.memberStatisticsInfo.couponCount
                this.dataForm.commentCount = data.memberStatisticsInfo.commentCount
                this.dataForm.returnOrderCount = data.memberStatisticsInfo.returnOrderCount
                this.dataForm.loginCount = data.memberStatisticsInfo.loginCount
                this.dataForm.attendCount = data.memberStatisticsInfo.attendCount
                this.dataForm.fansCount = data.memberStatisticsInfo.fansCount
                this.dataForm.collectProductCount = data.memberStatisticsInfo.collectProductCount
                this.dataForm.collectSubjectCount = data.memberStatisticsInfo.collectSubjectCount
                this.dataForm.collectCommentCount = data.memberStatisticsInfo.collectCommentCount
                this.dataForm.inviteFriendCount = data.memberStatisticsInfo.inviteFriendCount
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
              url: this.$http.adornUrl(`/member/memberstatisticsinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'consumeAmount': this.dataForm.consumeAmount,
                'couponAmount': this.dataForm.couponAmount,
                'orderCount': this.dataForm.orderCount,
                'couponCount': this.dataForm.couponCount,
                'commentCount': this.dataForm.commentCount,
                'returnOrderCount': this.dataForm.returnOrderCount,
                'loginCount': this.dataForm.loginCount,
                'attendCount': this.dataForm.attendCount,
                'fansCount': this.dataForm.fansCount,
                'collectProductCount': this.dataForm.collectProductCount,
                'collectSubjectCount': this.dataForm.collectSubjectCount,
                'collectCommentCount': this.dataForm.collectCommentCount,
                'inviteFriendCount': this.dataForm.inviteFriendCount
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
