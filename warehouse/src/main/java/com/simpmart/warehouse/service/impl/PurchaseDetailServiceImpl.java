package com.simpmart.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import com.simpmart.warehouse.dao.PurchaseDetailDao;
import com.simpmart.warehouse.entity.PurchaseDetailEntity;
import com.simpmart.warehouse.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    /**
     * modified to search status and warehouse id
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper =
                new LambdaQueryWrapper<PurchaseDetailEntity>();

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            wrapper.eq(PurchaseDetailEntity::getWareId, wareId);
        }
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq(PurchaseDetailEntity::getStatus, status);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> {
                w.eq(PurchaseDetailEntity::getId, key)
                 .or().eq(PurchaseDetailEntity::getPurchaseId, key)
                 .or().eq(PurchaseDetailEntity::getSkuId, key);
            });
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<PurchaseDetailEntity> listDetailByPurchaseId(Long id) {
        return this.list(
                new LambdaQueryWrapper<PurchaseDetailEntity>()
                        .eq(PurchaseDetailEntity::getPurchaseId, id));
    }

}