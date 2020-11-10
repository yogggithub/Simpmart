<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Update'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="sku id" prop="skuId">
      <el-input v-model="dataForm.skuId" placeholder="sku id"></el-input>
    </el-form-item>
    <el-form-item label="spu id" prop="spuId">
      <el-input v-model="dataForm.spuId" placeholder="spu id"></el-input>
    </el-form-item>
    <el-form-item label="product name" prop="spuName">
      <el-input v-model="dataForm.spuName" placeholder="product name"></el-input>
    </el-form-item>
    <el-form-item label="member nick name" prop="memberNickName">
      <el-input v-model="dataForm.memberNickName" placeholder="member nick name"></el-input>
    </el-form-item>
    <el-form-item label="review rating" prop="star">
      <el-input v-model="dataForm.star" placeholder="review rating"></el-input>
    </el-form-item>
    <el-form-item label="member ip address" prop="memberIp">
      <el-input v-model="dataForm.memberIp" placeholder="member ip address"></el-input>
    </el-form-item>
    <el-form-item label="review create time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="review create time"></el-input>
    </el-form-item>
    <el-form-item label="review showing status [0->no, 1->yes]" prop="showStatus">
      <el-input v-model="dataForm.showStatus" placeholder="review showing status [0->no, 1->yes]"></el-input>
    </el-form-item>
    <el-form-item label="sku attributes combination when purchased" prop="spuAttributes">
      <el-input v-model="dataForm.spuAttributes" placeholder="sku attributes combination when purchased"></el-input>
    </el-form-item>
    <el-form-item label="likes of the review" prop="likesCount">
      <el-input v-model="dataForm.likesCount" placeholder="likes of the review"></el-input>
    </el-form-item>
    <el-form-item label="replies of the review" prop="replyCount">
      <el-input v-model="dataForm.replyCount" placeholder="replies of the review"></el-input>
    </el-form-item>
    <el-form-item label="comment with image or video [JSON, [{type:file type, url:file address}]]" prop="resources">
      <el-input v-model="dataForm.resources" placeholder="comment with image or video [JSON, [{type:file type, url:file address}]]"></el-input>
    </el-form-item>
    <el-form-item label="review content" prop="content">
      <el-input v-model="dataForm.content" placeholder="review content"></el-input>
    </el-form-item>
    <el-form-item label="profile picture" prop="memberIcon">
      <el-input v-model="dataForm.memberIcon" placeholder="profile picture"></el-input>
    </el-form-item>
    <el-form-item label="comment type [0->review to the product, 1->reply to review]" prop="commentType">
      <el-input v-model="dataForm.commentType" placeholder="comment type [0->review to the product, 1->reply to review]"></el-input>
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
          spuId: '',
          spuName: '',
          memberNickName: '',
          star: '',
          memberIp: '',
          createTime: '',
          showStatus: '',
          spuAttributes: '',
          likesCount: '',
          replyCount: '',
          resources: '',
          content: '',
          memberIcon: '',
          commentType: ''
        },
        dataRule: {
          skuId: [
            { required: true, message: 'sku id can not be empty', trigger: 'blur' }
          ],
          spuId: [
            { required: true, message: 'spu id can not be empty', trigger: 'blur' }
          ],
          spuName: [
            { required: true, message: 'product name can not be empty', trigger: 'blur' }
          ],
          memberNickName: [
            { required: true, message: 'member nick name can not be empty', trigger: 'blur' }
          ],
          star: [
            { required: true, message: 'review rating can not be empty', trigger: 'blur' }
          ],
          memberIp: [
            { required: true, message: 'member ip address can not be empty', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'review create time can not be empty', trigger: 'blur' }
          ],
          showStatus: [
            { required: true, message: 'review showing status [0->no, 1->yes] can not be empty', trigger: 'blur' }
          ],
          spuAttributes: [
            { required: true, message: 'sku attributes combination when purchased can not be empty', trigger: 'blur' }
          ],
          likesCount: [
            { required: true, message: 'likes of the review can not be empty', trigger: 'blur' }
          ],
          replyCount: [
            { required: true, message: 'replies of the review can not be empty', trigger: 'blur' }
          ],
          resources: [
            { required: true, message: 'comment with image or video [JSON, [{type:file type, url:file address}]] can not be empty', trigger: 'blur' }
          ],
          content: [
            { required: true, message: 'review content can not be empty', trigger: 'blur' }
          ],
          memberIcon: [
            { required: true, message: 'profile picture can not be empty', trigger: 'blur' }
          ],
          commentType: [
            { required: true, message: 'comment type [0->review to the product, 1->reply to review] can not be empty', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/commodity/spucomment/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.skuId = data.spuComment.skuId
                this.dataForm.spuId = data.spuComment.spuId
                this.dataForm.spuName = data.spuComment.spuName
                this.dataForm.memberNickName = data.spuComment.memberNickName
                this.dataForm.star = data.spuComment.star
                this.dataForm.memberIp = data.spuComment.memberIp
                this.dataForm.createTime = data.spuComment.createTime
                this.dataForm.showStatus = data.spuComment.showStatus
                this.dataForm.spuAttributes = data.spuComment.spuAttributes
                this.dataForm.likesCount = data.spuComment.likesCount
                this.dataForm.replyCount = data.spuComment.replyCount
                this.dataForm.resources = data.spuComment.resources
                this.dataForm.content = data.spuComment.content
                this.dataForm.memberIcon = data.spuComment.memberIcon
                this.dataForm.commentType = data.spuComment.commentType
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
              url: this.$http.adornUrl(`/commodity/spucomment/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'skuId': this.dataForm.skuId,
                'spuId': this.dataForm.spuId,
                'spuName': this.dataForm.spuName,
                'memberNickName': this.dataForm.memberNickName,
                'star': this.dataForm.star,
                'memberIp': this.dataForm.memberIp,
                'createTime': this.dataForm.createTime,
                'showStatus': this.dataForm.showStatus,
                'spuAttributes': this.dataForm.spuAttributes,
                'likesCount': this.dataForm.likesCount,
                'replyCount': this.dataForm.replyCount,
                'resources': this.dataForm.resources,
                'content': this.dataForm.content,
                'memberIcon': this.dataForm.memberIcon,
                'commentType': this.dataForm.commentType
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
