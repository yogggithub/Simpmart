<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="order no." prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="order no."></el-input>
    </el-form-item>
    <el-form-item label="coupon used" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="coupon used"></el-input>
    </el-form-item>
    <el-form-item label="order create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="order create time"></el-input>
    </el-form-item>
    <el-form-item label="member username" prop="memberUsername">
      <el-input v-model="dataForm.memberUsername" placeholder="member username"></el-input>
    </el-form-item>
    <el-form-item label="item(s) subtotal amount" prop="totalAmount">
      <el-input v-model="dataForm.totalAmount" placeholder="item(s) subtotal amount"></el-input>
    </el-form-item>
    <el-form-item label="grand total amount" prop="payAmount">
      <el-input v-model="dataForm.payAmount" placeholder="grand total amount"></el-input>
    </el-form-item>
    <el-form-item label="shipping and handling costs" prop="freightAmount">
      <el-input v-model="dataForm.freightAmount" placeholder="shipping and handling costs"></el-input>
    </el-form-item>
    <el-form-item label="promotional Optimization Amount" prop="promotionAmount">
      <el-input v-model="dataForm.promotionAmount" placeholder="promotional Optimization Amount"></el-input>
    </el-form-item>
    <el-form-item label="membership rewards" prop="integrationAmount">
      <el-input v-model="dataForm.integrationAmount" placeholder="membership rewards"></el-input>
    </el-form-item>
    <el-form-item label="coupon credit" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="coupon credit"></el-input>
    </el-form-item>
    <el-form-item label="adjusted discounts of order" prop="discountAmount">
      <el-input v-model="dataForm.discountAmount" placeholder="adjusted discounts of order"></el-input>
    </el-form-item>
    <el-form-item label="payment method [1->credit card, 2->Paypal, 3->debit card, 4->cash on delivery]" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="payment method [1->credit card, 2->Paypal, 3->debit card, 4->cash on delivery]"></el-input>
    </el-form-item>
    <el-form-item label="order source[0->pc, 1->mobile]" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="order source[0->pc, 1->mobile]"></el-input>
    </el-form-item>
    <el-form-item label="order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]" prop="status">
      <el-input v-model="dataForm.status" placeholder="order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]"></el-input>
    </el-form-item>
    <el-form-item label="logistics company" prop="deliveryCompany">
      <el-input v-model="dataForm.deliveryCompany" placeholder="logistics company"></el-input>
    </el-form-item>
    <el-form-item label="shipment number" prop="deliverySn">
      <el-input v-model="dataForm.deliverySn" placeholder="shipment number"></el-input>
    </el-form-item>
    <el-form-item label="automatic confirmation time (days)" prop="autoConfirmDay">
      <el-input v-model="dataForm.autoConfirmDay" placeholder="automatic confirmation time (days)"></el-input>
    </el-form-item>
    <el-form-item label="member rewards to be earned" prop="integration">
      <el-input v-model="dataForm.integration" placeholder="member rewards to be earned"></el-input>
    </el-form-item>
    <el-form-item label="growth points to be earned" prop="growth">
      <el-input v-model="dataForm.growth" placeholder="growth points to be earned"></el-input>
    </el-form-item>
    <el-form-item label="invoice type[0->not required, 1->electronic, 2->printed]" prop="billType">
      <el-input v-model="dataForm.billType" placeholder="invoice type[0->not required, 1->electronic, 2->printed]"></el-input>
    </el-form-item>
    <el-form-item label="invoice recipient" prop="billHeader">
      <el-input v-model="dataForm.billHeader" placeholder="invoice recipient"></el-input>
    </el-form-item>
    <el-form-item label="contents of invoice" prop="billContent">
      <el-input v-model="dataForm.billContent" placeholder="contents of invoice"></el-input>
    </el-form-item>
    <el-form-item label="recipient phone number" prop="billReceiverPhone">
      <el-input v-model="dataForm.billReceiverPhone" placeholder="recipient phone number"></el-input>
    </el-form-item>
    <el-form-item label="recipient email" prop="billReceiverEmail">
      <el-input v-model="dataForm.billReceiverEmail" placeholder="recipient email"></el-input>
    </el-form-item>
    <el-form-item label="consignee name" prop="receiverName">
      <el-input v-model="dataForm.receiverName" placeholder="consignee name"></el-input>
    </el-form-item>
    <el-form-item label="consignee phone number" prop="receiverPhone">
      <el-input v-model="dataForm.receiverPhone" placeholder="consignee phone number"></el-input>
    </el-form-item>
    <el-form-item label="consignee postcode" prop="receiverPostCode">
      <el-input v-model="dataForm.receiverPostCode" placeholder="consignee postcode"></el-input>
    </el-form-item>
    <el-form-item label="state/province/region/territory" prop="receiverProvince">
      <el-input v-model="dataForm.receiverProvince" placeholder="state/province/region/territory"></el-input>
    </el-form-item>
    <el-form-item label="city" prop="receiverCity">
      <el-input v-model="dataForm.receiverCity" placeholder="city"></el-input>
    </el-form-item>
    <el-form-item label="address line 1 (street address/P.O. box)" prop="receiverRegion">
      <el-input v-model="dataForm.receiverRegion" placeholder="address line 1 (street address/P.O. box)"></el-input>
    </el-form-item>
    <el-form-item label="address line 2 (appartment/floor/suite/unit)" prop="receiverDetailAddress">
      <el-input v-model="dataForm.receiverDetailAddress" placeholder="address line 2 (appartment/floor/suite/unit)"></el-input>
    </el-form-item>
    <el-form-item label="order comments" prop="note">
      <el-input v-model="dataForm.note" placeholder="order comments"></el-input>
    </el-form-item>
    <el-form-item label="confirm status[0->unconfirmed, 1->confirmed]" prop="confirmStatus">
      <el-input v-model="dataForm.confirmStatus" placeholder="confirm status[0->unconfirmed, 1->confirmed]"></el-input>
    </el-form-item>
    <el-form-item label="delete status[0->non-deleted, 1->deleted]" prop="deleteStatus">
      <el-input v-model="dataForm.deleteStatus" placeholder="delete status[0->non-deleted, 1->deleted]"></el-input>
    </el-form-item>
    <el-form-item label="member rewards used when place order" prop="useIntegration">
      <el-input v-model="dataForm.useIntegration" placeholder="member rewards used when place order"></el-input>
    </el-form-item>
    <el-form-item label="payment time" prop="paymentTime">
      <el-input v-model="dataForm.paymentTime" placeholder="payment time"></el-input>
    </el-form-item>
    <el-form-item label="shipping time" prop="deliveryTime">
      <el-input v-model="dataForm.deliveryTime" placeholder="shipping time"></el-input>
    </el-form-item>
    <el-form-item label="shipped confirmation time" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="shipped confirmation time"></el-input>
    </el-form-item>
    <el-form-item label="review time" prop="commentTime">
      <el-input v-model="dataForm.commentTime" placeholder="review time"></el-input>
    </el-form-item>
    <el-form-item label="review modify time" prop="modifyTime">
      <el-input v-model="dataForm.modifyTime" placeholder="review modify time"></el-input>
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
          orderSn: '',
          couponId: '',
          createTime: '',
          memberUsername: '',
          totalAmount: '',
          payAmount: '',
          freightAmount: '',
          promotionAmount: '',
          integrationAmount: '',
          couponAmount: '',
          discountAmount: '',
          payType: '',
          sourceType: '',
          status: '',
          deliveryCompany: '',
          deliverySn: '',
          autoConfirmDay: '',
          integration: '',
          growth: '',
          billType: '',
          billHeader: '',
          billContent: '',
          billReceiverPhone: '',
          billReceiverEmail: '',
          receiverName: '',
          receiverPhone: '',
          receiverPostCode: '',
          receiverProvince: '',
          receiverCity: '',
          receiverRegion: '',
          receiverDetailAddress: '',
          note: '',
          confirmStatus: '',
          deleteStatus: '',
          useIntegration: '',
          paymentTime: '',
          deliveryTime: '',
          receiveTime: '',
          commentTime: '',
          modifyTime: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: 'order no. can not be empty', trigger: 'blur' }
          ],
          couponId: [
            { required: true, message: 'coupon used can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'order create time can not be empty', trigger: 'blur' }
          ],
          memberUsername: [
            { required: true, message: 'member username can not be empty', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: 'item(s) subtotal amount can not be empty', trigger: 'blur' }
          ],
          payAmount: [
            { required: true, message: 'grand total amount can not be empty', trigger: 'blur' }
          ],
          freightAmount: [
            { required: true, message: 'shipping and handling costs can not be empty', trigger: 'blur' }
          ],
          promotionAmount: [
            { required: true, message: 'promotional Optimization Amount can not be empty', trigger: 'blur' }
          ],
          integrationAmount: [
            { required: true, message: 'membership rewards can not be empty', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: 'coupon credit can not be empty', trigger: 'blur' }
          ],
          discountAmount: [
            { required: true, message: 'adjusted discounts of order can not be empty', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: 'payment method [1->credit card, 2->Paypal, 3->debit card, 4->cash on delivery] can not be empty', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: 'order source[0->pc, 1->mobile] can not be empty', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid] can not be empty', trigger: 'blur' }
          ],
          deliveryCompany: [
            { required: true, message: 'logistics company can not be empty', trigger: 'blur' }
          ],
          deliverySn: [
            { required: true, message: 'shipment number can not be empty', trigger: 'blur' }
          ],
          autoConfirmDay: [
            { required: true, message: 'automatic confirmation time (days) can not be empty', trigger: 'blur' }
          ],
          integration: [
            { required: true, message: 'member rewards to be earned can not be empty', trigger: 'blur' }
          ],
          growth: [
            { required: true, message: 'growth points to be earned can not be empty', trigger: 'blur' }
          ],
          billType: [
            { required: true, message: 'invoice type[0->not required, 1->electronic, 2->printed] can not be empty', trigger: 'blur' }
          ],
          billHeader: [
            { required: true, message: 'invoice recipient can not be empty', trigger: 'blur' }
          ],
          billContent: [
            { required: true, message: 'contents of invoice can not be empty', trigger: 'blur' }
          ],
          billReceiverPhone: [
            { required: true, message: 'recipient phone number can not be empty', trigger: 'blur' }
          ],
          billReceiverEmail: [
            { required: true, message: 'recipient email can not be empty', trigger: 'blur' }
          ],
          receiverName: [
            { required: true, message: 'consignee name can not be empty', trigger: 'blur' }
          ],
          receiverPhone: [
            { required: true, message: 'consignee phone number can not be empty', trigger: 'blur' }
          ],
          receiverPostCode: [
            { required: true, message: 'consignee postcode can not be empty', trigger: 'blur' }
          ],
          receiverProvince: [
            { required: true, message: 'state/province/region/territory can not be empty', trigger: 'blur' }
          ],
          receiverCity: [
            { required: true, message: 'city can not be empty', trigger: 'blur' }
          ],
          receiverRegion: [
            { required: true, message: 'address line 1 (street address/P.O. box) can not be empty', trigger: 'blur' }
          ],
          receiverDetailAddress: [
            { required: true, message: 'address line 2 (appartment/floor/suite/unit) can not be empty', trigger: 'blur' }
          ],
          note: [
            { required: true, message: 'order comments can not be empty', trigger: 'blur' }
          ],
          confirmStatus: [
            { required: true, message: 'confirm status[0->unconfirmed, 1->confirmed] can not be empty', trigger: 'blur' }
          ],
          deleteStatus: [
            { required: true, message: 'delete status[0->non-deleted, 1->deleted] can not be empty', trigger: 'blur' }
          ],
          useIntegration: [
            { required: true, message: 'member rewards used when place order can not be empty', trigger: 'blur' }
          ],
          paymentTime: [
            { required: true, message: 'payment time can not be empty', trigger: 'blur' }
          ],
          deliveryTime: [
            { required: true, message: 'shipping time can not be empty', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: 'shipped confirmation time can not be empty', trigger: 'blur' }
          ],
          commentTime: [
            { required: true, message: 'review time can not be empty', trigger: 'blur' }
          ],
          modifyTime: [
            { required: true, message: 'review modify time can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/order/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.order.memberId
                this.dataForm.orderSn = data.order.orderSn
                this.dataForm.couponId = data.order.couponId
                this.dataForm.createTime = data.order.createTime
                this.dataForm.memberUsername = data.order.memberUsername
                this.dataForm.totalAmount = data.order.totalAmount
                this.dataForm.payAmount = data.order.payAmount
                this.dataForm.freightAmount = data.order.freightAmount
                this.dataForm.promotionAmount = data.order.promotionAmount
                this.dataForm.integrationAmount = data.order.integrationAmount
                this.dataForm.couponAmount = data.order.couponAmount
                this.dataForm.discountAmount = data.order.discountAmount
                this.dataForm.payType = data.order.payType
                this.dataForm.sourceType = data.order.sourceType
                this.dataForm.status = data.order.status
                this.dataForm.deliveryCompany = data.order.deliveryCompany
                this.dataForm.deliverySn = data.order.deliverySn
                this.dataForm.autoConfirmDay = data.order.autoConfirmDay
                this.dataForm.integration = data.order.integration
                this.dataForm.growth = data.order.growth
                this.dataForm.billType = data.order.billType
                this.dataForm.billHeader = data.order.billHeader
                this.dataForm.billContent = data.order.billContent
                this.dataForm.billReceiverPhone = data.order.billReceiverPhone
                this.dataForm.billReceiverEmail = data.order.billReceiverEmail
                this.dataForm.receiverName = data.order.receiverName
                this.dataForm.receiverPhone = data.order.receiverPhone
                this.dataForm.receiverPostCode = data.order.receiverPostCode
                this.dataForm.receiverProvince = data.order.receiverProvince
                this.dataForm.receiverCity = data.order.receiverCity
                this.dataForm.receiverRegion = data.order.receiverRegion
                this.dataForm.receiverDetailAddress = data.order.receiverDetailAddress
                this.dataForm.note = data.order.note
                this.dataForm.confirmStatus = data.order.confirmStatus
                this.dataForm.deleteStatus = data.order.deleteStatus
                this.dataForm.useIntegration = data.order.useIntegration
                this.dataForm.paymentTime = data.order.paymentTime
                this.dataForm.deliveryTime = data.order.deliveryTime
                this.dataForm.receiveTime = data.order.receiveTime
                this.dataForm.commentTime = data.order.commentTime
                this.dataForm.modifyTime = data.order.modifyTime
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
              url: this.$http.adornUrl(`/order/order/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'orderSn': this.dataForm.orderSn,
                'couponId': this.dataForm.couponId,
                'createTime': this.dataForm.createTime,
                'memberUsername': this.dataForm.memberUsername,
                'totalAmount': this.dataForm.totalAmount,
                'payAmount': this.dataForm.payAmount,
                'freightAmount': this.dataForm.freightAmount,
                'promotionAmount': this.dataForm.promotionAmount,
                'integrationAmount': this.dataForm.integrationAmount,
                'couponAmount': this.dataForm.couponAmount,
                'discountAmount': this.dataForm.discountAmount,
                'payType': this.dataForm.payType,
                'sourceType': this.dataForm.sourceType,
                'status': this.dataForm.status,
                'deliveryCompany': this.dataForm.deliveryCompany,
                'deliverySn': this.dataForm.deliverySn,
                'autoConfirmDay': this.dataForm.autoConfirmDay,
                'integration': this.dataForm.integration,
                'growth': this.dataForm.growth,
                'billType': this.dataForm.billType,
                'billHeader': this.dataForm.billHeader,
                'billContent': this.dataForm.billContent,
                'billReceiverPhone': this.dataForm.billReceiverPhone,
                'billReceiverEmail': this.dataForm.billReceiverEmail,
                'receiverName': this.dataForm.receiverName,
                'receiverPhone': this.dataForm.receiverPhone,
                'receiverPostCode': this.dataForm.receiverPostCode,
                'receiverProvince': this.dataForm.receiverProvince,
                'receiverCity': this.dataForm.receiverCity,
                'receiverRegion': this.dataForm.receiverRegion,
                'receiverDetailAddress': this.dataForm.receiverDetailAddress,
                'note': this.dataForm.note,
                'confirmStatus': this.dataForm.confirmStatus,
                'deleteStatus': this.dataForm.deleteStatus,
                'useIntegration': this.dataForm.useIntegration,
                'paymentTime': this.dataForm.paymentTime,
                'deliveryTime': this.dataForm.deliveryTime,
                'receiveTime': this.dataForm.receiveTime,
                'commentTime': this.dataForm.commentTime,
                'modifyTime': this.dataForm.modifyTime
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
