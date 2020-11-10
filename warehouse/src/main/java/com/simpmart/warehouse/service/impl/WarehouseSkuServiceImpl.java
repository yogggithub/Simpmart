package com.simpmart.warehouse.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;

import com.simpmart.warehouse.dao.WarehouseSkuDao;
import com.simpmart.warehouse.entity.WarehouseSkuEntity;
import com.simpmart.warehouse.service.WarehouseSkuService;


@Service("warehouseSkuService")
public class WarehouseSkuServiceImpl extends ServiceImpl<WarehouseSkuDao, WarehouseSkuEntity> implements WarehouseSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarehouseSkuEntity> page = this.page(
                new Query<WarehouseSkuEntity>().getPage(params),
                new QueryWrapper<WarehouseSkuEntity>()
        );

        return new PageUtils(page);
    }

}