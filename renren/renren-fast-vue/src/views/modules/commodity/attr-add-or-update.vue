<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? 'Add' : 'Edit'"
        :visible.sync="visible"
        @closed="dialogClose"
    >
        <el-form :model="dataForm" :rules="dataRule" label-width="130px" ref="dataForm">
            <el-form-item label="attribute name" prop="attrName">
                <el-input placeholder="attribute name" v-model="dataForm.attrName"></el-input>
            </el-form-item>
            <el-form-item label="attribute type" prop="attrType">
                <el-select placeholder="Select" v-model="dataForm.attrType">
                    <el-option :value="1" label="base attribute"></el-option>
                    <el-option :value="0" label="sale attribute"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="value type" prop="valueType">
                <el-switch
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    active-text="multiple value allowed"
                    inactive-color="#ff4949"
                    inactive-text="only single value"
                    v-model="dataForm.valueType"
                ></el-switch>
            </el-form-item>
            <el-form-item label="optional value" prop="valueSelect">
                <el-select
                    allow-create
                    filterable
                    multiple
                    placeholder="please iinput"
                    v-model="dataForm.valueSelect"
                ></el-select>
            </el-form-item>
            <el-form-item label="icon" prop="icon">
                <el-input placeholder="icon" v-model="dataForm.icon"></el-input>
            </el-form-item>
            <el-form-item label="cagetory" prop="catalogId">
                <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
            </el-form-item>
            <el-form-item label="group" prop="attrGroupId" v-if="type == 1">
                <el-select placeholder="Select" ref="groupSelect" v-model="dataForm.attrGroupId">
                    <el-option
                        :key="item.attrGroupId"
                        :label="item.attrGroupName"
                        :value="item.attrGroupId"
                        v-for="item in attrGroups"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="searchable" prop="searchType" v-if="type == 1">
                <el-switch
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    v-model="dataForm.searchType"
                ></el-switch>
            </el-form-item>
            <el-form-item label="show description" prop="showDesc" v-if="type == 1">
                <el-switch
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    v-model="dataForm.showDesc"
                ></el-switch>
            </el-form-item>
            <el-form-item label="enable" prop="enable">
                <el-switch
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    v-model="dataForm.enable"
                ></el-switch>
            </el-form-item>
        </el-form>
        <span class="dialog-footer" slot="footer">
            <el-button @click="visible = false">Cancel</el-button>
            <el-button @click="dataFormSubmit()" type="primary">Confirm</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import CategoryCascader from '../common/category-cascader'
    export default {
        data () {
            return {
                visible: false,
                dataForm: {
                    attrId: 0,
                    attrName: '',
                    searchType: 0,
                    valueType: 1,
                    icon: '',
                    valueSelect: '',
                    attrType: 1,
                    enable: 1,
                    catalogId: '',
                    attrGroupId: '',
                    showDesc: 0
                },
                catalogPath: [],
                attrGroups: [],
                dataRule: {
                    attrName: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    searchType: [
                        {
                            required: true,
                            message: 'Can not be empty',
                            trigger: 'blur'
                        }
                    ],
                    valueType: [
                        {
                            required: true,
                            message: 'Can not be empty',
                            trigger: 'blur'
                        }
                    ],
                    icon: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    attrType: [
                        {
                            required: true,
                            message: 'Can not be empty',
                            trigger: 'blur'
                        }
                    ],
                    enable: [
                        {
                            required: true,
                            message: 'Can not be empty',
                            trigger: 'blur'
                        }
                    ],
                    catalogId: [
                        {
                            required: true,
                            message: 'Choose the right category',
                            trigger: 'blur'
                        }
                    ],
                    showDesc: [
                        {
                            required: true,
                            message: 'Can not be empty',
                            trigger: 'blur'
                        }
                    ]
                }
            }
        },
        props: {
            type: {
                type: Number,
                default: 1
            }
        },
        watch: {
            catalogPath (path) {
                // Listen category changing event, and refresh data
                this.attrGroups = []
                this.dataForm.attrGroupId = ''
                this.dataForm.catalogId = path[path.length - 1]
                if (path && path.length === 3) {
                    this.$http({
                        url: this.$http.adornUrl(
                            `/commodity/attrgroup/list/${path[path.length - 1]}`
                        ),
                        method: 'get',
                        params: this.$http.adornParams({ page: 1, limit: 10000000 })
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.attrGroups = data.page.list
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                } else if (path.length === 0) {
                    this.dataForm.catalogId = ''
                } else {
                    this.$message.error('Choose the right category')
                    this.dataForm.catalogId = ''
                }
            }
        },
        components: { CategoryCascader },
        methods: {
            init (id) {
                this.dataForm.attrId = id || 0
                this.dataForm.attrType = this.type
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.attrId) {
                        this.$http({
                            url: this.$http.adornUrl(
                                `/commodity/attr/info/${this.dataForm.attrId}`
                            ),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.attrName = data.attr.attrName
                                this.dataForm.searchType = data.attr.searchType
                                this.dataForm.valueType = data.attr.valueType
                                this.dataForm.icon = data.attr.icon
                                this.dataForm.valueSelect = data.attr.valueSelect.split(';')
                                this.dataForm.attrType = data.attr.attrType
                                this.dataForm.enable = data.attr.enable
                                this.dataForm.catalogId = data.attr.catalogId
                                this.dataForm.showDesc = data.attr.showDesc
                                this.catalogPath = data.attr.catalogPath
                                this.$nextTick(() => {
                                    this.dataForm.attrGroupId = data.attr.attrGroupId
                                })
                            }
                        })
                    }
                })
            },
            dataFormSubmit () {
                this.$refs['dataForm'].validate(valid => {
                    if (valid) {
                        this.$http({
                            url: this.$http.adornUrl(
                                `/commodity/attr/${!this.dataForm.attrId ? 'save' : 'update'}`
                            ),
                            method: 'post',
                            data: this.$http.adornData({
                                attrId: this.dataForm.attrId || undefined,
                                attrName: this.dataForm.attrName,
                                searchType: this.dataForm.searchType,
                                valueType: this.dataForm.valueType,
                                icon: this.dataForm.icon,
                                valueSelect: this.dataForm.valueSelect.join(';'),
                                attrType: this.dataForm.attrType,
                                enable: this.dataForm.enable,
                                catalogId: this.dataForm.catalogId,
                                attrGroupId: this.dataForm.attrGroupId,
                                showDesc: this.dataForm.showDesc
                            })
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: 'Submit successfully',
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
            },
            // dialogClose
            dialogClose () {
                this.catalogPath = []
            }
        }
    }
</script>