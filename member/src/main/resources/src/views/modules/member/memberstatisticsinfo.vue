<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="Parameter Name" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">Query</el-button>
        <el-button v-if="isAuth('member:memberstatisticsinfo:save')" type="primary" @click="addOrUpdateHandle()">Add</el-button>
        <el-button v-if="isAuth('member:memberstatisticsinfo:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">Batch Deletion</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
        prop="memberId"
        header-align="center"
        align="center"
        label="member id">
      </el-table-column>
      <el-table-column
        prop="consumeAmount"
        header-align="center"
        align="center"
        label="total spent amount">
      </el-table-column>
      <el-table-column
        prop="couponAmount"
        header-align="center"
        align="center"
        label="total discount amount">
      </el-table-column>
      <el-table-column
        prop="orderCount"
        header-align="center"
        align="center"
        label="order quantity">
      </el-table-column>
      <el-table-column
        prop="couponCount"
        header-align="center"
        align="center"
        label="coupon quantity">
      </el-table-column>
      <el-table-column
        prop="commentCount"
        header-align="center"
        align="center"
        label="review quantity">
      </el-table-column>
      <el-table-column
        prop="returnOrderCount"
        header-align="center"
        align="center"
        label="return quantity">
      </el-table-column>
      <el-table-column
        prop="loginCount"
        header-align="center"
        align="center"
        label="login quantity">
      </el-table-column>
      <el-table-column
        prop="attendCount"
        header-align="center"
        align="center"
        label="favorite quantity">
      </el-table-column>
      <el-table-column
        prop="fansCount"
        header-align="center"
        align="center"
        label="fans quantity">
      </el-table-column>
      <el-table-column
        prop="collectProductCount"
        header-align="center"
        align="center"
        label="production favorite quantity">
      </el-table-column>
      <el-table-column
        prop="collectSubjectCount"
        header-align="center"
        align="center"
        label="topic favorite quantity">
      </el-table-column>
      <el-table-column
        prop="collectCommentCount"
        header-align="center"
        align="center"
        label="review favorite quantity">
      </el-table-column>
      <el-table-column
        prop="inviteFriendCount"
        header-align="center"
        align="center"
        label="refer friends quantity">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">Update</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- pop-up window, add / update -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './memberstatisticsinfo-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // get data list
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/member/memberstatisticsinfo/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // amount per page
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // current page
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // multiple selection
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // add / update
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // delete
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`Do you want to [${id ? 'delete' : 'batch delete'}] [id=${ids.join(',')}]?`, 'warning', {
          confirmButtonText: 'Sure',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/member/memberstatisticsinfo/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: 'Completed',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
