<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
                <el-input clearable placeholder="parameter" v-model="dataForm.key"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">Query</el-button>
                <el-button
                    @click="addOrUpdateHandle()"
                    type="primary"
                    v-if="isAuth('ware:wareordertaskdetail:save')"
                >Add</el-button>
                <el-button
                    :disabled="dataListSelections.length <= 0"
                    @click="deleteHandle()"
                    type="danger"
                    v-if="isAuth('ware:wareordertaskdetail:delete')"
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
            <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
            <el-table-column align="center" header-align="center" label="id" prop="id"></el-table-column>
            <el-table-column align="center" header-align="center" label="sku id" prop="skuId"></el-table-column>
            <el-table-column align="center" header-align="center" label="sku name" prop="skuName"></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="order quantity"
                prop="skuNum"
            ></el-table-column>
            <el-table-column align="center" header-align="center" label="task id" prop="taskId"></el-table-column>
            <el-table-column
                align="center"
                fixed="right"
                header-align="center"
                label="Action"
                width="150"
            >
                <template slot-scope="scope">
                    <el-button
                        @click="addOrUpdateHandle(scope.row.id)"
                        size="small"
                        type="text"
                    >Edit</el-button>
                    <el-button @click="deleteHandle(scope.row.id)" size="small" type="text">Delete</el-button>
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
        <!-- pop-up window, add / update -->
        <add-or-update @refreshDataList="getDataList" ref="addOrUpdate" v-if="addOrUpdateVisible"></add-or-update>
    </div>
</template>

<script>
    import AddOrUpdate from './wareordertaskdetail-add-or-update'
    export default {
        data () {
            return {
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
        components: {
            AddOrUpdate
        },
        activated () {
            this.getDataList()
        },
        methods: {
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/ware/wareordertaskdetail/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        'page': this.pageIndex,
                        'limit': this.pageSize,
                        'key': this.dataForm.key
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
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            addOrUpdateHandle (id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })
            },
            deleteHandle (id) {
                var ids = id ? [id] : this.dataListSelections.map(item => {
                    return item.id
                })
                this.$confirm(`Do you want to ${id ? 'DELETE' : 'BATCH DELETE'} id=${ids.join(',')}?`, 'Warning', {
                    confirmButtonText: 'Confirm',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/ware/wareordertaskdetail/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: 'Successfully',
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
