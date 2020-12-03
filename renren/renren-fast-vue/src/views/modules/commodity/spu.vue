<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-form :inline="true" :model="dataForm">
                    <el-form-item label="category">
                        <category-cascader :catalogPath.sync="catalogPath"></category-cascader>
                    </el-form-item>
                    <el-form-item label="brand">
                        <brand-select style="width:160px"></brand-select>
                    </el-form-item>
                    <el-form-item label="status">
                        <el-select
                            clearable
                            placeholder=" Select"
                            style="width:160px"
                            v-model="dataForm.status"
                        >
                            <el-option :value="0" label="new created"></el-option>
                            <el-option :value="1" label="available"></el-option>
                            <el-option :value="2" label="unavailable"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="search">
                        <el-input clearable style="width:160px" v-model="dataForm.key"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="searchSpuInfo" type="primary">Query</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
            <el-col :span="24">
                <spuinfo :catId="catId"></spuinfo>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import CategoryCascader from '../common/category-cascader'
    import BrandSelect from '../common/brand-select'
    import Spuinfo from './spuinfo'
    export default {
        components: { CategoryCascader, Spuinfo, BrandSelect },
        props: {},
        data () {
            return {
                catId: 0,
                catalogPath: [],
                dataForm: {
                    status: '',
                    key: '',
                    brandId: 0,
                    catalogId: 0
                },
                catPathSub: null,
                brandIdSub: null

            }
        },
        methods: {
            searchSpuInfo () {
                this.PubSub.publish('dataForm', this.dataForm)
            }
        },
        created () { },
        mounted () {
            this.catPathSub = this.PubSub.subscribe('catPath', (msg, val) => {
                this.dataForm.catalogId = val[val.length - 1]
            })
            this.brandIdSub = this.PubSub.subscribe('brandId', (msg, val) => {
                this.dataForm.brandId = val
            })
        },
        beforeDestroy () {
            this.PubSub.unsubscribe(this.catPathSub)
            this.PubSub.unsubscribe(this.brandIdSub)
        }
    }
</script>

<style scoped>
</style>