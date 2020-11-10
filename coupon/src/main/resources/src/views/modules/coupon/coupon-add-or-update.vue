<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="coupontype [0->store wide bonus, 1->member gift, 2->shopping bonus, 3->register gift]" prop="couponType">
      <el-input v-model="dataForm.couponType" placeholder="coupontype [0->store wide bonus, 1->member gift, 2->shopping bonus, 3->register gift]"></el-input>
    </el-form-item>
    <el-form-item label="coupon image" prop="couponImg">
      <el-input v-model="dataForm.couponImg" placeholder="coupon image"></el-input>
    </el-form-item>
    <el-form-item label="coupon name" prop="couponName">
      <el-input v-model="dataForm.couponName" placeholder="coupon name"></el-input>
    </el-form-item>
    <el-form-item label="coupon quantity" prop="num">
      <el-input v-model="dataForm.num" placeholder="coupon quantity"></el-input>
    </el-form-item>
    <el-form-item label="coupon amount" prop="amount">
      <el-input v-model="dataForm.amount" placeholder="coupon amount"></el-input>
    </el-form-item>
    <el-form-item label="limitatiton per customer" prop="perLimit">
      <el-input v-model="dataForm.perLimit" placeholder="limitatiton per customer"></el-input>
    </el-form-item>
    <el-form-item label="usage threshold" prop="minPoint">
      <el-input v-model="dataForm.minPoint" placeholder="usage threshold"></el-input>
    </el-form-item>
    <el-form-item label="coupon start time" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="coupon start time"></el-input>
    </el-form-item>
    <el-form-item label="coupon end time" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="coupon end time"></el-input>
    </el-form-item>
    <el-form-item label="usage type [0->store wide, 1->specific catory, 2->specific item]" prop="useType">
      <el-input v-model="dataForm.useType" placeholder="usage type [0->store wide, 1->specific catory, 2->specific item]"></el-input>
    </el-form-item>
    <el-form-item label="coupon note" prop="note">
      <el-input v-model="dataForm.note" placeholder="coupon note"></el-input>
    </el-form-item>
    <el-form-item label="publish quantity" prop="publishCount">
      <el-input v-model="dataForm.publishCount" placeholder="publish quantity"></el-input>
    </el-form-item>
    <el-form-item label="used quantity" prop="useCount">
      <el-input v-model="dataForm.useCount" placeholder="used quantity"></el-input>
    </el-form-item>
    <el-form-item label="collected quantity" prop="receiveCount">
      <el-input v-model="dataForm.receiveCount" placeholder="collected quantity"></el-input>
    </el-form-item>
    <el-form-item label="collection period start time" prop="enableStartTime">
      <el-input v-model="dataForm.enableStartTime" placeholder="collection period start time"></el-input>
    </el-form-item>
    <el-form-item label="collection period end time" prop="enableEndTime">
      <el-input v-model="dataForm.enableEndTime" placeholder="collection period end time"></el-input>
    </el-form-item>
    <el-form-item label="promo code" prop="code">
      <el-input v-model="dataForm.code" placeholder="promo code"></el-input>
    </el-form-item>
    <el-form-item label="member level threshold [0->no limitatiton, other->corresponding member level]" prop="memberLevel">
      <el-input v-model="dataForm.memberLevel" placeholder="member level threshold [0->no limitatiton, other->corresponding member level]"></el-input>
    </el-form-item>
    <el-form-item label="publish status [0->unpublished, 1->published]" prop="publish">
      <el-input v-model="dataForm.publish" placeholder="publish status [0->unpublished, 1->published]"></el-input>
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
          couponType: '',
          couponImg: '',
          couponName: '',
          num: '',
          amount: '',
          perLimit: '',
          minPoint: '',
          startTime: '',
          endTime: '',
          useType: '',
          note: '',
          publishCount: '',
          useCount: '',
          receiveCount: '',
          enableStartTime: '',
          enableEndTime: '',
          code: '',
          memberLevel: '',
          publish: ''
        },
        dataRule: {
          couponType: [
            { required: true, message: 'coupontype [0->store wide bonus, 1->member gift, 2->shopping bonus, 3->register gift] can not be empty', trigger: 'blur' }
          ],
          couponImg: [
            { required: true, message: 'coupon image can not be empty', trigger: 'blur' }
          ],
          couponName: [
            { required: true, message: 'coupon name can not be empty', trigger: 'blur' }
          ],
          num: [
            { required: true, message: 'coupon quantity can not be empty', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: 'coupon amount can not be empty', trigger: 'blur' }
          ],
          perLimit: [
            { required: true, message: 'limitatiton per customer can not be empty', trigger: 'blur' }
          ],
          minPoint: [
            { required: true, message: 'usage threshold can not be empty', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: 'coupon start time can not be empty', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: 'coupon end time can not be empty', trigger: 'blur' }
          ],
          useType: [
            { required: true, message: 'usage type [0->store wide, 1->specific catory, 2->specific item] can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'coupon note can not be empty', trigger: 'blur' }
          ],
          publishCount: [
            { required: true, message: 'publish quantity can not be empty', trigger: 'blur' }
          ],
          useCount: [
            { required: true, message: 'used quantity can not be empty', trigger: 'blur' }
          ],
          receiveCount: [
            { required: true, message: 'collected quantity can not be empty', trigger: 'blur' }
          ],
          enableStartTime: [
            { required: true, message: 'collection period start time can not be empty', trigger: 'blur' }
          ],
          enableEndTime: [
            { required: true, message: 'collection period end time can not be empty', trigger: 'blur' }
          ],
          code: [
            { required: true, message: 'promo code can not be empty', trigger: 'blur' }
          ],
          memberLevel: [
            { required: true, message: 'member level threshold [0->no limitatiton, other->corresponding member level] can not be empty', trigger: 'blur' }
          ],
          publish: [
            { required: true, message: 'publish status [0->unpublished, 1->published] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/coupon/coupon/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.couponType = data.coupon.couponType
                this.dataForm.couponImg = data.coupon.couponImg
                this.dataForm.couponName = data.coupon.couponName
                this.dataForm.num = data.coupon.num
                this.dataForm.amount = data.coupon.amount
                this.dataForm.perLimit = data.coupon.perLimit
                this.dataForm.minPoint = data.coupon.minPoint
                this.dataForm.startTime = data.coupon.startTime
                this.dataForm.endTime = data.coupon.endTime
                this.dataForm.useType = data.coupon.useType
                this.dataForm.note = data.coupon.note
                this.dataForm.publishCount = data.coupon.publishCount
                this.dataForm.useCount = data.coupon.useCount
                this.dataForm.receiveCount = data.coupon.receiveCount
                this.dataForm.enableStartTime = data.coupon.enableStartTime
                this.dataForm.enableEndTime = data.coupon.enableEndTime
                this.dataForm.code = data.coupon.code
                this.dataForm.memberLevel = data.coupon.memberLevel
                this.dataForm.publish = data.coupon.publish
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
              url: this.$http.adornUrl(`/coupon/coupon/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'couponType': this.dataForm.couponType,
                'couponImg': this.dataForm.couponImg,
                'couponName': this.dataForm.couponName,
                'num': this.dataForm.num,
                'amount': this.dataForm.amount,
                'perLimit': this.dataForm.perLimit,
                'minPoint': this.dataForm.minPoint,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'useType': this.dataForm.useType,
                'note': this.dataForm.note,
                'publishCount': this.dataForm.publishCount,
                'useCount': this.dataForm.useCount,
                'receiveCount': this.dataForm.receiveCount,
                'enableStartTime': this.dataForm.enableStartTime,
                'enableEndTime': this.dataForm.enableEndTime,
                'code': this.dataForm.code,
                'memberLevel': this.dataForm.memberLevel,
                'publish': this.dataForm.publish
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
