<template>
    <div>
        <el-row>
            <el-col :span="24">
                <el-steps :active="step" finish-status="success">
                    <el-step title="General Info"></el-step>
                    <el-step title="Specifications"></el-step>
                    <el-step title="Sales Properties"></el-step>
                    <el-step title="SKU"></el-step>
                    <el-step title="Save"></el-step>
                </el-steps>
            </el-col>
            <el-col :span="24" v-show="step==0">
                <el-card class="box-card" style="width:80%;margin:20px auto">
                    <el-form
                        :model="spu"
                        :rules="spuBaseInfoRules"
                        label-width="120px"
                        ref="spuBaseForm"
                    >
                        <el-form-item label="name" prop="spuName">
                            <el-input v-model="spu.spuName"></el-input>
                        </el-form-item>
                        <el-form-item label="Description" prop="spuDescription">
                            <el-input v-model="spu.spuDescription"></el-input>
                        </el-form-item>
                        <el-form-item label="catalog" prop="catalogId">
                            <category-cascader></category-cascader>
                        </el-form-item>
                        <el-form-item label="brand" prop="brandId">
                            <brand-select></brand-select>
                        </el-form-item>
                        <el-form-item label="weight(Kg)" prop="weight">
                            <el-input-number
                                :min="0"
                                :precision="3"
                                :step="0.1"
                                v-model.number="spu.weight"
                            ></el-input-number>
                        </el-form-item>
                        <el-form-item label="rewards" prop="bounds">
                            <label>rewards</label>
                            <el-input-number
                                :min="0"
                                controls-position="right"
                                placeholder="rewards"
                                style="width:130px"
                                v-model="spu.bounds.buyBounds"
                            ></el-input-number>
                            <label style="margin-left:15px">growth value</label>
                            <el-input-number
                                :min="0"
                                controls-position="right"
                                placeholder="growth value"
                                style="width:130px"
                                v-model="spu.bounds.growBounds"
                            >
                                <template slot="prepend">growth value</template>
                            </el-input-number>
                        </el-form-item>
                        <el-form-item label="description images" prop="description">
                            <multi-upload v-model="spu.description"></multi-upload>
                        </el-form-item>

                        <el-form-item label="spu images" prop="images">
                            <multi-upload v-model="spu.images"></multi-upload>
                        </el-form-item>
                        <el-form-item>
                            <el-button
                                @click="collectSpuBaseInfo"
                                type="success"
                            >Next：Specifications</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :span="24" v-show="step==1">
                <el-card class="box-card" style="width:80%;margin:20px auto">
                    <el-tabs style="width:98%" tab-position="left">
                        <el-tab-pane
                            :key="group.attrGroupId"
                            :label="group.attrGroupName"
                            v-for="(group,gidx) in dataResp.attrGroups"
                        >
                            <el-form :model="spu" ref="form">
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
                                        placeholder="input or select"
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
                                    >quick show</el-checkbox>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                    <div style="margin:auto">
                        <el-button @click="step = 0" type="primary">Previous</el-button>
                        <el-button @click="generateSaleAttrs" type="success">Next：Sale Properties</el-button>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="24" v-show="step==2">
                <el-card class="box-card" style="width:80%;margin:20px auto">
                    <el-card class="box-card">
                        <div class="clearfix" slot="header">
                            <span>Select</span>
                            <el-form :model="spu" ref="saleform">
                                <el-form-item
                                    :key="attr.attrId"
                                    :label="attr.attrName"
                                    v-for="(attr,aidx) in dataResp.saleAttrs"
                                >
                                    <el-input
                                        type="hidden"
                                        v-model="dataResp.tempSaleAttrs[aidx].attrId"
                                        v-show="false"
                                    ></el-input>
                                    <el-checkbox-group
                                        v-model="dataResp.tempSaleAttrs[aidx].attrValues"
                                    >
                                        <div v-if="dataResp.saleAttrs[aidx].valueSelect != ''">
                                            <el-checkbox
                                                :key="val"
                                                :label="val"
                                                v-for="val in dataResp.saleAttrs[aidx].valueSelect.split(';')"
                                            ></el-checkbox>
                                        </div>
                                        <div style="margin-left:20px;display:inline">
                                            <el-button
                                                @click="showInput(aidx)"
                                                class="button-new-tag"
                                                size="mini"
                                                v-show="!inputVisible[aidx].view"
                                            >+Customize</el-button>
                                            <el-input
                                                :ref="'saveTagInput'+aidx"
                                                @blur="handleInputConfirm(aidx)"
                                                @keyup.enter.native="handleInputConfirm(aidx)"
                                                size="mini"
                                                style="width:150px"
                                                v-model="inputValue[aidx].val"
                                                v-show="inputVisible[aidx].view"
                                            ></el-input>
                                        </div>
                                    </el-checkbox-group>
                                </el-form-item>
                            </el-form>
                        </div>
                        <el-button @click="step = 1" type="primary">Previous</el-button>
                        <el-button @click="generateSkus" type="success">Next：SKU</el-button>
                    </el-card>
                </el-card>
            </el-col>
            <el-col :span="24" v-show="step==3">
                <el-card class="box-card" style="width:80%;margin:20px auto">
                    <el-table :data="spu.skus" style="width: 100%">
                        <el-table-column label="attribute portfolio">
                            <el-table-column
                                :key="item.attrId"
                                :label="item.attrName"
                                v-for="(item,index) in dataResp.tableAttrColumn"
                            >
                                <template slot-scope="scope">
                                    <span
                                        style="margin-left: 10px"
                                    >{{ scope.row.attr[index].attrValue }}</span>
                                </template>
                            </el-table-column>
                        </el-table-column>
                        <el-table-column label="sku name" prop="skuName">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.skuName"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="title" prop="skuTitle">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.skuTitle"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="subTitle" prop="skuSubtitle">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.skuSubtitle"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="price" prop="price">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.price"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column type="expand">
                            <template slot-scope="scope">
                                <el-row>
                                    <el-col :span="24">
                                        <label style="display:block;float:left">select images OR</label>
                                        <multi-upload
                                            :list-type="'text'"
                                            :show-file-list="false"
                                            :showFile="false"
                                            style="float:left; margin-left:10px;"
                                            v-model="uploadImages"
                                        ></multi-upload>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-divider></el-divider>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-card
                                            :body-style="{ padding: '0px' }"
                                            :key="index"
                                            style="width:170px;float:left;margin-left:15px;margin-top:15px;"
                                            v-for="(img,index) in spu.images"
                                        >
                                            <img :src="img" style="width:160px;height:120px" />
                                            <div style="padding: 14px;">
                                                <el-row>
                                                    <el-col :span="12">
                                                        <el-checkbox
                                                            :true-label="img"
                                                            false-label
                                                            v-model="scope.row.images[index].imgUrl"
                                                        ></el-checkbox>
                                                    </el-col>
                                                    <el-col :span="12">
                                                        <el-tag
                                                            v-if="scope.row.images[index].defaultImg == 1"
                                                        >
                                                            <input
                                                                :name="scope.row.skuName"
                                                                @change="checkDefaultImg(scope.row,index,img)"
                                                                checked
                                                                type="radio"
                                                            />Set as default
                                                        </el-tag>
                                                        <el-tag v-else>
                                                            <input
                                                                :name="scope.row.skuName"
                                                                @change="checkDefaultImg(scope.row,index,img)"
                                                                type="radio"
                                                            />Set as default
                                                        </el-tag>
                                                    </el-col>
                                                </el-row>
                                            </div>
                                        </el-card>
                                    </el-col>
                                </el-row>
                                <!-- discount, member price -->
                                <el-form :model="scope.row">
                                    <el-row>
                                        <el-col :span="24">
                                            <el-form-item label="set discount">
                                                <label>buy</label>
                                                <el-input-number
                                                    :min="0"
                                                    controls-position="right"
                                                    style="width:160px"
                                                    v-model="scope.row.fullCount"
                                                ></el-input-number>
                                                <label>count</label>

                                                <label style="margin-left:15px;">get</label>
                                                <el-input-number
                                                    :max="1"
                                                    :min="0"
                                                    :precision="2"
                                                    :step="0.01"
                                                    controls-position="right"
                                                    style="width:160px"
                                                    v-model="scope.row.discount"
                                                ></el-input-number>
                                                <label>off</label>
                                                <el-checkbox
                                                    :false-label="0"
                                                    :true-label="1"
                                                    v-model="scope.row.countStatus"
                                                >Use with other discout</el-checkbox>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="24">
                                            <el-form-item label="set reduction">
                                                <label>pay</label>
                                                <el-input-number
                                                    :min="0"
                                                    :step="100"
                                                    controls-position="right"
                                                    style="width:160px"
                                                    v-model="scope.row.fullPrice"
                                                ></el-input-number>
                                                <label>dollar</label>
                                                <label style="margin-left:15px;">save</label>
                                                <el-input-number
                                                    :min="0"
                                                    :step="10"
                                                    controls-position="right"
                                                    style="width:160px"
                                                    v-model="scope.row.reducePrice"
                                                ></el-input-number>
                                                <label>dollar</label>
                                                <el-checkbox
                                                    :false-label="0"
                                                    :true-label="1"
                                                    v-model="scope.row.priceStatus"
                                                >Use with other discout</el-checkbox>
                                            </el-form-item>
                                        </el-col>

                                        <el-col :span="24">
                                            <el-form-item
                                                label="set member price"
                                                v-if="scope.row.memberPrice.length>0"
                                            >
                                                <br />
                                                <!--   @change="handlePriceChange(scope,mpidx,$event)" -->
                                                <el-form-item
                                                    :key="mp.id"
                                                    v-for="(mp,mpidx) in scope.row.memberPrice"
                                                >
                                                    {{mp.name}}
                                                    <el-input-number
                                                        :min="0"
                                                        :precision="2"
                                                        controls-position="right"
                                                        style="width:160px"
                                                        v-model="scope.row.memberPrice[mpidx].price"
                                                    ></el-input-number>
                                                </el-form-item>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button @click="step = 2" type="primary">Previous</el-button>
                    <el-button @click="submitSkus" type="success">Next：Save</el-button>
                </el-card>
            </el-col>
            <el-col :span="24" v-show="step==4">
                <el-card class="box-card" style="width:80%;margin:20px auto">
                    <h1>Successful</h1>
                    <el-button @click="addAgian" type="primary">Continue to add another</el-button>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import CategoryCascader from '../common/category-cascader'
    import BrandSelect from '../common/brand-select'
    import MultiUpload from '@/components/upload/multiUpload'
    export default {
        components: { CategoryCascader, BrandSelect, MultiUpload },
        props: {},
        data () {
            return {
                catPathSub: null,
                brandIdSub: null,
                uploadDialogVisible: false,
                uploadImages: [],
                step: 0,
                // spu_name  spu_description  catalog_id  brand_id  weight  publish_status
                spu: {
                    spuName: '',
                    spuDescription: '',
                    catalogId: 0,
                    brandId: '',
                    weight: '',
                    publishStatus: 0,
                    description: [], // images used in product detailed description
                    images: [], // Photo Gallery
                    bounds: {
                        // reward points
                        buyBounds: 0,
                        growBounds: 0
                    },
                    baseAttrs: [], // basic attributes
                    skus: [] // all sku info
                },
                spuBaseInfoRules: {
                    spuName: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    spuDescription: [
                        { required: true, message: 'Can not be emptye', trigger: 'blur' }
                    ],
                    catalogId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    brandId: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    description: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    images: [
                        { required: true, message: 'Can not be empty', trigger: 'blur' }
                    ],
                    weight: [
                        {
                            type: 'number',
                            required: false,
                            message: 'Must input a number',
                            trigger: 'blur'
                        }
                    ]
                },
                dataResp: {
                    attrGroups: [],
                    baseAttrs: [],
                    saleAttrs: [],
                    tempSaleAttrs: [],
                    tableAttrColumn: [],
                    memberLevels: [],
                    steped: [false, false, false, false, false]
                },
                inputVisible: [],
                inputValue: []
            }
        },
        computed: {},
        watch: {
            uploadImages (val) {
                // entend imgs in each skus
                let imgArr = Array.from(new Set(this.spu.images.concat(val)))

                // concat imgs in order to save all images info
                this.spu.skus.forEach((item, index) => {
                    let len = imgArr.length - this.spu.skus[index].images.length
                    if (len > 0) {
                        let imgs = new Array(len)
                        imgs = imgs.fill({ imgUrl: '', defaultImg: 0 })
                        this.spu.skus[index].images = item.images.concat(imgs)
                    }
                })
                this.spu.images = imgArr
            }
        },
        methods: {
            addAgian () {
                this.step = 0
                this.resetSpuData()
            },
            resetSpuData () {
                this.spu = {
                    spuName: '',
                    spuDescription: '',
                    catalogId: 0,
                    brandId: '',
                    weight: '',
                    publishStatus: 0,
                    description: [],
                    images: [],
                    bounds: {
                        buyBounds: 0,
                        growBounds: 0
                    },
                    baseAttrs: [],
                    skus: []
                }
            },
            handlePriceChange (scope, mpidx, e) {
                this.spu.skus[scope.$index].memberPrice[mpidx].price = e
            },
            getMemberLevels () {
                this.$http({
                    url: this.$http.adornUrl('/member/memberlevel/list'),
                    method: 'get',
                    params: this.$http.adornParams({
                        page: 1,
                        limit: 500
                    })
                })
                    .then(({ data }) => {
                        this.dataResp.memberLevels = data.page.list
                    })
                    .catch(e => {
                    })
            },
            showInput (idx) {
                this.inputVisible[idx].view = true
                // this.$refs['saveTagInput'+idx].$refs.input.focus();
            },
            checkDefaultImg (row, index, img) {
                // selected image as default
                row.images[index].imgUrl = img
                row.images[index].defaultImg = 1
                // update other images' status
                row.images.forEach((item, idx) => {
                    if (idx !== index) {
                        row.images[idx].defaultImg = 0
                    }
                })
            },
            handleInputConfirm (idx) {
                let inputValue = this.inputValue[idx].val
                if (inputValue) {
                    if (this.dataResp.saleAttrs[idx].valueSelect === '') {
                        this.dataResp.saleAttrs[idx].valueSelect = inputValue
                    } else {
                        this.dataResp.saleAttrs[idx].valueSelect += ';' + inputValue
                    }
                }
                this.inputVisible[idx].view = false
                this.inputValue[idx].val = ''
            },
            collectSpuBaseInfo () {
                // spuBaseForm
                this.$refs.spuBaseForm.validate(valid => {
                    if (valid) {
                        this.step = 1
                        this.showBaseAttrs()
                    } else {
                        return false
                    }
                })
            },
            generateSaleAttrs () {
                // bind all attributes to spu
                this.spu.baseAttrs = []
                this.dataResp.baseAttrs.forEach(item => {
                    item.forEach(attr => {
                        let { attrId, attrValues, showDesc } = attr
                        // skip empty attributes
                        if (attrValues !== '') {
                            if (attrValues instanceof Array) {
                                // multiple value seperated by ;
                                attrValues = attrValues.join(';')
                            }
                            this.spu.baseAttrs.push({ attrId, attrValues, showDesc })
                        }
                    })
                })
                console.log('baseAttrs', this.spu.baseAttrs)
                this.step = 2
                this.getShowSaleAttr()
            },
            generateSkus () {
                this.step = 3

                // Generation of sku from Cartesian Product operations
                let selectValues = []
                this.dataResp.tableAttrColumn = []
                this.dataResp.tempSaleAttrs.forEach(item => {
                    if (item.attrValues.length > 0) {
                        selectValues.push(item.attrValues)
                        this.dataResp.tableAttrColumn.push(item)
                    }
                })

                let descartes = this.descartes(selectValues)
                console.log('Generated product portfolio', JSON.stringify(descartes))

                let skus = []

                descartes.forEach((descar, descaridx) => {
                    let attrArray = []
                    descar.forEach((de, index) => {
                        let saleAttrItem = {
                            attrId: this.dataResp.tableAttrColumn[index].attrId,
                            attrName: this.dataResp.tableAttrColumn[index].attrName,
                            attrValue: de
                        }
                        attrArray.push(saleAttrItem)
                    })
                    let imgs = []
                    this.spu.images.forEach((img, idx) => {
                        imgs.push({ imgUrl: '', defaultImg: 0 })
                    })

                    // member special price, bind to every sku
                    let memberPrices = []
                    if (this.dataResp.memberLevels.length > 0) {
                        for (let i = 0; i < this.dataResp.memberLevels.length; i++) {
                            if (this.dataResp.memberLevels[i].priviledgeMemberPrice === 1) {
                                memberPrices.push({
                                    id: this.dataResp.memberLevels[i].id,
                                    name: this.dataResp.memberLevels[i].name,
                                    price: 0
                                })
                            }
                        }
                    }

                    let res = this.hasAndReturnSku(this.spu.skus, descar)
                    if (res === null) {
                        skus.push({
                            attr: attrArray,
                            skuName: this.spu.spuName + ' ' + descar.join(' '),
                            price: 0,
                            skuTitle: this.spu.spuName + ' ' + descar.join(' '),
                            skuSubtitle: '',
                            images: imgs,
                            descar: descar,
                            fullCount: 0,
                            discount: 0,
                            countStatus: 0,
                            fullPrice: 0.0,
                            reducePrice: 0.0,
                            priceStatus: 0,
                            // eslint-disable-next-line no-array-constructor
                            memberPrice: new Array().concat(memberPrices)
                        })
                    } else {
                        skus.push(res)
                    }
                })
                this.spu.skus = skus
                console.log('结果!!!', this.spu.skus, this.dataResp.tableAttrColumn)
            },
            // return sku info if match some already exist sku
            hasAndReturnSku (skus, descar) {
                let res = null
                if (skus.length > 0) {
                    for (let i = 0; i < skus.length; i++) {
                        if (skus[i].descar.join(' ') === descar.join(' ')) {
                            res = skus[i]
                        }
                    }
                }
                return res
            },
            getShowSaleAttr () {
                if (!this.dataResp.steped[1]) {
                    this.$http({
                        url: this.$http.adornUrl(
                            `/commodity/attr/sale/list/${this.spu.catalogId}`
                        ),
                        method: 'get',
                        params: this.$http.adornParams({
                            page: 1,
                            limit: 500
                        })
                    }).then(({ data }) => {
                        this.dataResp.saleAttrs = data.page.list
                        data.page.list.forEach(item => {
                            this.dataResp.tempSaleAttrs.push({
                                attrId: item.attrId,
                                attrValues: [],
                                attrName: item.attrName
                            })
                            this.inputVisible.push({ view: false })
                            this.inputValue.push({ val: '' })
                        })
                        this.dataResp.steped[1] = true
                    })
                }
            },
            showBaseAttrs () {
                if (!this.dataResp.steped[0]) {
                    this.$http({
                        url: this.$http.adornUrl(
                            `/commodity/attrgroup/${this.spu.catalogId}/withattr`
                        ),
                        method: 'get',
                        params: this.$http.adornParams({})
                    }).then(({ data }) => {
                        data.data.forEach(item => {
                            let attrArray = []
                            item.attrs.forEach(attr => {
                                attrArray.push({
                                    attrId: attr.attrId,
                                    attrValues: '',
                                    showDesc: attr.showDesc
                                })
                            })
                            this.dataResp.baseAttrs.push(attrArray)
                        })
                        this.dataResp.steped[0] = 0
                        this.dataResp.attrGroups = data.data
                    })
                }
            },

            submitSkus () {
                // console.log('~~~~~', JSON.stringify(this.spu))
                this.$confirm('Submit product information would take a short time，continue?', 'Notice', {
                    confirmButtonText: 'Confirm',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                })
                    .then(() => {
                        this.$http({
                            url: this.$http.adornUrl('/commodity/spuinfo/save'),
                            method: 'post',
                            data: this.$http.adornData(this.spu, false)
                        }).then(({ data }) => {
                            if (data.code === 0) {
                                this.$message.success('Successful!')
                                this.step = 4
                            } else {
                                this.$message.error('Error: ' + data.msg + '')
                            }
                        })
                    })
                    .catch(e => {
                        console.log(e)
                        this.$message.info('Canceled')
                    })
            },
            // Cartesian product operations
            descartes (list) {
                var point = {}

                var result = []
                var pIndex = null
                var tempCount = 0
                var temp = []

                // generate pointer from the parameter
                for (var index in list) {
                    if (typeof list[index] === 'object') {
                        point[index] = { parent: pIndex, count: 0 }
                        pIndex = index
                    }
                }

                // directly return single-dimensional data
                if (pIndex == null) {
                    return list
                }

                // dynamic
                while (true) {
                    for (var i in list) {
                        tempCount = point[i]['count']
                        temp.push(list[i][tempCount])
                    }

                    result.push(temp)
                    temp = []

                    // check maximium value of pointer
                    while (true) {
                        if (point[index]['count'] + 1 >= list[index].length) {
                            point[index]['count'] = 0
                            pIndex = point[index]['parent']
                            if (pIndex == null) {
                                return result
                            }

                            index = pIndex
                        } else {
                            point[index]['count']++
                            break
                        }
                    }
                }
            }
        },
        created () { },
        mounted () {
            this.catPathSub = this.PubSub.subscribe('catPath', (msg, val) => {
                this.spu.catalogId = val[val.length - 1]
            })
            this.brandIdSub = this.PubSub.subscribe('brandId', (msg, val) => {
                this.spu.brandId = val
            })
            this.getMemberLevels()
        },
        beforeDestroy () {
            this.PubSub.unsubscribe(this.catPathSub)
            this.PubSub.unsubscribe(this.brandIdSub)
        }
    }
</script>
<style scoped>
</style>