<template>
    <div>
        <el-input placeholder="Enter keywords to filter" v-model="filterText"></el-input>
        <el-tree
            :data="menus"
            :filter-node-method="filterNode"
            :highlight-current="true"
            :props="defaultProps"
            @node-click="nodeclick"
            node-key="catId"
            ref="menuTree"
        ></el-tree>
    </div>
</template>

<script>
    export default {
        components: {},
        props: {},
        data () {
            return {
                filterText: '',
                menus: [],
                expandedKey: [],
                defaultProps: {
                    children: 'childrenCategory',
                    label: 'name'
                }
            }
        },
        watch: {
            filterText (val) {
                this.$refs.menuTree.filter(val)
            }
        },
        methods: {
            // tree node filter
            filterNode (value, data) {
                if (!value) return true
                return data.name.indexOf(value) !== -1
            },
            getMenus () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/category/list/tree'),
                    method: 'get'
                }).then(({ data }) => {
                    this.menus = data.data
                })
            },

            /**
             * passing Data between parent and child Components
             * 1. from child to parent: send an event which contains data
             * 2.
             */
            nodeclick (data, node, component) {
                this.$emit('tree-node-click', data, node, component)
            }
        },
        created () {
            this.getMenus()
        }
    }
</script>
<style scoped>
</style>