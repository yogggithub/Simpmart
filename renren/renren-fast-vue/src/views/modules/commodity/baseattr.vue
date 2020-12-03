<template>
    <el-row :gutter="20">
        <el-col :span="6">
            <category @tree-node-click="treenodeclick"></category>
        </el-col>
        <el-col :span="18">
            <div class="mod-config">
                <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
                    <el-form-item>
                        <el-input clearable placeholder="attribute name" v-model="dataForm.key"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="getDataList()">Query</el-button>
                        <el-button @click="getAllDataList()" type="success">List ALL</el-button>
                        <el-button
                            @click="addOrUpdateHandle()"
                            type="primary"
                            v-if="isAuth('product:attr:save')"
                        >Add</el-button>
                        <el-button
                            :disabled="dataListSelections.length <= 0"
                            @click="deleteHandle()"
                            type="danger"
                            v-if="isAuth('product:attr:delete')"
                        >Batch Delete</el-button>
                    </el-form-item>
                </el-form>
                <el-table
                    :data="dataList"
                    @selection-change="selectionChangeHandle"
                    border
                    style="width: 100%;"
                    v-loading="dataListLoading"
                >
                    <el-table-column
                        align="center"
                        header-align="center"
                        type="selection"
                        width="50"
                    ></el-table-column>
                    <el-table-column align="center" header-align="center" label="id" prop="attrId"></el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="attribute name"
                        prop="attrName"
                    ></el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="earch type"
                        prop="searchType"
                        v-if="attrtype == 1"
                    >
                        <template slot-scope="scope">
                            <i class="el-icon-success" v-if="scope.row.searchType==1"></i>
                            <i class="el-icon-error" v-else></i>
                        </template>
                    </el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="value type"
                        prop="valueType"
                    >
                        <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.valueType==0">signle value</el-tag>
                            <el-tag v-else>multiple value</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" header-align="center" label="icon" prop="icon"></el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="optional value"
                        prop="valueSelect"
                    >
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
                    <el-table-column align="center" header-align="center" label="enable" prop="enable">
                        <template slot-scope="scope">
                            <i class="el-icon-success" v-if="scope.row.enable==1"></i>
                            <i class="el-icon-error" v-else></i>
                        </template>
                    </el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="category name"
                        prop="catalogName"
                    ></el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="group name"
                        prop="groupName"
                        v-if="attrtype == 1"
                    ></el-table-column>
                    <el-table-column
                        align="center"
                        header-align="center"
                        label="show description"
                        prop="showDesc"
                        v-if="attrtype == 1"
                    >
                        <template slot-scope="scope">
                            <i class="el-icon-success" v-if="scope.row.showDesc==1"></i>
                            <i class="el-icon-error" v-else></i>
                        </template>
                    </el-table-column>
                    <el-table-column
                        align="center"
                        fixed="right"
                        header-align="center"
                        label="Action"
                        width="150"
                    >
                        <template slot-scope="scope">
                            <el-button
                                @click="addOrUpdateHandle(scope.row.attrId)"
                                size="small"
                                type="text"
                            >Edit</el-button>
                            <el-button
                                @click="deleteHandle(scope.row.attrId)"
                                size="small"
                                type="text"
                            >Delete</el-button>
                        </template>
                    </el-table-column>
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
                <!-- pop up windwo, add new or edit -->
                <add-or-update
                    :type="attrtype"
                    @refreshDataList="getDataList"
                    ref="addOrUpdate"
                    v-if="addOrUpdateVisible"
                ></add-or-update>
            </div>
        </el-col>
    </el-row>
</template>

<script>
    import Category from '../common/category'
    import AddOrUpdate from './attr-add-or-update'
    export default {
        components: { Category, AddOrUpdate },
        props: {
            attrtype: {
                type: Number,
                default: 1
            }
        },
        data () {
            return {
                catId: 0,
                type: 1,
                dataForm: {
                    key: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false
            }
        },
        activated () {
            this.getDataList()
        },
        methods: {
            treenodeclick (data, node, component) {
                if (node.level === 3) {
                    this.catId = data.catId
                    this.getDataList()
                }
            },
            getAllDataList () {
                this.catId = 0
                this.getDataList()
            },
            getDataList () {
                this.dataListLoading = true
                let type = this.attrtype === 0 ? 'sale' : 'base'
                this.$http({
                    url: this.$http.adornUrl(`/commodity/attr/${type}/list/${this.catId}`),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: this.pageIndex,
                        limit: this.pageSize,
                        key: this.dataForm.key
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
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
            },
            // value type
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            addOrUpdateHandle (id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })
            },
            // 删除
            deleteHandle (id) {
                var ids = id
                    ? [id]
                    : this.dataListSelections.map(item => {
                        return item.attrId
                    })
                this.$confirm(
                    `Do you want to [${id ? 'delete' : 'batch delete'}] [id=${ids.join(',')}]?`,
                    'Warning',
                    {
                        confirmButtonText: 'Confirm',
                        cancelButtonText: 'Cancel',
                        type: 'warning'
                    }
                ).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/commodity/attr/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: 'Delete Successfully',
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
            }
        }
    }
</script>
<style scoped>
</style>