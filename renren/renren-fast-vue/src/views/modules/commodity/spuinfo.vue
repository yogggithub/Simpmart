<template>
    <div class="mod-config">
        <el-table
            :data="dataList"
            @selection-change="selectionChangeHandle"
            border
            style="width: 100%;"
            v-loading="dataListLoading"
        >
            <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
            <el-table-column align="center" header-align="center" label="id" prop="id"></el-table-column>
            <el-table-column align="center" header-align="center" label="name" prop="spuName"></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="description"
                prop="spuDescription"
            ></el-table-column>
            <el-table-column align="center" header-align="center" label="catalog" prop="catalogId"></el-table-column>
            <el-table-column align="center" header-align="center" label="brand" prop="brandId"></el-table-column>
            <el-table-column align="center" header-align="center" label="weight" prop="weight"></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="satuse"
                prop="publishStatus"
            >
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.publishStatus == 0">new created</el-tag>
                    <el-tag v-if="scope.row.publishStatus == 1">avaliable</el-tag>
                    <el-tag v-if="scope.row.publishStatus == 2">unavailable</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" header-align="center" label="创建时间" prop="createTime"></el-table-column>
            <el-table-column align="center" header-align="center" label="修改时间" prop="updateTime"></el-table-column>
            <el-table-column
                align="center"
                fixed="right"
                header-align="center"
                label="操作"
                width="150"
            >
                <template slot-scope="scope">
                    <el-button
                        @click="productUp(scope.row.id)"
                        size="small"
                        type="text"
                        v-if="scope.row.publishStatus == 0"
                    >active</el-button>
                    <el-button
                        @click="attrUpdateShow(scope.row)"
                        size="small"
                        type="text"
                    >specifications</el-button>
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
    </div>
</template>

<script>
    export default {
        data () {
            return {
                dataSub: null,
                dataForm: {},
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false
            }
        },
        props: {
            catId: {
                type: Number,
                default: 0
            }
        },
        components: {},
        activated () {
            this.getDataList()
        },
        methods: {
            productUp (id) {
                this.$http({
                    url: this.$http.adornUrl('/commodity/spuinfo/' + id + '/up'),
                    method: 'post'
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
            },
            attrUpdateShow (row) {
                console.log(row)
                this.$router.push({
                    path: '/commodity-attrupdate',
                    query: { spuId: row.id, catalogId: row.catalogId }
                })
            },
            getDataList () {
                this.dataListLoading = true
                let param = {}
                Object.assign(param, this.dataForm, {
                    page: this.pageIndex,
                    limit: this.pageSize
                })
                this.$http({
                    url: this.$http.adornUrl('/commodity/spuinfo/list'),
                    method: 'get',
                    params: this.$http.adornParams(param)
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
            addOrUpdateHandle (id) { }
        },
        mounted () {
            this.dataSub = this.PubSub.subscribe('dataForm', (msg, val) => {
                this.dataForm = val
                this.getDataList()
            })
        },
        beforeDestroy () {
            this.PubSub.unsubscribe(this.dataSub)
        }
    }
</script>
