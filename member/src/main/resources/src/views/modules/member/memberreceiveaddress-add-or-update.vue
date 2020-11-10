<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="member id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member id"></el-input>
    </el-form-item>
    <el-form-item label="consignee name" prop="name">
      <el-input v-model="dataForm.name" placeholder="consignee name"></el-input>
    </el-form-item>
    <el-form-item label="consignee phone number" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="consignee phone number"></el-input>
    </el-form-item>
    <el-form-item label="postcode" prop="postCode">
      <el-input v-model="dataForm.postCode" placeholder="postcode"></el-input>
    </el-form-item>
    <el-form-item label="state/province/region/territory" prop="province">
      <el-input v-model="dataForm.province" placeholder="state/province/region/territory"></el-input>
    </el-form-item>
    <el-form-item label="city" prop="city">
      <el-input v-model="dataForm.city" placeholder="city"></el-input>
    </el-form-item>
    <el-form-item label="address line 1 (street address/P.O. box)" prop="region">
      <el-input v-model="dataForm.region" placeholder="address line 1 (street address/P.O. box)"></el-input>
    </el-form-item>
    <el-form-item label="address line 2 (appartment/floor/suite/unit)" prop="detailAddress">
      <el-input v-model="dataForm.detailAddress" placeholder="address line 2 (appartment/floor/suite/unit)"></el-input>
    </el-form-item>
    <el-form-item label="abbreviation of state/province/region/territory" prop="areacode">
      <el-input v-model="dataForm.areacode" placeholder="abbreviation of state/province/region/territory"></el-input>
    </el-form-item>
    <el-form-item label="default address [0 -> no, 1 -> yes]" prop="defaultStatus">
      <el-input v-model="dataForm.defaultStatus" placeholder="default address [0 -> no, 1 -> yes]"></el-input>
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
          name: '',
          phone: '',
          postCode: '',
          province: '',
          city: '',
          region: '',
          detailAddress: '',
          areacode: '',
          defaultStatus: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member id can not be empty', trigger: 'blur' }
          ],
          name: [
            { required: true, message: 'consignee name can not be empty', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: 'consignee phone number can not be empty', trigger: 'blur' }
          ],
          postCode: [
            { required: true, message: 'postcode can not be empty', trigger: 'blur' }
          ],
          province: [
            { required: true, message: 'state/province/region/territory can not be empty', trigger: 'blur' }
          ],
          city: [
            { required: true, message: 'city can not be empty', trigger: 'blur' }
          ],
          region: [
            { required: true, message: 'address line 1 (street address/P.O. box) can not be empty', trigger: 'blur' }
          ],
          detailAddress: [
            { required: true, message: 'address line 2 (appartment/floor/suite/unit) can not be empty', trigger: 'blur' }
          ],
          areacode: [
            { required: true, message: 'abbreviation of state/province/region/territory can not be empty', trigger: 'blur' }
          ],
          defaultStatus: [
            { required: true, message: 'default address [0 -> no, 1 -> yes] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/memberreceiveaddress/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.memberReceiveAddress.memberId
                this.dataForm.name = data.memberReceiveAddress.name
                this.dataForm.phone = data.memberReceiveAddress.phone
                this.dataForm.postCode = data.memberReceiveAddress.postCode
                this.dataForm.province = data.memberReceiveAddress.province
                this.dataForm.city = data.memberReceiveAddress.city
                this.dataForm.region = data.memberReceiveAddress.region
                this.dataForm.detailAddress = data.memberReceiveAddress.detailAddress
                this.dataForm.areacode = data.memberReceiveAddress.areacode
                this.dataForm.defaultStatus = data.memberReceiveAddress.defaultStatus
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
              url: this.$http.adornUrl(`/member/memberreceiveaddress/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'name': this.dataForm.name,
                'phone': this.dataForm.phone,
                'postCode': this.dataForm.postCode,
                'province': this.dataForm.province,
                'city': this.dataForm.city,
                'region': this.dataForm.region,
                'detailAddress': this.dataForm.detailAddress,
                'areacode': this.dataForm.areacode,
                'defaultStatus': this.dataForm.defaultStatus
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
