<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? '新增' : '修改'"
        :visible.sync="visible"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            @keyup.enter.native="dataFormSubmit()"
            label-width="120px"
            ref="dataForm"
        >
            <el-form-item label="专题名字" prop="name">
                <el-input placeholder="专题名字" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="专题id" prop="subjectId">
                <el-input placeholder="专题id" v-model="dataForm.subjectId"></el-input>
            </el-form-item>
            <el-form-item label="spu_id" prop="spuId">
                <el-input placeholder="spu_id" v-model="dataForm.spuId"></el-input>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
                <el-input placeholder="排序" v-model="dataForm.sort"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">取消</el-button>
            <el-button @click="dataFormSubmit()" type="primary">确定</el-button>
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
                    subjectId: '',
                    spuId: '',
                    sort: ''
                },
                dataRule: {
                    name: [
                        { required: true, message: '专题名字不能为空', trigger: 'blur' }
                    ],
                    subjectId: [
                        { required: true, message: '专题id不能为空', trigger: 'blur' }
                    ],
                    spuId: [
                        { required: true, message: 'spu_id不能为空', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: '排序不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/coupon/homesubjectspu/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.homeSubjectSpu.name
                                this.dataForm.subjectId = data.homeSubjectSpu.subjectId
                                this.dataForm.spuId = data.homeSubjectSpu.spuId
                                this.dataForm.sort = data.homeSubjectSpu.sort
                            }
                        })
                    }
                })
            },
            // 表单提交
            dataFormSubmit () {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(`/coupon/homesubjectspu/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'name': this.dataForm.name,
                                'subjectId': this.dataForm.subjectId,
                                'spuId': this.dataForm.spuId,
                                'sort': this.dataForm.sort
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: '操作成功',
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
