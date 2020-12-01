<template>
    <div>
        <el-dialog :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
            <el-dialog
                :visible.sync="innerVisible"
                append-to-body
                title="Select Argument"
                width="40%"
            >
                <div>
                    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
                        <el-form-item>
                            <el-input clearable placeholder="argument name" v-model="dataForm.key"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="getDataList()">Query</el-button>
                        </el-form-item>
                    </el-form>
                    <el-table
                        :data="dataList"
                        @selection-change="innerSelectionChangeHandle"
                        border
                        style="width: 100%;"
                        v-loading="dataListLoading"
                    >
                        <el-table-column align="center" header-align="center" type="selection"></el-table-column>
                        <el-table-column
                            align="center"
                            header-align="center"
                            label="attribute id"
                            prop="attrId"
                        ></el-table-column>
                        <el-table-column
                            align="center"
                            header-align="center"
                            label="attribute name"
                            prop="attrName"
                        ></el-table-column>
                        <el-table-column
                            align="center"
                            header-align="center"
                            label="attribute icon"
                            prop="icon"
                        ></el-table-column>
                        <el-table-column
                            align="center"
                            header-align="center"
                            label="Optional Value List"
                            prop="valueSelect"
                        ></el-table-column>
                    </el-table>
                    <el-pagination
                        :current-page="pageIndex"
                        :page-size="pageSize"
                        :page-sizes="[10, 20, 50, 100]"
                        :total="totalPage"
                        @current-change="currentChangeHandle"
                        @size-change="sizeChangeHandle"
                        layout="total, sizes, prev, pager, next, jumper"
                    ></el-pagination>
                </div>
                <div class="dialog-footer" slot="footer">
                    <el-button @click="innerVisible = false">Cancel</el-button>
                    <el-button @click="submitAddRealtion" type="primary">Confirm</el-button>
                </div>
            </el-dialog>
            <el-row>
                <el-col :span="24">
                    <el-button @click="addRelation" type="primary">Create</el-button>
                    <el-button
                        :disabled="dataListSelections.length <= 0"
                        @click="batchDeleteRelation"
                        type="danger"
                    >Batch Remove</el-button>
                    <!--  -->
                    <el-table
                        :data="relationAttrs"
                        @selection-change="selectionChangeHandle"
                        border
                        style="width: 200%"
                    >
                        <el-table-column
                            align="center"
                            header-align="center"
                            type="selection"
                            width="50"
                        ></el-table-column>
                        <el-table-column label="#" prop="attrId"></el-table-column>
                        <el-table-column label="attribute name" prop="attrName"></el-table-column>
                        <el-table-column label="Optional Values" prop="valueSelect">
                            <template slot-scope="scope">
                                <el-tooltip placement="top">
                                    <div slot="content">
                                        <span
                                            :key="index"
                                            v-for="(i,index) in scope.row.valueSelect.split(';')"
                                        >
                                            {{i}}
                                            <br />
                                        </span>
                                    </div>
                                    <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
                                </el-tooltip>
                            </template>
                        </el-table-column>
                        <el-table-column
                            align="center"
                            fixed="right"
                            header-align="center"
                            label="Action"
                        >
                            <template slot-scope="scope">
                                <el-button
                                    @click="relationRemove(scope.row.attrId)"
                                    size="small"
                                    type="text"
                                >Remove</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        components: {},
        props: {},
        data () {
            return {
                attrGroupId: 0,
                visible: false,
                innerVisible: false,
                relationAttrs: [],
                dataListSelections: [],
                dataForm: {
                    key: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                innerdataListSelections: []
            }
        },
        computed: {},
        watch: {},
        methods: {
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            innerSelectionChangeHandle (val) {
                this.innerdataListSelections = val
            },
            addRelation () {
                this.getDataList()
                this.innerVisible = true
            },
            batchDeleteRelation (val) {
                let postData = []
                this.dataListSelections.forEach(item => {
                    postData.push({ attrId: item.attrId, attrGroupId: this.attrGroupId })
                })
                this.$http({
                    url: this.$http.adornUrl('/commodity/attrgroup/attr/relation/delete'),
                    method: 'post',
                    data: this.$http.adornData(postData, false)
                }).then(({ data }) => {
                    if (data.code === 0) {
                        this.$message.success('Delete Successfully')
                        this.init(this.attrGroupId)
                    } else {
                        this.$message.error(data.msg)
                    }
                })
            },
            // delete relationship
            relationRemove (attrId) {
                let data = []
                data.push({ attrId, attrGroupId: this.attrGroupId })
                this.$http({
                    url: this.$http.adornUrl('/commodity/attrgroup/attr/relation/delete'),
                    method: 'post',
                    data: this.$http.adornData(data, false)
                }).then(({ data }) => {
                    if (data.code === 0) {
                        this.$message.success('Delete Successfully')
                        this.init(this.attrGroupId)
                    } else {
                        this.$message.error(data.msg)
                    }
                })
            },
            submitAddRealtion () {
                this.innerVisible = false
                if (this.innerdataListSelections.length > 0) {
                    let postData = []
                    this.innerdataListSelections.forEach(item => {
                        postData.push({ attrId: item.attrId, attrGroupId: this.attrGroupId })
                    })
                    this.$http({
                        url: this.$http.adornUrl('/commodity/attrgroup/attr/relation'),
                        method: 'post',
                        data: this.$http.adornData(postData, false)
                    }).then(({ data }) => {
                        if (data.code === 0) {
                            this.$message.success('Create new relationship successfully')
                        }
                        this.$emit('refreshData')
                        this.init(this.attrGroupId)
                    })
                } else {
                }
            },
            init (id) {
                this.attrGroupId = id || 0
                this.visible = true
                this.$http({
                    url: this.$http.adornUrl(
                        '/commodity/attrgroup/' + this.attrGroupId + '/attr/relation'
                    ),
                    method: 'get',
                    params: this.$http.adornParams({})
                }).then(({ data }) => {
                    this.relationAttrs = data.data
                })
            },
            dialogClose () { },
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl(
                        '/commodity/attrgroup/' + this.attrGroupId + '/noattr/relation'
                    ),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: this.pageIndex,
                        limit: this.pageSize,
                        key: this.dataForm.key
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        console.log('data', data)
                        this.dataList = data.page.list
                        this.totalPage = data.page.totalCount
                    } else {
                        this.dataList = []
                        this.totalPage = 0
                    }
                    this.dataListLoading = false
                })
            },
            sizeChangeHandle (val) {
                this.pageSize = val
                this.pageIndex = 1
                this.getDataList()
            },
            currentChangeHandle (val) {
                this.pageIndex = val
                this.getDataList()
            }
        }
    }
</script>
<style scoped>
</style>