<template>
    <div>
        <el-cascader
            :options="categories"
            :props="setting"
            clearable
            filterable
            placeholder="Try to search: ex. mobile"
            v-model="paths"
        ></el-cascader>
    </div>
</template>

<script>
    export default {
        components: {},
        props: {
            catalogPath: {
                type: Array,
                default () {
                    return []
                }
            }
        },
        data () {
            return {
                setting: {
                    value: 'catId',
                    label: 'name',
                    children: 'childrenCategory'
                },
                categories: [],
                paths: this.catalogPath
            }
        },
        watch: {
            catalogPath (v) {
                this.paths = this.catalogPath
            },
            paths (v) {
                this.$emit('update:catalogPath', v)
                this.PubSub.publish('catPath', v)
            }
        },
        methods: {
            getCategories () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/category/list/tree'),
                    method: 'get'
                }).then(({ data }) => {
                    this.categories = data.data
                })
            }
        },
        created () {
            this.getCategories()
        }
    }
</script>
<style scoped>
</style>