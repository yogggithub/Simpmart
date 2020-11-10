<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="return order no." prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="return order no."></el-input>
    </el-form-item>
    <el-form-item label="return item sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="return item sku id"></el-input>
    </el-form-item>
    <el-form-item label="original order no." prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="original order no."></el-input>
    </el-form-item>
    <el-form-item label="apply time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="apply time"></el-input>
    </el-form-item>
    <el-form-item label="member username" prop="memberUsername">
      <el-input v-model="dataForm.memberUsername" placeholder="member username"></el-input>
    </el-form-item>
    <el-form-item label="refund amount" prop="returnAmount">
      <el-input v-model="dataForm.returnAmount" placeholder="refund amount"></el-input>
    </el-form-item>
    <el-form-item label="return applicant name" prop="returnName">
      <el-input v-model="dataForm.returnName" placeholder="return applicant name"></el-input>
    </el-form-item>
    <el-form-item label="return applicant phone number" prop="returnPhone">
      <el-input v-model="dataForm.returnPhone" placeholder="return applicant phone number"></el-input>
    </el-form-item>
    <el-form-item label="application status[0->awaiting treatment, 1->Returning, 2->completed, 3->refused]" prop="status">
      <el-input v-model="dataForm.status" placeholder="application status[0->awaiting treatment, 1->Returning, 2->completed, 3->refused]"></el-input>
    </el-form-item>
    <el-form-item label="application handle time" prop="handleTime">
      <el-input v-model="dataForm.handleTime" placeholder="application handle time"></el-input>
    </el-form-item>
    <el-form-item label="sku picture" prop="skuImg">
      <el-input v-model="dataForm.skuImg" placeholder="sku picture"></el-input>
    </el-form-item>
    <el-form-item label="sku name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku name"></el-input>
    </el-form-item>
    <el-form-item label="sku brand" prop="skuBrand">
      <el-input v-model="dataForm.skuBrand" placeholder="sku brand"></el-input>
    </el-form-item>
    <el-form-item label="sku attributes combination (JSON)" prop="skuAttrsVals">
      <el-input v-model="dataForm.skuAttrsVals" placeholder="sku attributes combination (JSON)"></el-input>
    </el-form-item>
    <el-form-item label="return quantity" prop="skuCount">
      <el-input v-model="dataForm.skuCount" placeholder="return quantity"></el-input>
    </el-form-item>
    <el-form-item label="sku price" prop="skuPrice">
      <el-input v-model="dataForm.skuPrice" placeholder="sku price"></el-input>
    </el-form-item>
    <el-form-item label="grand total after deduction" prop="skuRealPrice">
      <el-input v-model="dataForm.skuRealPrice" placeholder="grand total after deduction"></el-input>
    </el-form-item>
    <el-form-item label="return reason" prop="reason">
      <el-input v-model="dataForm.reason" placeholder="return reason"></el-input>
    </el-form-item>
    <el-form-item label="application description" prop="description">
      <el-input v-model="dataForm.description" placeholder="application description"></el-input>
    </el-form-item>
    <el-form-item label="application picture" prop="descPics">
      <el-input v-model="dataForm.descPics" placeholder="application picture"></el-input>
    </el-form-item>
    <el-form-item label="handling comment" prop="handleNote">
      <el-input v-model="dataForm.handleNote" placeholder="handling comment"></el-input>
    </el-form-item>
    <el-form-item label="application handler" prop="handleMan">
      <el-input v-model="dataForm.handleMan" placeholder="application handler"></el-input>
    </el-form-item>
    <el-form-item label="consignee name" prop="receiveMan">
      <el-input v-model="dataForm.receiveMan" placeholder="consignee name"></el-input>
    </el-form-item>
    <el-form-item label="delivery time" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="delivery time"></el-input>
    </el-form-item>
    <el-form-item label="shipping comment" prop="receiveNote">
      <el-input v-model="dataForm.receiveNote" placeholder="shipping comment"></el-input>
    </el-form-item>
    <el-form-item label="consignee phone number" prop="receivePhone">
      <el-input v-model="dataForm.receivePhone" placeholder="consignee phone number"></el-input>
    </el-form-item>
    <el-form-item label="company delivery address" prop="companyAddress">
      <el-input v-model="dataForm.companyAddress" placeholder="company delivery address"></el-input>
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
          orderId: '',
          skuId: '',
          orderSn: '',
          createTime: '',
          memberUsername: '',
          returnAmount: '',
          returnName: '',
          returnPhone: '',
          status: '',
          handleTime: '',
          skuImg: '',
          skuName: '',
          skuBrand: '',
          skuAttrsVals: '',
          skuCount: '',
          skuPrice: '',
          skuRealPrice: '',
          reason: '',
          description: '',
          descPics: '',
          handleNote: '',
          handleMan: '',
          receiveMan: '',
          receiveTime: '',
          receiveNote: '',
          receivePhone: '',
          companyAddress: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: 'return order no. can not be empty', trigger: 'blur' }
          ],
          skuId: [
            { required: true, message: 'return item sku id can not be empty', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: 'original order no. can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'apply time can not be empty', trigger: 'blur' }
          ],
          memberUsername: [
            { required: true, message: 'member username can not be empty', trigger: 'blur' }
          ],
          returnAmount: [
            { required: true, message: 'refund amount can not be empty', trigger: 'blur' }
          ],
          returnName: [
            { required: true, message: 'return applicant name can not be empty', trigger: 'blur' }
          ],
          returnPhone: [
            { required: true, message: 'return applicant phone number can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'application status[0->awaiting treatment, 1->Returning, 2->completed, 3->refused] can not be empty', trigger: 'blur' }
          ],
          handleTime: [
            { required: true, message: 'application handle time can not be empty', trigger: 'blur' }
          ],
          skuImg: [
            { required: true, message: 'sku picture can not be empty', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku name can not be empty', trigger: 'blur' }
          ],
          skuBrand: [
            { required: true, message: 'sku brand can not be empty', trigger: 'blur' }
          ],
          skuAttrsVals: [
            { required: true, message: 'sku attributes combination (JSON) can not be empty', trigger: 'blur' }
          ],
          skuCount: [
            { required: true, message: 'return quantity can not be empty', trigger: 'blur' }
          ],
          skuPrice: [
            { required: true, message: 'sku price can not be empty', trigger: 'blur' }
          ],
          skuRealPrice: [
            { required: true, message: 'grand total after deduction can not be empty', trigger: 'blur' }
          ],
          reason: [
            { required: true, message: 'return reason can not be empty', trigger: 'blur' }
          ],
          description: [
            { required: true, message: 'application description can not be empty', trigger: 'blur' }
          ],
          descPics: [
            { required: true, message: 'application picture can not be empty', trigger: 'blur' }
          ],
          handleNote: [
            { required: true, message: 'handling comment can not be empty', trigger: 'blur' }
          ],
          handleMan: [
            { required: true, message: 'application handler can not be empty', trigger: 'blur' }
          ],
          receiveMan: [
            { required: true, message: 'consignee name can not be empty', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: 'delivery time can not be empty', trigger: 'blur' }
          ],
          receiveNote: [
            { required: true, message: 'shipping comment can not be empty', trigger: 'blur' }
          ],
          receivePhone: [
            { required: true, message: 'consignee phone number can not be empty', trigger: 'blur' }
          ],
          companyAddress: [
            { required: true, message: 'company delivery address can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/orderreturnapply/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.orderReturnApply.orderId
                this.dataForm.skuId = data.orderReturnApply.skuId
                this.dataForm.orderSn = data.orderReturnApply.orderSn
                this.dataForm.createTime = data.orderReturnApply.createTime
                this.dataForm.memberUsername = data.orderReturnApply.memberUsername
                this.dataForm.returnAmount = data.orderReturnApply.returnAmount
                this.dataForm.returnName = data.orderReturnApply.returnName
                this.dataForm.returnPhone = data.orderReturnApply.returnPhone
                this.dataForm.status = data.orderReturnApply.status
                this.dataForm.handleTime = data.orderReturnApply.handleTime
                this.dataForm.skuImg = data.orderReturnApply.skuImg
                this.dataForm.skuName = data.orderReturnApply.skuName
                this.dataForm.skuBrand = data.orderReturnApply.skuBrand
                this.dataForm.skuAttrsVals = data.orderReturnApply.skuAttrsVals
                this.dataForm.skuCount = data.orderReturnApply.skuCount
                this.dataForm.skuPrice = data.orderReturnApply.skuPrice
                this.dataForm.skuRealPrice = data.orderReturnApply.skuRealPrice
                this.dataForm.reason = data.orderReturnApply.reason
                this.dataForm.description = data.orderReturnApply.description
                this.dataForm.descPics = data.orderReturnApply.descPics
                this.dataForm.handleNote = data.orderReturnApply.handleNote
                this.dataForm.handleMan = data.orderReturnApply.handleMan
                this.dataForm.receiveMan = data.orderReturnApply.receiveMan
                this.dataForm.receiveTime = data.orderReturnApply.receiveTime
                this.dataForm.receiveNote = data.orderReturnApply.receiveNote
                this.dataForm.receivePhone = data.orderReturnApply.receivePhone
                this.dataForm.companyAddress = data.orderReturnApply.companyAddress
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
              url: this.$http.adornUrl(`/order/orderreturnapply/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'orderId': this.dataForm.orderId,
                'skuId': this.dataForm.skuId,
                'orderSn': this.dataForm.orderSn,
                'createTime': this.dataForm.createTime,
                'memberUsername': this.dataForm.memberUsername,
                'returnAmount': this.dataForm.returnAmount,
                'returnName': this.dataForm.returnName,
                'returnPhone': this.dataForm.returnPhone,
                'status': this.dataForm.status,
                'handleTime': this.dataForm.handleTime,
                'skuImg': this.dataForm.skuImg,
                'skuName': this.dataForm.skuName,
                'skuBrand': this.dataForm.skuBrand,
                'skuAttrsVals': this.dataForm.skuAttrsVals,
                'skuCount': this.dataForm.skuCount,
                'skuPrice': this.dataForm.skuPrice,
                'skuRealPrice': this.dataForm.skuRealPrice,
                'reason': this.dataForm.reason,
                'description': this.dataForm.description,
                'descPics': this.dataForm.descPics,
                'handleNote': this.dataForm.handleNote,
                'handleMan': this.dataForm.handleMan,
                'receiveMan': this.dataForm.receiveMan,
                'receiveTime': this.dataForm.receiveTime,
                'receiveNote': this.dataForm.receiveNote,
                'receivePhone': this.dataForm.receivePhone,
                'companyAddress': this.dataForm.companyAddress
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
