<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item label="warehouse">
                <el-select
                    clearable
                    placeholder="Select"
                    style="width:160px;"
                    v-model="dataForm.wareId"
                >
                    <el-option :key="w.id" :label="w.name" :value="w.id" v-for="w in wareList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="skuId">
                <el-input clearable placeholder="skuId" v-model="dataForm.skuId"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">Query</el-button>
                <el-button
                    @click="addOrUpdateHandle()"
                    type="primary"
                    v-if="isAuth('ware:waresku:save')"
                >Add</el-button>
                <el-button
                    :disabled="dataListSelections.length <= 0"
                    @click="deleteHandle()"
                    type="danger"
                    v-if="isAuth('ware:waresku:delete')"
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
            <el-table-column align="center" header-align="center" label="sku_id" prop="skuId"></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="warehouse_id"
                prop="wareId"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="stock quantity"
                prop="stock"
            ></el-table-column>
            <el-table-column align="center" header-align="center" label="sku name" prop="skuName"></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="stock locked"
                prop="stockLocked"
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
    import AddOrUpdate from './waresku-add-or-update'
    export default {
        data () {
            return {
                wareList: [],
                dataForm: {
                    wareId: '',
                    skuId: ''
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
            if (this.$route.query.skuId) {
                this.dataForm.skuId = this.$route.query.skuId
            }
            this.getWares()
            this.getDataList()
        },
        methods: {
            getWares () {
                this.$http({
                    url: this.$http.adornUrl('/ware/warehouseinfo/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: 1,
                        limit: 500
                    })
                }).then(({ data }) => {
                    this.wareList = data.page.list
                })
            },
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/ware/warehousesku/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: this.pageIndex,
                        limit: this.pageSize,
                        skuId: this.dataForm.skuId,
                        wareId: this.dataForm.wareId
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
                var ids = id
                    ? [id]
                    : this.dataListSelections.map(item => {
                        return item.id
                    })
                this.$confirm(`Do you want to ${id ? 'DELETE' : 'BATCH DELETE'} id=${ids.join(',')}?`, 'Warning', {
                    confirmButtonText: 'Confirm',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/ware/warehousesku/delete'),
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
