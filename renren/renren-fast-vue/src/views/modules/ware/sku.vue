<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item label="仓库">
                <el-select
                    clearable
                    placeholder="请选择仓库"
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
                <el-button @click="getDataList()">查询</el-button>
                <el-button
                    @click="addOrUpdateHandle()"
                    type="primary"
                    v-if="isAuth('ware:waresku:save')"
                >新增</el-button>
                <el-button
                    :disabled="dataListSelections.length <= 0"
                    @click="deleteHandle()"
                    type="danger"
                    v-if="isAuth('ware:waresku:delete')"
                >批量删除</el-button>
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
            <el-table-column align="center" header-align="center" label="仓库id" prop="wareId"></el-table-column>
            <el-table-column align="center" header-align="center" label="库存数" prop="stock"></el-table-column>
            <el-table-column align="center" header-align="center" label="sku_name" prop="skuName"></el-table-column>
            <el-table-column align="center" header-align="center" label="锁定库存" prop="stockLocked"></el-table-column>
            <el-table-column
                align="center"
                fixed="right"
                header-align="center"
                label="操作"
                width="150"
            >
                <template slot-scope="scope">
                    <el-button @click="addOrUpdateHandle(scope.row.id)" size="small" type="text">修改</el-button>
                    <el-button @click="deleteHandle(scope.row.id)" size="small" type="text">删除</el-button>
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
        <!-- 弹窗, 新增 / 修改 -->
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
            console.log('接收到', this.$route.query.skuId)
            if (this.$route.query.skuId) {
                this.dataForm.skuId = this.$route.query.skuId
            }
            this.getWares()
            this.getDataList()
        },
        methods: {
            getWares () {
                this.$http({
                    url: this.$http.adornUrl('/ware/wareinfo/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: 1,
                        limit: 500
                    })
                }).then(({ data }) => {
                    this.wareList = data.page.list
                })
            },
            // 获取数据列表
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/ware/waresku/list'),
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
            // 每页数
            sizeChangeHandle (val) {
                this.pageSize = val
                this.pageIndex = 1
                this.getDataList()
            },
            // 当前页
            currentChangeHandle (val) {
                this.pageIndex = val
                this.getDataList()
            },
            // 多选
            selectionChangeHandle (val) {
                this.dataListSelections = val
            },
            // 新增 / 修改
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
                        return item.id
                    })
                this.$confirm(
                    `确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`,
                    '提示',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/ware/waresku/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({ data }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
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
