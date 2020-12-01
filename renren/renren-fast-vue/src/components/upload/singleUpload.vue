<template>
    <div>
        <el-upload
            :before-upload="beforeUpload"
            :file-list="fileList"
            :http-request="uploadFile"
            :multiple="false"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :show-file-list="showFileList"
            action="#"
            list-type="picture"
        >
            <el-button size="small" type="primary">Upload</el-button>
            <div
                class="el-upload__tip"
                slot="tip"
            >Only uploade jpg/png files, and no more than 10MB.</div>
        </el-upload>
        <el-dialog :modal="false" :visible.sync="dialogVisible">
            <img :src="fileList[0].url" alt width="100%" />
        </el-dialog>
    </div>
</template>
<script>
    import { authorize } from './authorize'

    export default {
        name: 'singleUpload',
        props: {
            value: String
        },
        computed: {
            imageUrl () {
                return this.value
            },
            imageName () {
                if (this.value != null && this.value !== '') {
                    return this.value.substr(this.value.lastIndexOf('/') + 1)
                } else {
                    return null
                }
            },
            fileList () {
                return [{
                    name: this.imageName,
                    url: this.imageUrl
                }]
            },
            showFileList: {
                get: function () {
                    return this.value !== null && this.value !== '' && this.value !== undefined
                },
                set: function (newValue) {
                }
            }
        },
        data () {
            return {
                pre_signed_url: '',
                share_url: '',
                dialogVisible: false
            }
        },
        methods: {
            emitInput (val) {
                this.$emit('input', val)
            },
            handleRemove (file, fileList) {
                this.emitInput('')
            },
            handlePreview (file) {
                this.dialogVisible = true
            },
            beforeUpload (file) {
                return new Promise((resolve, reject) => {
                    let objectName = file.uid + '_' + file.name
                    authorize(objectName).then(response => {
                        this.pre_signed_url = response.data
                        this.share_url = this.pre_signed_url.split('?')[0]
                        resolve(true)
                        // eslint-disable-next-line handle-callback-err
                    }).catch(err => {
                        // ignore reject parameter hint
                        // eslint-disable-next-line prefer-promise-reject-errors
                        reject(false)
                    })
                })
            },
            uploadFile (data) {
                fetch(this.pre_signed_url, {
                    method: 'PUT',
                    body: data.file
                }).then(() => {
                    this.showFileList = true
                    this.fileList.pop()
                    this.fileList.push({ name: data.file.name, url: this.share_url })
                    this.emitInput(this.fileList[0].url)
                    this.$message.success('Upload successfully')
                })
            }
        }
    }
</script>
<style>
</style>


