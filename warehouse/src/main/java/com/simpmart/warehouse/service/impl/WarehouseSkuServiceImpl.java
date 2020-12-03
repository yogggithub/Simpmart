package com.simpmart.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import com.simpmart.common.utils.R;
import com.simpmart.warehouse.dao.WarehouseSkuDao;
import com.simpmart.warehouse.entity.WarehouseSkuEntity;
import com.simpmart.warehouse.feign.CommodityFeighService;
import com.simpmart.warehouse.service.WarehouseSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;


@Service("warehouseSkuService")
public class WarehouseSkuServiceImpl extends ServiceImpl<WarehouseSkuDao, WarehouseSkuEntity> implements WarehouseSkuService {

    /**
     * update to search warehouse_id and sku_id
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<WarehouseSkuEntity> wrapper =
                new LambdaQueryWrapper<WarehouseSkuEntity>();
        String wareId = (String) params.get("wareId");
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(wareId)) {
            wrapper.eq(WarehouseSkuEntity::getWareId, wareId);
        }
        if (!StringUtils.isEmpty(skuId)) {
            wrapper.eq(WarehouseSkuEntity::getSkuId, skuId);
        }

        IPage<WarehouseSkuEntity> page = this.page(
                new Query<WarehouseSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Autowired
    WarehouseSkuDao skuDao;

    @Autowired
    CommodityFeighService commodityFeighService;

    /**
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        // if there is no record for this sku, it should be a insert
        List<WarehouseSkuEntity> skuEntities = skuDao.selectList(
                new LambdaQueryWrapper<WarehouseSkuEntity>()
                        .eq(WarehouseSkuEntity::getSkuId, skuId)
                        .eq(WarehouseSkuEntity::getWareId, wareId)
        );
        if (skuEntities == null || skuEntities.size() == 0) {
            WarehouseSkuEntity skuEntity = new WarehouseSkuEntity();
            skuEntity.setSkuId(skuId);
            skuEntity.setWareId(wareId);
            skuEntity.setStock(skuNum);
            skuEntity.setStockLocked(0);    // no locked stock

            // surround with try...catch
            // avoid service error cause all transaction failed and roll back
            try {
                R skuInfo = commodityFeighService.info(skuId);
                if (skuInfo.get("code").equals(0)) {
                    Map<String, Object> data =
                            (Map<String, Object>) skuInfo.get("skuInfo");
                    skuEntity.setSkuName(
                            data.get("skuName").toString());

                }

            } catch (Exception e) {
            }
            skuDao.insert(skuEntity);
        }
        // otherwise should be a update
        else {
            skuDao.addStock(skuId, wareId, skuNum);
        }
    }

}