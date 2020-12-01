<template>
    <div class="mod-config">
        <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
                <el-input clearable placeholder="Parameter Name" v-model="dataForm.key"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="getDataList()">Query</el-button>
                <el-button
                    @click="addOrUpdateHandle()"
                    type="primary"
                    v-if="isAuth('commodity:brand:save')"
                >Add</el-button>
                <el-button
                    :disabled="dataListSelections.length <= 0"
                    @click="deleteHandle()"
                    type="danger"
                    v-if="isAuth('commodity:brand:delete')"
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
            <el-table-column align="center" header-align="center" label="Brand ID" prop="brandId"></el-table-column>
            <el-table-column align="center" header-align="center" label="Brand Name" prop="name"></el-table-column>
            <el-table-column align="center" header-align="center" label="Logo" prop="logo">
                <template slot-scope="scope">
                    <img :src="scope.row.logo" style="width: 100px; height: 100px" />
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="Description"
                prop="description"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="Showing Status"
                prop="showStatus"
            >
                <template slot-scope="scope">
                    <el-switch
                        :active-value="1"
                        :inactive-value="0"
                        @change="updateShowStatus(scope.row)"
                        active-color="#13ce66"
                        inactive-color="#ff4949"
                        v-model="scope.row.showStatus"
                    ></el-switch>
                </template>
            </el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="Initial Letter"
                prop="firstLetter"
            ></el-table-column>
            <el-table-column
                align="center"
                header-align="center"
                label="Brand Sequence"
                prop="sort"
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
                        @click="updatecatalogHandle(scope.row.brandId)"
                        size="small"
                        type="text"
                    >Related Category</el-button>
                    <el-button
                        @click="addOrUpdateHandle(scope.row.brandId)"
                        size="small"
                        type="text"
                    >Update</el-button>
                    <el-button
                        @click="deleteHandle(scope.row.brandId)"
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
        <!-- pop-up window, add / update -->
        <add-or-update @refreshDataList="getDataList" ref="addOrUpdate" v-if="addOrUpdateVisible"></add-or-update>
        <el-dialog :visible.sync="cateRelationDialogVisible" title="Related Category" width="55%">
            <el-popover
                @after-leave="handleClosed"
                placement="right-end"
                v-model="popcatalogSelectVisible"
            >
                <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
                <div style="text-align: right; margin: 0">
                    <el-button
                        @click="popcatalogSelectVisible = false"
                        size="mini"
                        type="text"
                    >Cancel</el-button>
                    <el-button @click="addcatalogSelect" size="mini" type="primary">Confirm</el-button>
                </div>
                <el-button slot="reference">New</el-button>
            </el-popover>
            <el-table :data="cateRelationTableData" style="width: 100%">
                <el-table-column label="#" prop="id"></el-table-column>
                <el-table-column label="brand name" prop="brandName"></el-table-column>
                <el-table-column label="category name" prop="catalogName"></el-table-column>
                <el-table-column align="center" fixed="right" header-align="center" label="Action">
                    <template slot-scope="scope">
                        <el-button
                            @click="deleteCateRelationHandle(scope.row.id,scope.row.brandId)"
                            size="small"
                            type="text"
                        >Remove</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span class="dialog-footer" slot="footer">
                <el-button @click="cateRelationDialogVisible = false">Cancel</el-button>
                <el-button @click="cateRelationDialogVisible = false" type="primary">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import AddOrUpdate from './brand-add-or-update'
    import CategoryCascader from '../common/category-cascader'
    export default {
        data () {
            return {
                dataForm: {
                    key: ''
                },
                brandId: 0,
                catalogPath: [],
                dataList: [],
                cateRelationTableData: [],
                pageIndex: 1,
                pageSize: 10,
                totalPage: 0,
                dataListLoading: false,
                dataListSelections: [],
                addOrUpdateVisible: false,
                cateRelationDialogVisible: false,
                popcatalogSelectVisible: false
            }
        },
        components: {
            AddOrUpdate,
            CategoryCascader
        },
        activated () {
            this.getDataList()
        },
        methods: {
            // get data list
            getDataList () {
                this.dataListLoading = true
                this.$http({
                    url: this.$http.adornUrl('/commodity/brand/list'),
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
            updateBrandStatus (data) {
                let { brandId, showStatus } = data
                this.$http({
                    url: this.$http.adornUrl('/commodity/brand/update/status'),
                    method: 'post',
                    data: this.$http.adornData({ brandId, showStatus }, false)
                }).then(({ data }) => {
                    this.$message.success('Update successfully')
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
                    return item.brandId
                })
                this.$confirm(`Do you want to [${id ? 'delete' : 'batch delete'}] [id=${ids.join(',')}]?`, 'warning', {
                    confirmButtonText: 'Sure',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        url: this.$http.adornUrl('/commodity/brand/delete'),
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
            // handle switch changing event
            updateShowStatus (brand) {
                let { brandId, name, showStatus } = brand
                this.$http({
                    url: this.$http.adornUrl('/commodity/brand/update'),
                    method: 'post',
                    data: this.$http.adornData({ brandId, name, showStatus }, false)
                }).then(({ data }) => {
                    this.$message.success(
                        `${brand.showStatus === 0 ? brand.name + ' will not display' : brand.name + ' will display'}`
                    )
                })
            },
            addcatalogSelect () {
                this.popcatalogSelectVisible = false
                this.$http({
                    url: this.$http.adornUrl('/commodity/categorybrandrelation/save'),
                    method: 'post',
                    data: this.$http.adornData({ brandId: this.brandId, catalogId: this.catalogPath[this.catalogPath.length - 1] }, false)
                }).then(({ data }) => {
                    this.getCateRelation()
                })
            },
            deleteCateRelationHandle (id, brandId) {
                this.$http({
                    url: this.$http.adornUrl('/commodity/categorybrandrelation/delete'),
                    method: 'post',
                    data: this.$http.adornData([id], false)
                }).then(({ data }) => {
                    this.getCateRelation()
                })
            },
            updatecatalogHandle (brandId) {
                this.cateRelationDialogVisible = true
                this.brandId = brandId
                this.getCateRelation()
            },
            getCateRelation () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/categorybrandrelation/catalog/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        brandId: this.brandId
                    })
                }).then(({ data }) => {
                    this.cateRelationTableData = data.data
                })
            },
            handleClosed () {
                this.catalogPath = []
            }
        }
    }
</script>
