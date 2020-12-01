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
            <el-form-item label="会员等级id" prop="levelId">
                <el-input placeholder="会员等级id" v-model="dataForm.levelId"></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="username">
                <el-input placeholder="用户名" v-model="dataForm.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input placeholder="密码" v-model="dataForm.password"></el-input>
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
                <el-input placeholder="昵称" v-model="dataForm.nickname"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop="mobile">
                <el-input placeholder="手机号码" v-model="dataForm.mobile"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input placeholder="邮箱" v-model="dataForm.email"></el-input>
            </el-form-item>
            <el-form-item label="头像" prop="header">
                <el-input placeholder="头像" v-model="dataForm.header"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="gender">
                <el-input placeholder="性别" v-model="dataForm.gender"></el-input>
            </el-form-item>
            <el-form-item label="生日" prop="birth">
                <el-input placeholder="生日" v-model="dataForm.birth"></el-input>
            </el-form-item>
            <el-form-item label="所在城市" prop="city">
                <el-input placeholder="所在城市" v-model="dataForm.city"></el-input>
            </el-form-item>
            <el-form-item label="职业" prop="job">
                <el-input placeholder="职业" v-model="dataForm.job"></el-input>
            </el-form-item>
            <el-form-item label="个性签名" prop="sign">
                <el-input placeholder="个性签名" v-model="dataForm.sign"></el-input>
            </el-form-item>
            <el-form-item label="用户来源" prop="sourceType">
                <el-input placeholder="用户来源" v-model="dataForm.sourceType"></el-input>
            </el-form-item>
            <el-form-item label="积分" prop="integration">
                <el-input placeholder="积分" v-model="dataForm.integration"></el-input>
            </el-form-item>
            <el-form-item label="成长值" prop="growth">
                <el-input placeholder="成长值" v-model="dataForm.growth"></el-input>
            </el-form-item>
            <el-form-item label="启用状态" prop="status">
                <el-input placeholder="启用状态" v-model="dataForm.status"></el-input>
            </el-form-item>
            <el-form-item label="注册时间" prop="createTime">
                <el-input placeholder="注册时间" v-model="dataForm.createTime"></el-input>
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
                    levelId: '',
                    username: '',
                    password: '',
                    nickname: '',
                    mobile: '',
                    email: '',
                    header: '',
                    gender: '',
                    birth: '',
                    city: '',
                    job: '',
                    sign: '',
                    sourceType: '',
                    integration: '',
                    growth: '',
                    status: '',
                    createTime: ''
                },
                dataRule: {
                    levelId: [
                        { required: true, message: '会员等级id不能为空', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '用户名不能为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不能为空', trigger: 'blur' }
                    ],
                    nickname: [
                        { required: true, message: '昵称不能为空', trigger: 'blur' }
                    ],
                    mobile: [
                        { required: true, message: '手机号码不能为空', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '邮箱不能为空', trigger: 'blur' }
                    ],
                    header: [
                        { required: true, message: '头像不能为空', trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '性别不能为空', trigger: 'blur' }
                    ],
                    birth: [
                        { required: true, message: '生日不能为空', trigger: 'blur' }
                    ],
                    city: [
                        { required: true, message: '所在城市不能为空', trigger: 'blur' }
                    ],
                    job: [
                        { required: true, message: '职业不能为空', trigger: 'blur' }
                    ],
                    sign: [
                        { required: true, message: '个性签名不能为空', trigger: 'blur' }
                    ],
                    sourceType: [
                        { required: true, message: '用户来源不能为空', trigger: 'blur' }
                    ],
                    integration: [
                        { required: true, message: '积分不能为空', trigger: 'blur' }
                    ],
                    growth: [
                        { required: true, message: '成长值不能为空', trigger: 'blur' }
                    ],
                    status: [
                        { required: true, message: '启用状态不能为空', trigger: 'blur' }
                    ],
                    createTime: [
                        { required: true, message: '注册时间不能为空', trigger: 'blur' }
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
                            url: this.$http.adornUrl(`/member/member/info/${this.dataForm.id}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.levelId = data.member.levelId
                                this.dataForm.username = data.member.username
                                this.dataForm.password = data.member.password
                                this.dataForm.nickname = data.member.nickname
                                this.dataForm.mobile = data.member.mobile
                                this.dataForm.email = data.member.email
                                this.dataForm.header = data.member.header
                                this.dataForm.gender = data.member.gender
                                this.dataForm.birth = data.member.birth
                                this.dataForm.city = data.member.city
                                this.dataForm.job = data.member.job
                                this.dataForm.sign = data.member.sign
                                this.dataForm.sourceType = data.member.sourceType
                                this.dataForm.integration = data.member.integration
                                this.dataForm.growth = data.member.growth
                                this.dataForm.status = data.member.status
                                this.dataForm.createTime = data.member.createTime
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
                            url: this.$http.adornUrl(`/member/member/${!this.dataForm.id ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'id': this.dataForm.id || undefined,
                                'levelId': this.dataForm.levelId,
                                'username': this.dataForm.username,
                                'password': this.dataForm.password,
                                'nickname': this.dataForm.nickname,
                                'mobile': this.dataForm.mobile,
                                'email': this.dataForm.email,
                                'header': this.dataForm.header,
                                'gender': this.dataForm.gender,
                                'birth': this.dataForm.birth,
                                'city': this.dataForm.city,
                                'job': this.dataForm.job,
                                'sign': this.dataForm.sign,
                                'sourceType': this.dataForm.sourceType,
                                'integration': this.dataForm.integration,
                                'growth': this.dataForm.growth,
                                'status': this.dataForm.status,
                                'createTime': this.dataForm.createTime
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
