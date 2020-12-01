<template>
    <div>
        <el-row>
            <el-switch active-text="Drag to Sort" inactive-text="Cannot Drag" v-model="draggable"></el-switch>
            <!-- Using ElementUI tree structrue to display tree-level menu -->
            <el-button @click="batchUpdate" type="primary" v-if="draggable">Save Sort</el-button>
            <el-button @click="batchDelete" type="danger">Batch Delete</el-button>
        </el-row>
        <el-tree
            :allow-drop="allowDrop"
            :data="menus"
            :default-expanded-keys="expandedKeys"
            :draggable="draggable"
            :expand-on-click-node="false"
            :props="defaultProps"
            @node-drop="handleDrop"
            accordion
            node-key="catId"
            ref="categoryTree"
            show-checkbox
        >
            <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                    <el-button @click="() => edit(data)" size="mini" type="text">Edit</el-button>
                    <el-button
                        @click="() => append(data)"
                        size="mini"
                        type="text"
                        v-if="node.level <= 2"
                    >Append</el-button>
                    <el-button
                        @click="() => remove(node, data)"
                        size="mini"
                        type="text"
                        v-if="node.childNodes.length == 0"
                    >Delete</el-button>
                </span>
            </span>
        </el-tree>

        <!-- ElementUI Dialog -->
        <el-dialog :title="dialog.title" :visible.sync="dialog.visible" width="30%">
            <el-form :model="category">
                <el-form-item label="Category Name">
                    <el-input autocomplete="off" v-model="category.name"></el-input>
                </el-form-item>
                <el-form-item label="Category Icon">
                    <el-input autocomplete="off" v-model="category.icon"></el-input>
                </el-form-item>
                <el-form-item label="Product Unit">
                    <el-input autocomplete="off" v-model="category.prodcut_unit"></el-input>
                </el-form-item>
            </el-form>
            <span class="dialog-footer" slot="footer">
                <el-button @click="dialog.visible = false">Cancel</el-button>
                <el-button @click="submitDialog" type="primary">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    // Import other file, like components, third party js, json, pic, ect.
    // for example: import file-name from 'file-location'

    export default {
        // Any component been imported have to bind with Vue object before using
        components: {},
        props: {},
        data () {
            // Store data
            return {
                category: {
                    catId: null,
                    name: '',
                    parentCid: 0,
                    catLevel: 1,
                    showStatus: 1,
                    sort: 0,
                    icon: '',
                    product_unit: ''
                },
                menus: [],
                expandedKeys: [],
                dialog: {
                    title: '',
                    // Used to distinguish whether a request is a modification or an addition
                    // 0 for modification, 1 for addition
                    type: '',
                    visible: false   // by default, dialog cannot be visible
                },
                draggable: false,
                maxLevel: 1,    // defaul value for node depth
                maxAllowedLevel: 3, // allowed maximum layers of category, original is 3
                updateNodes: [],
                pCid: [],
                defaultProps: {
                    // children attribute of nodes
                    children: 'childrenCategory',
                    // the attribute that display on the page
                    label: 'name'
                }
            }
        },
        // Computed properties
        computed: {},
        // Monitor changes in data
        watch: {},
        // Methods
        methods: {
            // fetch all the categories
            getCategories () {
                this.$http({
                    // send request to controller in order to get categories tree list
                    url: this.$http.adornUrl('/commodity/category/list/tree'),
                    method: 'get'
                    // THEN contains action to be done after request send successfully
                }).then(({ data }) => {
                    this.menus = data.data
                })
            },

            // Adjust the Dialog display content according to the clicked button.
            submitDialog () {
                if (this.dialog.type === 0) {
                    this.editCategory()
                } else if (this.dialog.type === 1) {
                    this.addCategory()
                }
            },

            edit (data) {
                this.dialog.title = 'Edit Category'
                this.dialog.visible = true
                this.dialog.type = 0

                // dynamic get current category info before display on page
                this.$http({
                    url: this.$http.adornUrl(`/commodity/category/info/${data.catId}`),
                    method: 'get'
                }).then(({ data }) => {
                    // the server response contains a object named 'category'
                    // which is define in auto-generated CategoryController.class
                    this.category.catId = data.category.catId
                    this.category.name = data.category.name
                    this.category.icon = data.category.icon
                    this.category.product_unit = data.category.product_unit
                    this.category.parentCid = data.category.parentCid
                })
            },

            // click the append button to make the Dialog visible.
            append (data) {
                this.dialog.title = 'New Category'
                this.dialog.visible = true
                this.dialog.type = 1
                // must set back to null
                this.category.catId = null
                this.category.name = ''
                this.category.icon = ''
                this.category.product_unit = ''
                // get information from clicked category node
                // and pass to new category
                this.category.parentCid = data.catId
                // make sure the catLevel is numerical then increment 1
                this.category.catLevel = data.catLevel * 1 + 1
            },

            editCategory () {
                // create a temporate category object
                // hiden those attribute that do not need to sumbit
                // NOTE: every attribute must be exactly the same as defined in CategoryEntity.class
                let { catId, name, icon, productUnit } = this.category

                this.$http({
                    url: this.$http.adornUrl('/commodity/category/update'),
                    method: 'post',
                    data: this.$http.adornData({ catId, name, icon, productUnit }, false)
                }).then(({ data }) => {
                    this.dialog.visible = false
                    this.expandedKeys = [
                        this.category.parentCid
                    ]
                    this.getCategories()
                    this.$message.success('Category modified successfully')
                })
            },

            // click 'append' button, add category as current category's child
            addCategory () {
                this.$http({
                    url: this.$http.adornUrl('/commodity/category/save'),
                    method: 'post',
                    data: this.$http.adornData(this.category, false)
                }).then(({ data }) => {
                    this.dialog.visible = false
                    this.expandedKeys = [
                        this.category.parentCid
                    ]
                    this.getCategories()
                    this.$message.success('Category Added')
                })
            },

            remove (node, data) {
                // an array that store the catId of all categories
                let ids = [data.catId]
                this.$confirm(`Do you want to delete ${data.name}?`, 'Delete', {
                    confirmButtonText: 'Delete', // click confirm button go to then()
                    cancelButtonText: 'Cancel', // click cancel button go to catch()
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        // adornUrl() is util method that defined in src/util/httpRequest.js
                        url: this.$http.adornUrl('/commodity/category/delete'),
                        method: 'post',
                        data: this.$http.adornData(ids, false)
                    }).then(({ data }) => {
                        this.$message.success('Delete successfully!')
                        // After deleting a menu
                        // let the parent menu expand automatically when loaded
                        this.expandedKeys = [
                            node.parent.data.catId
                        ]
                        this.getCategories()
                    })
                }).catch(() => {
                    this.$message.error('Action canceled')
                })
            },

            // Determine if a category can be dropped to a node.
            // draggingNode: the node that being dragging with mouse
            // dropNode: the destination node
            // type: the type of destination postion, could be prev, next, inner
            allowDrop (draggingNode, dropNode, type) {
                // rule: after drop, the total depth must not greate than 3
                // 1. get the max category level of the dragging node
                this.nodeMaxLevel(draggingNode)
                // 2. calculate the depth of dragging node
                let depth = this.maxLevel - draggingNode.level + 1
                // 3. situational Determination
                if (type === 'inner') {
                    // when drop into a category, the total depth of destination and dragging
                    // category must less than 3
                    return depth + dropNode.level <= this.maxAllowedLevel
                } else {
                    // otherwise, i.e. prev and next, the total depth of destination and dragging
                    // category must less than 4, because in this case,
                    // the dragging category is actually added to the parent of destination
                    return depth + dropNode.parent.level <= this.maxAllowedLevel
                }
            },

            // find the max category level of a node and its children categoories
            nodeMaxLevel (node) {
                if (node.childNodes != null && node.childNodes.length > 0) {
                    for (let i = 0; i < node.childNodes.length; i++) {
                        if (node.childNodes[i].level > this.maxLevel) {
                            this.maxLevel = node.childNodes[i].level
                        }
                        this.nodeMaxLevel(node.childNodes[i])
                    }
                } else {
                    this.maxLevel = node.level
                }
            },

            // update catgory after drag and drop
            handleDrop (draggingNode, dropNode, type, event) {
                let pCid = 0
                let siblingNodes = null

                // 1. update information of dragging node
                if (type === 'inner') {
                    pCid = dropNode.data.catId
                    siblingNodes = dropNode.childNodes
                } else {
                    pCid = dropNode.parent.data.catId === undefined
                        ? 0 : dropNode.parent.data.catId
                    siblingNodes = dropNode.parent.childNodes
                }
                this.pCid.push(pCid)

                // 2. iterate through sibling nodes, and sort based on new position
                // if current element is dragging node, update the parent node incidentally
                for (let i = 0; i < siblingNodes.length; i++) {
                    if (siblingNodes[i].data.catId === draggingNode.data.catId) {
                        // 3. update new level if dragging node's level has changed
                        if (siblingNodes[i].level !== draggingNode.level) {
                            draggingNode.level = siblingNodes[i].level
                            // update category level of dragging node's children nodes
                            this.updateChildNode(siblingNodes[i])
                        }
                        this.updateNodes.push({
                            catId: siblingNodes[i].data.catId,
                            sort: i,
                            parentCid: pCid,
                            catLevel: draggingNode.level
                        })
                    } else {
                        this.updateNodes.push({
                            catId: siblingNodes[i].data.catId,
                            sort: i
                        })
                    }
                }
                this.maxLevel = 1
            },

            // recursively update level of children nodes
            updateChildNode (node) {
                for (let i = 0; i < node.childNodes.length; i++) {
                    let cNode = node.childNodes[i].data
                    this.updateNodes.push({
                        catId: cNode.catId,
                        catLevel: node.childNodes[i].level
                    })
                    if (node.childNodes[i].childNodes.length > 0) {
                        this.updateChildNode(node.childNodes[i])
                    }
                }
            },

            batchUpdate () {
                // 4.data persistence
                this.$http({
                    url: this.$http.adornUrl('/commodity/category/update/sort'),
                    method: 'post',
                    data: this.$http.adornData(this.updateNodes, false)
                }).then(({ data }) => {
                    this.$message.success('Drag successfully')
                    // refresh tree, and clear temporate data used in the process
                    this.updateNodes = []
                    this.maxLevel = 1
                    this.expandedKeys = this.pCid
                    this.getCategories()
                }).catch(() => {
                    this.getCategories()
                    this.updateNodes = []
                    this.maxLevel = 1
                    this.$message.error('Can not drag and drop there')
                })
            },

            batchDelete () {
                let deleteCatIds = []
                this.$refs.categoryTree.getCheckedNodes()
                    .forEach(element => {
                        deleteCatIds.push(element.catId)
                    })
                // message box to confirm before send data to server
                this.$confirm(`Do you want to delete selected ${deleteCatIds.length} ${deleteCatIds.length > 1 ? 'categories' : 'category'}?`, 'Batch Delete', {
                    confirmButtonText: 'Delete', // click confirm button go to then()
                    cancelButtonText: 'Cancel', // click cancel button go to catch()
                    type: 'warning'
                }).then(() => {
                    this.$http({
                        // adornUrl() is util method that defined in src/util/httpRequest.js
                        url: this.$http.adornUrl('/commodity/category/delete'),
                        method: 'post',
                        data: this.$http.adornData(deleteCatIds, false)
                    }).then(({ data }) => {
                        this.$message.success('Delete successfully!')
                        this.getCategories()
                    })
                }).catch(() => {
                    this.$message.error('Action canceled')
                })
            }
        },
        // Life cycle - Object been created (can access instance by this keyword)
        created () {
            this.getCategories()
        }
        // Life cycle - Elements been mounted (can access DOM elements)
        // mounted () {},
        // beforeCreate () {}, // Life cycle - Before object been created
        // beforeMount () {}, // Life cycle - Before DOM mount
        // beforeUpdate () {}, // Life cycle - Before update
        // updated () {}, // Life cycle - After updated
        // beforeDestroy () {}, // Life cycle - Before destory
        // destroyed () {}, // Life cycle - Destory
        // activated () {} // Triggered when the page has a keep-alive cache
    }
</script>

<style scoped></style>
