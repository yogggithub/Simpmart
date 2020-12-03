<template>
    <div>
        <el-select clearable filterable placeholder="Select" v-model="brandId">
            <el-option
                :key="item.brandId"
                :label="item.brandName"
                :value="item.brandId"
                v-for="item in brands"
            ></el-option>
        </el-select>
    </div>
</template>

<script>
    export default {
        components: {},
        props: {},
        data () {
            return {
                catId: 0,
                brands: [
                    {
                        label: 'a',
                        value: 1
                    }
                ],
                brandId: '',
                subscribe: null
            }
        },
        computed: {},
        watch: {
            brandId (val) {
                this.PubSub.publish('brandId', val)
            }
        },
        methods: {
            getCatBrands () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/categorybrandrelation/brand/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        catId: this.catId
                    })
                }).then(({ data }) => {
                    this.brands = data.data
                })
            }
        },
        mounted () {
            // listen the change of categories
            this.subscribe = this.PubSub.subscribe('catPath', (msg, val) => {
                this.catId = val[val.length - 1]
                this.getCatBrands()
            })
        },
        beforeDestroy () {
            this.PubSub.unsubscribe(this.subscribe)
        }
    }
</script>
<style scoped>
</style>