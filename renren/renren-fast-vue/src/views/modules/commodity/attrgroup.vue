<template>
    <el-row :gutter="20">
        <!-- ELementUI have 24 columns in each row -->
        <el-col :span="6">
            <category @tree-node-click="treeNodeClick"></category>
        </el-col>
        <el-col :span="18">
            <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
                <el-form-item>
                    <el-input clearable placeholder="Parameter Name" v-model="dataForm.key"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="getDataList()">Query</el-button>
                    <el-button @click="getAllDataList()" type="success">List ALL</el-button>
                    <el-button
                        @click="addOrUpdateHandle()"
                        type="primary"
                        v-if="isAuth('commodity:attrgroup:save')"
                    >Add</el-button>
                    <el-button
                        :disabled="dataListSelections.length <= 0"
                        @click="deleteHandle()"
                        type="danger"
                        v-if="isAuth('commodity:attrgroup:delete')"
                    >Batch Deletion</el-button>
                </el-form-item>
            </el-form>
            <el-table
                :data="dataList"
                @selection-change="selectionChangeHandle"
                border
                style="width: 100%;"
                v-loading="dataListLoading"
            >
                <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="attribute group id"
                    prop="attrGroupId"
                ></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="group name"
                    prop="attrGroupName"
                ></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="group sequence"
                    prop="sort"
                ></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="group description"
                    prop="description"
                ></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="group icon"
                    prop="icon"
                ></el-table-column>
                <el-table-column
                    align="center"
                    header-align="center"
                    label="attribute category id"
                    prop="catalogId"
                ></el-table-column>
                <el-table-column
                    align="center"
                    fixed="right"
                    header-align="center"
                    label="Action"
                    width="150"
                >
                    <template slot-scope="scope">
                        <el-button
                            @click="relationHandle(scope.row.attrGroupId)"
                            size="small"
                            type="text"
                        >Related</el-button>
                        <el-button
                            @click="addOrUpdateHandle(scope.row.attrGroupId)"
                            size="small"
                            type="text"
                        >Update</el-button>
                        <el-button
                            @click="deleteHandle(scope.row.attrGroupId)"
                            size="small"
                            type="text"
                        >Delete</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                :current-page="pageIndex"
                :page-count="totalPage"
                :page-size="pageSize"
                :page-sizes="[10, 20, 50, 100]"
                :total="totalCount"
                @current-change="currentChangeHandle"
                @size-change="sizeChangeHandle"
                layout="total, sizes, prev, pager, next, jumper"
            ></el-pagination>
            <!-- pop-up window, add / update -->
            <add-or-update
                @refreshDataList="getDataList"
                ref="addOrUpdate"
                v-if="addOrUpdateVisible"
            ></add-or-update>
            <!-- update relationship -->
            <relation-update @refreshData="getDataList" ref="relationUpdate" v-if="relationVisible"></relation-update>
        </el-col>
    </el-row>
</template>

<script>
    import AddOrUpdate from './attrgroup-add-or-update'
    import category from '../common/category'
    import RelationUpdate from './attr-group-relation'
    export default {
        data () {
            return {
                dataForm: {
                    key: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalCount: 0,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false,
                clickedCatId: 0,
                relationVisible: false
            }
        },
        components: {
            AddOrUpdate,
            category,
            RelationUpdate
        },
        activated () {
            this.getDataList()
        },
        methods: {
            // get data list
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl(`/commodity/attrgroup/list/${this.clickedCatId}`),
                    method: 'get',
                    params: this.$http.adornParams({
                        'page': this.pageIndex,
                        'limit': this.pageSize,
                        'key': this.dataForm.key
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.dataList = data.page.list
                        this.totalPage = data.page.totalPage
                        this.totalCount = data.page.totalCount
                    } else {
                        this.dataList = []
                        this.totalPage = 0
                    }
                    this.dataListLoading = false
                    this.clickedCatId = 0
                })
            },
            // amount per page
            sizeChangeHandle (val) {
                this.pageSize = val
                this.pageIndex = 1
                this.getDataList()
            },
            // current page
            currentChangeHandle (val) {
                this.pageIndex = val
                this.getDataList()
            },
            // multiple selection
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            // add / update
            addOrUpdateHandle (id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })
            },
            // delete
            deleteHandle (id) {
                var ids = id ? [id] : this.dataListSelections.map(item => {
                    return item.attrGroupId
                })
                this.$confirm(`Do you want to [${id ? 'delete' : 'batch delete'}] [id=${ids.join(',')}]?`, 'warning', {
                    confirmButtonText: 'Sure',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/commodity/attrgroup/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: 'Completed',
                                type: 'success',
                                duration: 1500,
                                onClose: () => {
                                    this.getDataList()
                                }
                            })
                        } else {
                            this.$message.error(data.msg)
                        }
                    })
                })
            },
            treeNodeClick (data, node, component) {
                if (node.level === 3) {
                    this.clickedCatId = data.catId
                    this.getDataList()
                }
            },
            // Establishing group and attribute associations
            relationHandle (groupId) {
                this.relationVisible = true
                this.$nextTick(() => {
                    this.$refs.relationUpdate.init(groupId)
                })
            },
            // handle tree node clicked event
            treenodeclick (data, node, component) {
                if (node.level === 3) {
                    this.catId = data.catId
                    this.getDataList()
                }
            },
            getAllDataList () {
                this.catId = 0
                this.getDataList()
            }
        }
    }
</script>
