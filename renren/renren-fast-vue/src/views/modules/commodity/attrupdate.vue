<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="16">
                <el-card class="box-card">
                    <el-tabs style="width:98%" tab-position="left">
                        <el-tab-pane
                            :key="group.attrGroupId"
                            :label="group.attrGroupName"
                            v-for="(group,gidx) in dataResp.attrGroups"
                        >
                            <!-- iterate attributees: spu.baseAttrs[0] = [{attrId:xx,val:}] -->
                            <el-form :model="dataResp" ref="form">
                                <el-form-item
                                    :key="attr.attrId"
                                    :label="attr.attrName"
                                    v-for="(attr,aidx) in group.attrs"
                                >
                                    <el-input
                                        type="hidden"
                                        v-model="dataResp.baseAttrs[gidx][aidx].attrId"
                                        v-show="false"
                                    ></el-input>
                                    <el-select
                                        :multiple="attr.valueType == 1"
                                        allow-create
                                        default-first-option
                                        filterable
                                        placeholder="Select or Input"
                                        v-model="dataResp.baseAttrs[gidx][aidx].attrValues"
                                    >
                                        <el-option
                                            :key="vidx"
                                            :label="val"
                                            :value="val"
                                            v-for="(val,vidx) in attr.valueSelect.split(';')"
                                        ></el-option>
                                    </el-select>
                                    <el-checkbox
                                        :false-label="0"
                                        :true-label="1"
                                        v-model="dataResp.baseAttrs[gidx][aidx].showDesc"
                                    >Quick Show</el-checkbox>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                    <div style="margin:auto">
                        <el-button
                            @click="submitSpuAttrs"
                            style="float:right"
                            type="success"
                        >Confirm</el-button>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        components: {},
        props: {},
        data () {
            return {
                spuId: '',
                catalogId: '',
                dataResp: {
                    attrGroups: [],
                    baseAttrs: []
                },
                spuAttrsMap: {}
            }
        },
        computed: {},
        methods: {
            clearData () {
                this.dataResp.attrGroups = []
                this.dataResp.baseAttrs = []
                this.spuAttrsMap = {}
            },
            getSpuBaseAttrs () {
                this.$http({
                    url: this.$http.adornUrl(`/product/attr/base/listforspu/${this.spuId}`),
                    method: 'get'
                }).then(({ data }) => {
                    data.data.forEach(item => {
                        this.spuAttrsMap['' + item.attrId] = item
                    })
                })
            },
            getQueryParams () {
                this.spuId = this.$route.query.spuId
                this.catalogId = this.$route.query.catalogId
            },
            showBaseAttrs () {
                let _this = this
                this.$http({
                    url: this.$http.adornUrl(
                        `/product/attrgroup/${this.catalogId}/withattr`
                    ),
                    method: 'get',
                    params: this.$http.adornParams({})
                }).then(({ data }) => {
                    data.data.forEach(item => {
                        let attrArray = []
                        item.attrs.forEach(attr => {
                            let v = ''
                            if (_this.spuAttrsMap['' + attr.attrId]) {
                                v = _this.spuAttrsMap['' + attr.attrId].attrValue.split(';')
                                if (v.length === 1) {
                                    v = v[0] + ''
                                }
                            }
                            attrArray.push({
                                attrId: attr.attrId,
                                attrName: attr.attrName,
                                attrValues: v,
                                showDesc: _this.spuAttrsMap['' + attr.attrId]
                                    ? _this.spuAttrsMap['' + attr.attrId].quickShow
                                    : attr.showDesc
                            })
                        })
                        this.dataResp.baseAttrs.push(attrArray)
                    })
                    this.dataResp.attrGroups = data.data
                })
            },
            submitSpuAttrs () {
                // spu_id  attr_id  attr_name   attr_value    attr_sort  quick_show
                let submitData = []
                this.dataResp.baseAttrs.forEach(item => {
                    item.forEach(attr => {
                        let val = ''
                        if (attr.attrValues instanceof Array) {
                            val = attr.attrValues.join(';')
                        } else {
                            val = attr.attrValues
                        }

                        if (val !== '') {
                            submitData.push({
                                attrId: attr.attrId,
                                attrName: attr.attrName,
                                attrValue: val,
                                quickShow: attr.showDesc
                            })
                        }
                    })
                })

                this.$confirm('Do you want to update product specification?', 'Warning', {
                    confirmButtonText: 'Confirm',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                })
                    .then(() => {
                        this.$http({
                            url: this.$http.adornUrl(`/product/attr/update/${this.spuId}`),
                            method: 'post',
                            data: this.$http.adornData(submitData, false)
                        }).then(({ data }) => {
                            this.$message.success('Update Successfully')
                        })
                    })
                    .catch((e) => {
                        this.$message.info('Update Canceled')
                    })
            }
        },
        created () { },
        activated () {
            this.clearData()
            this.getQueryParams()
            if (this.spuId && this.catalogId) {
                this.showBaseAttrs()
                this.getSpuBaseAttrs()
            }
        }
    }
</script>
<style scoped>
</style>