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
            <el-form-item label="名字" prop="name">
                <el-input placeholder="名字" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="图片地址" prop="pic">
                <el-input placeholder="图片地址" v-model="dataForm.pic"></el-input>
            </el-form-item>
            <el-form-item label="开始时间" prop="startTime">
                <el-input placeholder="开始时间" v-model="dataForm.startTime"></el-input>
            </el-form-item>
            <el-form-item label="结束时间" prop="endTime">
                <el-input placeholder="结束时间" v-model="dataForm.endTime"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-input placeholder="状态" v-model="dataForm.status"></el-input>
            </el-form-item>
            <el-form-item label="点击数" prop="clickCount">
                <el-input placeholder="点击数" v-model="dataForm.clickCount"></el-input>
            </el-form-item>
            <el-form-item label="广告详情连接地址" prop="url">
                <el-input placeholder="广告详情连接地址" v-model="dataForm.url"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="note">
                <el-input placeholder="备注" v-model="dataForm.note"></el-input>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
                <el-input placeholder="排序" v-model="dataForm.sort"></el-input>
            </el-form-item>
            <el-form-item label="发布者" prop="publisherId">
                <el-input placeholder="发布者" v-model="dataForm.publisherId"></el-input>
            </el-form-item>
            <el-form-item label="审核者" prop="authId">
                <el-input placeholder="审核者" v-model="dataForm.authId"></el-input>
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
                    pic: '',
                    startTime: '',
                    endTime: '',
                    status: '',
                    clickCount: '',
                    url: '',
                    note: '',
                    sort: '',
                    publisherId: '',
                    authId: ''
                },
                dataRule: {
                    name: [
                        { required: true, message: '名字不能为空', trigger: 'blur' }
                    ],
                    pic: [
                        { required: true, message: '图片地址不能为空', trigger: 'blur' }
                    ],
                    startTime: [
                        { required: true, message: '开始时间不能为空', trigger: 'blur' }
                    ],
                    endTime: [
                        { required: true, message: '结束时间不能为空', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '状态不能为空', trigger: 'blur' }
                    ],
                    clickCount: [
                        { required: true, message: '点击数不能为空', trigger: 'blur' }
                    ],
                    url: [
                        { required: true, message: '广告详情连接地址不能为空', trigger: 'blur' }
                    ],
                    note: [
                        { required: true, message: '备注不能为空', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: '排序不能为空', trigger: 'blur' }
                    ],
                    publisherId: [
                        { required: true, message: '发布者不能为空', trigger: 'blur' }
                    ],
                    authId: [
                        { required: true, message: '审核者不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/coupon/homeadv/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.homeAdv.name
                                this.dataForm.pic = data.homeAdv.pic
                                this.dataForm.startTime = data.homeAdv.startTime
                                this.dataForm.endTime = data.homeAdv.endTime
                                this.dataForm.status = data.homeAdv.status
                                this.dataForm.clickCount = data.homeAdv.clickCount
                                this.dataForm.url = data.homeAdv.url
                                this.dataForm.note = data.homeAdv.note
                                this.dataForm.sort = data.homeAdv.sort
                                this.dataForm.publisherId = data.homeAdv.publisherId
                                this.dataForm.authId = data.homeAdv.authId
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
                            url: this.$http.adornUrl(`/coupon/homeadv/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'name': this.dataForm.name,
                                'pic': this.dataForm.pic,
                                'startTime': this.dataForm.startTime,
                                'endTime': this.dataForm.endTime,
                                'status': this.dataForm.status,
                                'clickCount': this.dataForm.clickCount,
                                'url': this.dataForm.url,
                                'note': this.dataForm.note,
                                'sort': this.dataForm.sort,
                                'publisherId': this.dataForm.publisherId,
                                'authId': this.dataForm.authId
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
