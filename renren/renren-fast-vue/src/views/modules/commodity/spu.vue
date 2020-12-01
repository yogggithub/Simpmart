<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-form :inline="true" :model="dataForm">
                    <el-form-item label="分类">
                        <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
                    </el-form-item>
                    <el-form-item label="品牌">
                        <brand-select style="width:160px"></brand-select>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select clearable style="width:160px" v-model="dataForm.status">
                            <el-option :value="0" label="新建"></el-option>
                            <el-option :value="1" label="上架"></el-option>
                            <el-option :value="2" label="下架"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="检索">
                        <el-input clearable style="width:160px" v-model="dataForm.key"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="searchSpuInfo" type="primary">查询</el-button>
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
        // import引入的组件需要注入到对象中才能使用
        components: { CategoryCascader, Spuinfo, BrandSelect },
        props: {},
        data () {
            // 这里存放数据
            return {
                catId: 0,
                catelogPath: [],
                dataForm: {
                    status: '',
                    key: '',
                    brandId: 0,
                    catelogId: 0
                },
                catPathSub: null,
                brandIdSub: null

            }
        },
        computed: {},
        // 监控data中的数据变化
        watch: {},
        // 方法集合
        methods: {
            searchSpuInfo () {
                this.PubSub.publish('dataForm', this.dataForm)
            }
        },
        created () { },
        mounted () {
            this.catPathSub = this.PubSub.subscribe('catPath', (msg, val) => {
                this.dataForm.catelogId = val[val.length - 1]
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