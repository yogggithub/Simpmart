<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item label="warehouse">
                <el-select
                    clearable
                    placeholder="Select"
                    style="width:120px;"
                    v-model="dataForm.wareId"
                >
                    <el-option :key="w.id" :label="w.name" :value="w.id" v-for="w in wareList"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="status">
                <el-select
                    clearable
                    placeholder="Select"
                    style="width:120px;"
                    v-model="dataForm.status"
                >
                    <el-option :value="0" label="created"></el-option>
                    <el-option :value="1" label="assigned"></el-option>
                    <el-option :value="2" label="purchasing"></el-option>
                    <el-option :value="3" label="finished"></el-option>
                    <el-option :value="4" label="failure"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="key word">
                <el-input
                    clearable
                    placeholder="parameter"
                    style="width:120px;"
                    v-model="dataForm.key"
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">Query</el-button>
                <el-button
                    @click="addOrUpdateHandle()"
                    type="primary"
                    v-if="isAuth('ware:purchasedetail:save')"
                >Add</el-button>
                <el-dropdown
                    :disabled="dataListSelections.length <= 0"
                    @command="handleBatchCommand"
                >
                    <el-button type="danger">
                        Batch
                        <i class="el-icon-arrow-down el-icon--right"></i>
                    </el-button>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="delete">Delete</el-dropdown-item>
                        <el-dropdown-item command="merge">Merge</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
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
            <el-table-column
                align="center"
                header-align="center"
                label="purchase order id"
                prop="purchaseId"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="purchase sku id"
                prop="skuId"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="purchase quantity"
                prop="skuNum"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="purchase amount"
                prop="skuPrice"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="warehouse id"
                prop="wareId"
            ></el-table-column>
            <el-table-column align="center" header-align="center" label="status" prop="status">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status==0">created</el-tag>
                    <el-tag type="info" v-if="scope.row.status==1">assigned</el-tag>
                    <el-tag type="wanring" v-if="scope.row.status==2">purchasing</el-tag>
                    <el-tag type="success" v-if="scope.row.status==3">finished</el-tag>
                    <el-tag type="danger" v-if="scope.row.status==4">failure</el-tag>
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
        <el-dialog :visible.sync="mergedialogVisible" title="Merge">
            <!-- id  assignee_id  assignee_name  phone   priority status -->
            <el-select clearable filterable placeholder="Select" v-model="purchaseId">
                <el-option
                    :key="item.id"
                    :label="item.id"
                    :value="item.id"
                    v-for="item in purchasetableData"
                >
                    <span style="float: left">{{ item.id }}</span>
                    <span
                        style="float: right; color: #8492a6; font-size: 13px"
                    >{{ item.assigneeName }}：{{item.phone}}</span>
                </el-option>
            </el-select>
            <span class="dialog-footer" slot="footer">
                <el-button @click="mergedialogVisible = false">Cancel</el-button>
                <el-button @click="mergeItem" type="primary">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import AddOrUpdate from './purchasedetail-add-or-update'
    export default {
        data () {
            return {
                dataForm: {
                    key: '',
                    status: '',
                    wareId: ''
                },
                wareList: [],
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false,
                mergedialogVisible: false,
                purchaseId: '',
                purchasetableData: []
            }
        },
        components: {
            AddOrUpdate
        },
        activated () {
            this.getDataList()
            this.getWares()
        },
        methods: {
            mergeItem () {
                let items = this.dataListSelections.map(item => {
                    return item.id
                })
                if (!this.purchaseId) {
                    this.$confirm('No Purchase Order is selected, a new order will be created automatically for consolidation', 'Warning', {
                        confirmButtonText: 'Confirm',
                        cancelButtonText: 'Cancel',
                        type: 'warning'
                    }).then(() => {
                        this.$http({
                            url: this.$http.adornUrl('/ware/purchase/merge'),
                            method: 'post',
                            data: this.$http.adornData({ items: items }, false)
                        }).then(({ data }) => {
                            this.getDataList()
                        })
                    }).catch(() => { })
                } else {
                    this.$http({
                        url: this.$http.adornUrl('/ware/purchase/merge'),
                        method: 'post',
                        data: this.$http.adornData(
                            { purchaseId: this.purchaseId, items: items },
                            false
                        )
                    }).then(({ data }) => {
                        this.getDataList()
                    })
                }
                this.mergedialogVisible = false
            },
            getUnreceivedPurchase () {
                this.$http({
                    url: this.$http.adornUrl('/ware/purchase/unreceived/list'),
                    method: 'get',
                    params: this.$http.adornParams({})
                }).then(({ data }) => {
                    this.purchasetableData = data.page.list
                })
            },
            handleBatchCommand (cmd) {
                if (cmd === 'delete') {
                    this.deleteHandle()
                }
                if (cmd === 'merge') {
                    if (this.dataListSelections.length !== 0) {
                        this.getUnreceivedPurchase()
                        this.mergedialogVisible = true
                    } else {
                        this.$alert('Please select the requirements to be merged first', 'Warning', {
                            confirmButtonText: 'Confirm',
                            callback: action => { }
                        })
                    }
                }
            },
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
                    url: this.$http.adornUrl('/ware/purchasedetail/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: this.pageIndex,
                        limit: this.pageSize,
                        key: this.dataForm.key,
                        status: this.dataForm.status,
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
                        url: this.$http.adornUrl('/ware/purchasedetail/delete'),
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
