<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? 'Add' : 'Update'"
        :visible.sync="visible"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            @keyup.enter.native="dataFormSubmit()"
            label-width="80px"
            ref="dataForm"
        >
            <el-form-item label="spu id" prop="spuId">
                <el-input placeholder="spu id" v-model="dataForm.spuId"></el-input>
            </el-form-item>
            <el-form-item label="image name" prop="imgName">
                <el-input placeholder="image name" v-model="dataForm.imgName"></el-input>
            </el-form-item>
            <el-form-item label="image link" prop="imgUrl">
                <el-input placeholder="image link" v-model="dataForm.imgUrl"></el-input>
            </el-form-item>
            <el-form-item label="image sequence" prop="imgSort">
                <el-input placeholder="image sequence" v-model="dataForm.imgSort"></el-input>
            </el-form-item>
            <el-form-item label="is default image [0->no, 1->yes]" prop="defaultImg">
                <el-input
                    placeholder="is default image [0->no, 1->yes]"
                    v-model="dataForm.defaultImg"
                ></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">Cancel</el-button>
            <el-button @click="dataFormSubmit()" type="primary">Confirm</el-button>
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
                    spuId: '',
                    imgName: '',
                    imgUrl: '',
                    imgSort: '',
                    defaultImg: ''
                },
                dataRule: {
                    spuId: [
                        { required: true, message: 'spu id can not be empty', trigger: 'blur' }
                    ],
                    imgName: [
                        { required: true, message: 'image name can not be empty', trigger: 'blur' }
                    ],
                    imgUrl: [
                        { required: true, message: 'image link can not be empty', trigger: 'blur' }
                    ],
                    imgSort: [
                        { required: true, message: 'image sequence can not be empty', trigger: 'blur' }
                    ],
                    defaultImg: [
                        { required: true, message: 'is default image [0->no, 1->yes] can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/commodity/spuimages/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.spuId = data.spuImages.spuId
                                this.dataForm.imgName = data.spuImages.imgName
                                this.dataForm.imgUrl = data.spuImages.imgUrl
                                this.dataForm.imgSort = data.spuImages.imgSort
                                this.dataForm.defaultImg = data.spuImages.defaultImg
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
                            url: this.$http.adornUrl(`/commodity/spuimages/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'spuId': this.dataForm.spuId,
                                'imgName': this.dataForm.imgName,
                                'imgUrl': this.dataForm.imgUrl,
                                'imgSort': this.dataForm.imgSort,
                                'defaultImg': this.dataForm.defaultImg
                            })
                        }).then(({ data }) => {
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
