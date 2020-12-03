package com.simpmart.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.constant.WarehouseConst;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import com.simpmart.warehouse.dao.PurchaseDao;
import com.simpmart.warehouse.entity.PurchaseDetailEntity;
import com.simpmart.warehouse.entity.PurchaseEntity;
import com.simpmart.warehouse.service.PurchaseDetailService;
import com.simpmart.warehouse.service.PurchaseService;
import com.simpmart.warehouse.service.WarehouseSkuService;
import com.simpmart.warehouse.vo.ItemVo;
import com.simpmart.warehouse.vo.MergeVo;
import com.simpmart.warehouse.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageUnreceived(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseEntity> wrapper =
                new LambdaQueryWrapper<PurchaseEntity>()
                        .eq(PurchaseEntity::getStatus,
                            WarehouseConst.PurchaseStatusEnum
                                    .CREATED.getCode())
                        .or()
                        .eq(PurchaseEntity::getStatus,
                            WarehouseConst.PurchaseStatusEnum
                                    .ASSIGNED.getCode());

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Autowired
    PurchaseDetailService detailService;

    /**
     * @param mergeVo
     */
    @Transactional
    @Override
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();

        // create new order when user didn't select any order
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setStatus(
                    WarehouseConst.PurchaseStatusEnum
                            .CREATED.getCode());    // 0 means in created status
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }

        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> purchaseDetailEntities =
                mergeVo.getItems()
                       .stream()
                       // .filter(v -> {
                       //     Integer status = this.getById(v)
                       //                          .getStatus();
                       //     return status == WarehouseConst.PurchaseStatusEnum
                       //             .CREATED.getCode() ||
                       //            status == WarehouseConst.PurchaseStatusEnum
                       //                    .ASSIGNED.getCode();
                       // })
                       .map(item -> {
                           PurchaseDetailEntity detailEntity =
                                   new PurchaseDetailEntity();
                           detailEntity.setId(item);
                           detailEntity.setPurchaseId(finalPurchaseId);
                           detailEntity.setStatus(WarehouseConst.PurchaseDetailStatusEnum
                                                          .ASSIGNED.getCode());
                           return detailEntity;
                       })
                       .collect(Collectors.toList());

        detailService.updateBatchById(purchaseDetailEntities);

        // refresh the update time of purchase order
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(finalPurchaseId);
        purchaseEntity.setUpdateTime(new Date());

        this.updateById(purchaseEntity);

    }

    /**
     * @param ids
     */
    @Transactional
    @Override
    public void receiveTask(List<Long> ids) {
        // validate the task
        /*
           validate rules:
           1. task should be in assigned status
                theoretically, created orders should be assigned to one staff first.
           2. TODO task should be assigned to staff's account (Incomplete)
         */
        List<PurchaseEntity> purchaseEntities =
                ids.stream()
                   .map(this::getById)
                   .filter(o -> o.getStatus() ==
                                WarehouseConst.PurchaseStatusEnum.ASSIGNED.getCode())
                   // change the status of order to received
                   .peek(o -> {
                       o.setStatus(
                               WarehouseConst.PurchaseStatusEnum.RECEIVED.getCode());
                       o.setUpdateTime(new Date());
                   })
                   .collect(Collectors.toList());

        // change status of tasks
        this.updateBatchById(purchaseEntities);

        // change status of purchase requirements belongs to the task
        for (PurchaseEntity purchase : purchaseEntities) {
            System.out.println(purchase);
            List<PurchaseDetailEntity> purchaseDetailEntities =
                    detailService
                            .listDetailByPurchaseId(purchase.getId());
            List<PurchaseDetailEntity> detailEntities =
                    purchaseDetailEntities
                            .stream()
                            .map(item -> {
                                PurchaseDetailEntity detailEntity =
                                        new PurchaseDetailEntity();
                                detailEntity.setId(item.getId());
                                detailEntity.setStatus(
                                        WarehouseConst
                                                .PurchaseDetailStatusEnum
                                                .PURCHASING
                                                .getCode());
                                return detailEntity;
                            }).collect(Collectors.toList());
            detailService.updateBatchById(detailEntities);
        }
    }

    @Autowired
    WarehouseSkuService skuService;

    /**
     * @param vo
     */
    @Override
    public void purchaseDone(PurchaseDoneVo vo) {
        // update requirements contained in the order
        boolean flag = true;
        List<PurchaseDetailEntity> updateDetail = new ArrayList<>();
        for (ItemVo item : vo.getItems()) {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            if (item.getStatus() == WarehouseConst
                    .PurchaseDetailStatusEnum
                    .Failure.getCode()) {
                flag = false;
            } else {
                // put purchased goods into stock
                PurchaseDetailEntity entity =
                        detailService.getById(item.getItemId());

                skuService.addStock(entity.getSkuId(),
                                    entity.getWareId(),
                                    entity.getSkuNum());
            }
            detailEntity.setStatus(item.getStatus());
            detailEntity.setId(item.getItemId());
            updateDetail.add(detailEntity);
        }
        detailService.updateBatchById(updateDetail);
        // update purchase order
        // status would be failure when any of requirements failure
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(vo.getId());
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setStatus(
                flag ? WarehouseConst.PurchaseStatusEnum
                        .FINISHED.getCode()
                        : WarehouseConst.PurchaseStatusEnum
                        .Failure.getCode());
        this.updateById(purchaseEntity);
    }
}