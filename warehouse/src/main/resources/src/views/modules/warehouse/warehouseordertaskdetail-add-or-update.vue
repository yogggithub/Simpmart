<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
    </el-form-item>
    <el-form-item label="sku name" prop="skuName">
      <el-input v-model="dataForm.skuName" placeholder="sku name"></el-input>
    </el-form-item>
    <el-form-item label="purchase quantity" prop="skuNum">
      <el-input v-model="dataForm.skuNum" placeholder="purchase quantity"></el-input>
    </el-form-item>
    <el-form-item label="task id" prop="taskId">
      <el-input v-model="dataForm.taskId" placeholder="task id"></el-input>
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
          skuId: '',
          skuName: '',
          skuNum: '',
          taskId: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'sku id can not be empty', trigger: 'blur' }
          ],
          skuName: [
            { required: true, message: 'sku name can not be empty', trigger: 'blur' }
          ],
          skuNum: [
            { required: true, message: 'purchase quantity can not be empty', trigger: 'blur' }
          ],
          taskId: [
            { required: true, message: 'task id can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/warehouse/warehouseordertaskdetail/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.warehouseOrderTaskDetail.skuId
                this.dataForm.skuName = data.warehouseOrderTaskDetail.skuName
                this.dataForm.skuNum = data.warehouseOrderTaskDetail.skuNum
                this.dataForm.taskId = data.warehouseOrderTaskDetail.taskId
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
              url: this.$http.adornUrl(`/warehouse/warehouseordertaskdetail/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'skuName': this.dataForm.skuName,
                'skuNum': this.dataForm.skuNum,
                'taskId': this.dataForm.taskId
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
