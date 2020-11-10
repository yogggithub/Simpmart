<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="Parameter Name" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">Query</el-button>
        <el-button v-if="isAuth('order:order:save')" type="primary" @click="addOrUpdateHandle()">Add</el-button>
        <el-button v-if="isAuth('order:order:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">Batch Deletion</el-button>
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
        prop="orderSn"
        header-align="center"
        align="center"
        label="order no.">
      </el-table-column>
      <el-table-column
        prop="couponId"
        header-align="center"
        align="center"
        label="coupon used">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="order create time">
      </el-table-column>
      <el-table-column
        prop="memberUsername"
        header-align="center"
        align="center"
        label="member username">
      </el-table-column>
      <el-table-column
        prop="totalAmount"
        header-align="center"
        align="center"
        label="item(s) subtotal amount">
      </el-table-column>
      <el-table-column
        prop="payAmount"
        header-align="center"
        align="center"
        label="grand total amount">
      </el-table-column>
      <el-table-column
        prop="freightAmount"
        header-align="center"
        align="center"
        label="shipping and handling costs">
      </el-table-column>
      <el-table-column
        prop="promotionAmount"
        header-align="center"
        align="center"
        label="promotional Optimization Amount">
      </el-table-column>
      <el-table-column
        prop="integrationAmount"
        header-align="center"
        align="center"
        label="membership rewards">
      </el-table-column>
      <el-table-column
        prop="couponAmount"
        header-align="center"
        align="center"
        label="coupon credit">
      </el-table-column>
      <el-table-column
        prop="discountAmount"
        header-align="center"
        align="center"
        label="adjusted discounts of order">
      </el-table-column>
      <el-table-column
        prop="payType"
        header-align="center"
        align="center"
        label="payment method [1->credit card, 2->Paypal, 3->debit card, 4->cash on delivery]">
      </el-table-column>
      <el-table-column
        prop="sourceType"
        header-align="center"
        align="center"
        label="order source[0->pc, 1->mobile]">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]">
      </el-table-column>
      <el-table-column
        prop="deliveryCompany"
        header-align="center"
        align="center"
        label="logistics company">
      </el-table-column>
      <el-table-column
        prop="deliverySn"
        header-align="center"
        align="center"
        label="shipment number">
      </el-table-column>
      <el-table-column
        prop="autoConfirmDay"
        header-align="center"
        align="center"
        label="automatic confirmation time (days)">
      </el-table-column>
      <el-table-column
        prop="integration"
        header-align="center"
        align="center"
        label="member rewards to be earned">
      </el-table-column>
      <el-table-column
        prop="growth"
        header-align="center"
        align="center"
        label="growth points to be earned">
      </el-table-column>
      <el-table-column
        prop="billType"
        header-align="center"
        align="center"
        label="invoice type[0->not required, 1->electronic, 2->printed]">
      </el-table-column>
      <el-table-column
        prop="billHeader"
        header-align="center"
        align="center"
        label="invoice recipient">
      </el-table-column>
      <el-table-column
        prop="billContent"
        header-align="center"
        align="center"
        label="contents of invoice">
      </el-table-column>
      <el-table-column
        prop="billReceiverPhone"
        header-align="center"
        align="center"
        label="recipient phone number">
      </el-table-column>
      <el-table-column
        prop="billReceiverEmail"
        header-align="center"
        align="center"
        label="recipient email">
      </el-table-column>
      <el-table-column
        prop="receiverName"
        header-align="center"
        align="center"
        label="consignee name">
      </el-table-column>
      <el-table-column
        prop="receiverPhone"
        header-align="center"
        align="center"
        label="consignee phone number">
      </el-table-column>
      <el-table-column
        prop="receiverPostCode"
        header-align="center"
        align="center"
        label="consignee postcode">
      </el-table-column>
      <el-table-column
        prop="receiverProvince"
        header-align="center"
        align="center"
        label="state/province/region/territory">
      </el-table-column>
      <el-table-column
        prop="receiverCity"
        header-align="center"
        align="center"
        label="city">
      </el-table-column>
      <el-table-column
        prop="receiverRegion"
        header-align="center"
        align="center"
        label="address line 1 (street address/P.O. box)">
      </el-table-column>
      <el-table-column
        prop="receiverDetailAddress"
        header-align="center"
        align="center"
        label="address line 2 (appartment/floor/suite/unit)">
      </el-table-column>
      <el-table-column
        prop="note"
        header-align="center"
        align="center"
        label="order comments">
      </el-table-column>
      <el-table-column
        prop="confirmStatus"
        header-align="center"
        align="center"
        label="confirm status[0->unconfirmed, 1->confirmed]">
      </el-table-column>
      <el-table-column
        prop="deleteStatus"
        header-align="center"
        align="center"
        label="delete status[0->non-deleted, 1->deleted]">
      </el-table-column>
      <el-table-column
        prop="useIntegration"
        header-align="center"
        align="center"
        label="member rewards used when place order">
      </el-table-column>
      <el-table-column
        prop="paymentTime"
        header-align="center"
        align="center"
        label="payment time">
      </el-table-column>
      <el-table-column
        prop="deliveryTime"
        header-align="center"
        align="center"
        label="shipping time">
      </el-table-column>
      <el-table-column
        prop="receiveTime"
        header-align="center"
        align="center"
        label="shipped confirmation time">
      </el-table-column>
      <el-table-column
        prop="commentTime"
        header-align="center"
        align="center"
        label="review time">
      </el-table-column>
      <el-table-column
        prop="modifyTime"
        header-align="center"
        align="center"
        label="review modify time">
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
  import AddOrUpdate from './order-add-or-update'
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
          url: this.$http.adornUrl('/order/order/list'),
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
            url: this.$http.adornUrl('/order/order/delete'),
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
