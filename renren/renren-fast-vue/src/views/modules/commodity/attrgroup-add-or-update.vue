<template>
    <el-dialog
        :close-on-click-modal="false"
        :title="!dataForm.id ? 'Add' : 'Update'"
        :visible.sync="visible"
        @closed="handleClosed"
    >
        <el-form
            :model="dataForm"
            :rules="dataRule"
            @keyup.enter.native="dataFormSubmit()"
            label-width="145px"
            ref="dataForm"
        >
            <el-form-item label="group name" prop="attrGroupName">
                <el-input placeholder="group name" v-model="dataForm.attrGroupName"></el-input>
            </el-form-item>
            <el-form-item label="group sequence" prop="sort">
                <el-input placeholder="group sequence" v-model="dataForm.sort"></el-input>
            </el-form-item>
            <el-form-item label="group description" prop="description">
                <el-input placeholder="group description" v-model="dataForm.description"></el-input>
            </el-form-item>
            <el-form-item label="group icon" prop="icon">
                <el-input placeholder="group icon" v-model="dataForm.icon"></el-input>
            </el-form-item>
            <el-form-item label="attribute category id" prop="catalogId">
                <el-cascader
                    :options="categories"
                    :props="props"
                    placeholder="Select"
                    v-model="dataForm.catalogPath"
                    filterable
                ></el-cascader>
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
                    attrGroupId: 0,
                    attrGroupName: '',
                    sort: '',
                    description: '',
                    icon: '',
                    catalogPath: [],
                    catalogId: 0
                },
                // store the categories get from database
                categories: [],
                // Clarify which attributes the cascader uses
                props: {
                    label: 'name',
                    value: 'catId',
                    children: 'childrenCategory'
                },
                dataRule: {
                    attrGroupName: [
                        { required: true, message: 'group name can not be empty', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: 'group sequence can not be empty', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'group name can not be empty', trigger: 'blur' }
                    ],
                    icon: [
                        { required: true, message: 'group icon can not be empty', trigger: 'blur' }
                    ],
                    catalogId: [
                        { required: true, message: 'attribute category id can not be empty', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            init (id) {
                this.dataForm.attrGroupId = id || 0
                this.visible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].resetFields()
                    if (this.dataForm.attrGroupId) {
                        this.$http({
                            url: this.$http.adornUrl(`/commodity/attrgroup/info/${this.dataForm.attrGroupId}`),
                            method: 'get',
                            params: this.$http.adornParams()
                        }).then(({ data }) => {
                            if (data && data.code === 0) {
                                this.dataForm.attrGroupName = data.attrGroup.attrGroupName
                                this.dataForm.sort = data.attrGroup.sort
                                this.dataForm.description = data.attrGroup.description
                                this.dataForm.icon = data.attrGroup.icon
                                this.dataForm.catalogPath = data.attrGroup.catalogPath
                                this.dataForm.catalogId = data.attrGroup.catalogId
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
                            url: this.$http.adornUrl(`/commodity/attrgroup/${!this.dataForm.attrGroupId ? 'save' : 'update'}`),
                            method: 'post',
                            data: this.$http.adornData({
                                'attrGroupId': this.dataForm.attrGroupId || undefined,
                                'attrGroupName': this.dataForm.attrGroupName,
                                'sort': this.dataForm.sort,
                                'description': this.dataForm.description,
                                'icon': this.dataForm.icon,
                                'catalogId': this.dataForm.catalogPath[this.dataForm.catalogPath.length - 1]
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
            },
            handleClosed () {
                this.dataForm.catalogPath = []
            },
            getCategories () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/category/list/tree'),
                    method: 'get'
                }).then(({ data }) => {
                    this.categories = data.data
                })
            }
        },
        created () {
            this.getCategories()
        }
    }
</script>
