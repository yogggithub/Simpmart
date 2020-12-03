<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item label="status">
                <el-select
                    clearable
                    placeholder="Select"
                    style="width:120px;"
                    v-model="dataForm.status"
                >
                    <el-option :value="0" label="created"></el-option>
                    <el-option :value="1" label="assigned"></el-option>
                    <el-option :value="2" label="received"></el-option>
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
                    v-if="isAuth('ware:purchase:save')"
                >Add</el-button>
                <el-button
                    :disabled="dataListSelections.length <= 0"
                    @click="deleteHandle()"
                    type="danger"
                    v-if="isAuth('ware:purchase:delete')"
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
            <el-table-column align="center" header-align="center" label="purchase order id" prop="id"></el-table-column>
            <el-table-column align="center" header-align="center" label="purchase staff id" prop="assigneeId"></el-table-column>
            <el-table-column align="center" header-align="center" label="purchase staff name" prop="assigneeName"></el-table-column>
            <el-table-column align="center" header-align="center" label="contact information" prop="phone"></el-table-column>
            <el-table-column align="center" header-align="center" label="priority" prop="priority"></el-table-column>
            <el-table-column align="center" header-align="center" label="status" prop="status">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == 0">created</el-tag>
                    <el-tag type="info" v-if="scope.row.status == 1">assigned</el-tag>
                    <el-tag type="warning" v-if="scope.row.status == 2">received</el-tag>
                    <el-tag type="success" v-if="scope.row.status == 3">finished</el-tag>
                    <el-tag type="danger" v-if="scope.row.status == 4">failure</el-tag>
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="warehouse id"
                prop="wareId"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="total amount"
                prop="amount"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="create time"
                prop="createTime"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="update time"
                prop="updateTime"
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
                        @click="opendrawer(scope.row)"
                        size="small"
                        type="text"
                        v-if="scope.row.status==0||scope.row.status==1"
                    >Assign</el-button>
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
        <el-dialog
            :visible.sync="caigoudialogVisible"
            title="Assigning Procurement Staff"
            width="30%"
        >
            <el-select filterable placeholder="Select" v-model="userId">
                <el-option
                    :key="item.userId"
                    :label="item.username"
                    :value="item.userId"
                    v-for="item in userList"
                ></el-option>
            </el-select>
            <span class="dialog-footer" slot="footer">
                <el-button @click="caigoudialogVisible = false">Cancel</el-button>
                <el-button @click="assignUser" type="primary">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import AddOrUpdate from './purchase-add-or-update'
    export default {
        data () {
            return {
                currentRow: {},
                dataForm: {
                    key: '',
                    status: ''
                },
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false,
                caigoudialogVisible: false,
                userId: '',
                userList: []
            }
        },
        components: {
            AddOrUpdate
        },
        activated () {
            this.getDataList()
        },
        created () {

        },
        methods: {
            opendrawer (row) {
                this.getUserList()
                this.currentRow = row
                this.caigoudialogVisible = true
            },
            assignUser () {
                let _this = this
                let user = {}
                this.userList.forEach(item => {
                    if (item.userId === _this.userId) {
                        user = item
                    }
                })
                this.caigoudialogVisible = false
                this.$http({
                    url: this.$http.adornUrl(
                        `/ware/purchase/update`
                    ),
                    method: 'post',
                    data: this.$http.adornData({
                        id: this.currentRow.id || undefined,
                        assigneeId: user.userId,
                        assigneeName: user.username,
                        phone: user.mobile,
                        status: 1
                    })
                }).then(({ data }) => {
                    if (data && data.code === 0) {
                        this.$message({
                            message: 'Successfully',
                            type: 'success',
                            duration: 1500
                        })

                        this.userId = ''
                        this.getDataList()
                    } else {
                        this.$message.error(data.msg)
                    }
                })
            },
            getUserList () {
                this.$http({
                    url: this.$http.adornUrl('/sys/user/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: 1,
                        limit: 500
                    })
                }).then(({ data }) => {
                    this.userList = data.page.list
                })
            },
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/ware/purchase/list'),
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
                        url: this.$http.adornUrl('/ware/purchase/delete'),
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
