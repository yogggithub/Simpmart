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
            label-width="130px"
            ref="dataForm"
        >
            <el-form-item label="Brand Name" prop="name">
                <el-input placeholder="Brand Name" v-model="dataForm.name"></el-input>
            </el-form-item>
            <el-form-item label="Logo Link" prop="logo">
                <!-- <el-input placeholder="brand logo link" v-model="dataForm.logo"></el-input> -->
                <!-- Upload local icon image -->
                <singleUpload v-model="dataForm.logo"></singleUpload>
            </el-form-item>
            <el-form-item label="Description" prop="description">
                <el-input placeholder="brand description" v-model="dataForm.description"></el-input>
            </el-form-item>
            <el-form-item label="Showing Status" prop="showStatus">
                <el-switch
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    v-model="dataForm.showStatus"
                ></el-switch>
            </el-form-item>
            <el-form-item label="Initial Letter" prop="firstLetter">
                <el-input
                    placeholder="brand initial letter for searching"
                    v-model="dataForm.firstLetter"
                ></el-input>
            </el-form-item>
            <el-form-item label="Sequence" prop="sort">
                <!-- v-model.number: limit only input number -->
                <el-input placeholder="brand sequence" v-model.number="dataForm.sort"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">Cancel</el-button>
            <el-button @click="dataFormSubmit()" type="primary">Confirm</el-button>
        </span>
    </el-dialog>
</template>

<script>
    // import upload component
    import singleUpload from '@/components/upload/singleUpload.vue'

    export default {
        data () {
            return {
                visible: false,
                dataForm: {
                    brandId: 0,
                    name: '',
                    logo: '',
                    description: '',
                    showStatus: 1,
                    firstLetter: '',
                    sort: 0
                },
                dataRule: {
                    name: [
                        { required: true, message: 'brand name can not be empty', trigger: 'blur' }
                    ],
                    logo: [
                        { required: true, message: 'brand logo link can not be empty', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'brand description can not be empty', trigger: 'blur' }
                    ],
                    showStatus: [
                        { required: true, message: 'showing status [0->no, 1->yes] can not be empty', trigger: 'blur' }
                    ],
                    firstLetter: [
                        {
                            validator: (rule, value, callback) => {
                                if (value === '') {
                                    callback(new Error('initial letter can not be empty'))
                                } else if (!/^[a-zA-Z]$/.test(value)) {
                                    callback(new Error('initial letter must be one letter'))
                                } else {
                                    // pass the validation
                                    callback()
                                }
                            },
                            trigger: 'blur'
                        }
                    ],
                    sort: [
                        {
                            validator: (rule, value, callback) => {
                                if (value === '') {
                                    callback(new Error('brand sort can not be empty'))
                                } else if (!Number.isInteger(value) || value < 0) {
                                    callback(new Error('brand sort must be a postitive integer'))
                                } else {
                                    // pass the validation
                                    callback()
                                }
                            },
                            trigger: 'blur'
                        }
                    ]
                }
            }
        },
        methods: {
            init (id) {
                this.dataForm.brandId = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.brandId) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/brand/info/${this.dataForm.brandId}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.name = data.brand.name
                                this.dataForm.logo = data.brand.logo
                                this.dataForm.description = data.brand.description
                                this.dataForm.showStatus = data.brand.showStatus
                                this.dataForm.firstLetter = data.brand.firstLetter
                                this.dataForm.sort = data.brand.sort
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
                            url: this.$http.adornUrl(`/commodity/brand/${!this.dataForm.brandId ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'brandId': this.dataForm.brandId || undefined,
                                'name': this.dataForm.name,
                                'logo': this.dataForm.logo,
                                'description': this.dataForm.description,
                                'showStatus': this.dataForm.showStatus,
                                'firstLetter': this.dataForm.firstLetter,
                                'sort': this.dataForm.sort
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
        },
        components: {
            singleUpload
        }
    }
</script>
