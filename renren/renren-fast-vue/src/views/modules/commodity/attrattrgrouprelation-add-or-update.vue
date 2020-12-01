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
            <el-form-item label="attribute id" prop="attrId">
                <el-input placeholder="attribute id" v-model="dataForm.attrId"></el-input>
            </el-form-item>
            <el-form-item label="attribute grouping id" prop="attrGroupId">
                <el-input placeholder="attribute grouping id" v-model="dataForm.attrGroupId"></el-input>
            </el-form-item>
            <el-form-item label="attribute sequence" prop="attrSort">
                <el-input placeholder="attribute sequence" v-model="dataForm.attrSort"></el-input>
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
                    attrId: '',
                    attrGroupId: '',
                    attrSort: ''
                },
                dataRule: {
                    attrId: [
                        { required: true, message: 'attribute id can not be empty', trigger: 'blur' }
                    ],
                    attrGroupId: [
                        { required: true, message: 'attribute grouping id can not be empty', trigger: 'blur' }
                    ],
                    attrSort: [
                        { required: true, message: 'attribute sequence can not be empty', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/commodity/attrattrgrouprelation/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.attrId = data.attrAttrgroupRelation.attrId
                                this.dataForm.attrGroupId = data.attrAttrgroupRelation.attrGroupId
                                this.dataForm.attrSort = data.attrAttrgroupRelation.attrSort
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
                            url: this.$http.adornUrl(`/commodity/attrattrgrouprelation/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'attrId': this.dataForm.attrId,
                                'attrGroupId': this.dataForm.attrGroupId,
                                'attrSort': this.dataForm.attrSort
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
