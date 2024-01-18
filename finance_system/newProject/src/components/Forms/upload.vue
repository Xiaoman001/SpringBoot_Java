<template>
  <div>
    <el-upload
    action="http://121.43.229.173:6790/file/uploadFile"
    list-type="picture-card"
    :limit="1"
    :file-list="fileList"
    :http-request="httpRequest"
    accept="image/jpeg,image/png,image/jpg,.pdf"
    :auto-upload="true"
    :class="{hide:hideUploadEdit}"
    :on-change="handleEditChange">
        <i slot="default" class="el-icon-plus"></i>
        <div slot="file" slot-scope="{file}">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
        <span class="el-upload-list__item-actions">
            <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
            </span>
            <span v-if="!disabled" class="el-upload-list__item-delete" @click="handleDownload(file)">
            <i class="el-icon-download"></i>
            </span>
            <span class="el-upload-list__item-delete" @click="handleRemove(file)">
            <i class="el-icon-delete"></i>
            </span>
        </span>
        </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
    <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  props:['imageType'],
  data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        disabled: false,
        name:'file',
        file_id:null,
        fileList:[],
        resule:[],
        hideUploadEdit:false
      };
    },
    methods: {
      handleEditChange (file, fileList) {
        this.hideUploadEdit = fileList.length >= 1
      },
      httpRequest(params){
          var formData=new FormData();
          formData.append(this.name,params.file)
          axios.post('http://121.43.229.173:6790/file/uploadFile',formData, { headers: { 'Content-Type': 'multipart/form-data' }})
            .then((res)=>{
              this.file_id=res.data.data
              console.log(this.file_id)
              this.identifyImage(this.file_id)
          })
      },
      handleRemove(file) {
        if(this.fileList){
          this.fileList=this.fileList.slice(-1)
        }
        this.hideUploadEdit=false
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },
      handleDownload(file) {
        console.log(file);
      },
      identifyImage(fileId){
        this.$post({
          url:"/api/image/getInfo",
          data:{
            fileId: this.file_id,
            type:this.imageType
          }
        })
          .then((response)=>{
            this.resule = response.data
            // console.log(response.data)
            this.$emit('receiveResult',response.data )
          })
      },
    }
  }
</script>

<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>
