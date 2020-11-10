<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="Parameter Name" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">Query</el-button>
        <el-button v-if="isAuth('coupon:coupon:save')" type="primary" @click="addOrUpdateHandle()">Add</el-button>
        <el-button v-if="isAuth('coupon:coupon:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">Batch Deletion</el-button>
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
        prop="couponType"
        header-align="center"
        align="center"
        label="coupontype [0->store wide bonus, 1->member gift, 2->shopping bonus, 3->register gift]">
      </el-table-column>
      <el-table-column
        prop="couponImg"
        header-align="center"
        align="center"
        label="coupon image">
      </el-table-column>
      <el-table-column
        prop="couponName"
        header-align="center"
        align="center"
        label="coupon name">
      </el-table-column>
      <el-table-column
        prop="num"
        header-align="center"
        align="center"
        label="coupon quantity">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="coupon amount">
      </el-table-column>
      <el-table-column
        prop="perLimit"
        header-align="center"
        align="center"
        label="limitatiton per customer">
      </el-table-column>
      <el-table-column
        prop="minPoint"
        header-align="center"
        align="center"
        label="usage threshold">
      </el-table-column>
      <el-table-column
        prop="startTime"
        header-align="center"
        align="center"
        label="coupon start time">
      </el-table-column>
      <el-table-column
        prop="endTime"
        header-align="center"
        align="center"
        label="coupon end time">
      </el-table-column>
      <el-table-column
        prop="useType"
        header-align="center"
        align="center"
        label="usage type [0->store wide, 1->specific catory, 2->specific item]">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="coupon note">
      </el-table-column>
      <el-table-column
        prop="publishCount"
        header-align="center"
        align="center"
        label="publish quantity">
      </el-table-column>
      <el-table-column
        prop="useCount"
        header-align="center"
        align="center"
        label="used quantity">
      </el-table-column>
      <el-table-column
        prop="receiveCount"
        header-align="center"
        align="center"
        label="collected quantity">
      </el-table-column>
      <el-table-column
        prop="enableStartTime"
        header-align="center"
        align="center"
        label="collection period start time">
      </el-table-column>
      <el-table-column
        prop="enableEndTime"
        header-align="center"
        align="center"
        label="collection period end time">
      </el-table-column>
      <el-table-column
        prop="code"
        header-align="center"
        align="center"
        label="promo code">
      </el-table-column>
      <el-table-column
        prop="memberLevel"
        header-align="center"
        align="center"
        label="member level threshold [0->no limitatiton, other->corresponding member level]">
      </el-table-column>
      <el-table-column
        prop="publish"
        header-align="center"
        align="center"
        label="publish status [0->unpublished, 1->published]">
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
  import AddOrUpdate from './coupon-add-or-update'
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
          url: this.$http.adornUrl('/coupon/coupon/list'),
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
            url: this.$http.adornUrl('/coupon/coupon/delete'),
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
