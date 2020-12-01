<template>
    <div>
        <el-upload
            :before-upload="beforeUpload"
            :file-list="fileList"
            :http-request="uploadFile"
            :limit="maxCount"
            :list-type="'picture-card'"
            :multiple="true"
            :on-exceed="handleExceed"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :show-file-list="true"
            action="#"
        >
            <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :modal="false" :visible.sync="dialogVisible">
            <img :src="dialogImageUrl" alt width="100%" />
        </el-dialog>
    </div>
</template>
<script>
    import { authorize } from './authorize'
    export default {
        name: 'multiUpload',
        props: {
            // 图片属性数组
            value: Array,
            // 最大上传图片数量
            maxCount: {
                type: Number,
                default: 30
            }
        },
        data () {
            return {
                share_url: 'http://192.168.42.88:9000/simpmart/',
                pre_signed_url: '',
                dialogVisible: false,
                dialogImageUrl: null
            }
        },
        computed: {
            fileList () {
                let fileList = []
                for (let i = 0; i < this.value.length; i++) {
                    fileList.push({ url: this.value[i] })
                }

                return fileList
            }
        },
        methods: {
            emitInput (fileList) {
                let value = []
                for (let i = 0; i < fileList.length; i++) {
                    value.push(fileList[i].url)
                }
                this.$emit('input', value)
            },
            handleRemove (file, fileList) {
                this.emitInput(fileList)
            },
            handlePreview (file) {
                this.dialogVisible = true
                this.dialogImageUrl = file.url
            },
            beforeUpload (file) {
                return new Promise((resolve, reject) => {
                    let objectName = file.uid + '_' + file.name
                    authorize(objectName).then(response => {
                        this.pre_signed_url = response.data
                        resolve(true)
                        // eslint-disable-next-line handle-callback-err
                    }).catch(err => {
                        // ignore reject parameter hint
                        // eslint-disable-next-line prefer-promise-reject-errors
                        reject(false)
                    })
                })
            },
            handleExceed (files, fileList) {
                this.$message({
                    message: 'Upload limited to ' + this.maxCount + 'pictures',
                    type: 'warning',
                    duration: 1000
                })
            },
            uploadFile (data) {
                fetch(this.pre_signed_url, {
                    method: 'PUT',
                    body: data.file
                }).then(() => {
                    this.fileList.push({ url: this.share_url + '' + data.file.uid + '_' + data.file.name })
                    this.emitInput(this.fileList)
                    this.$message.success('Upload successfully')
                })
            }
        }
    }
</script>
<style>
</style>


