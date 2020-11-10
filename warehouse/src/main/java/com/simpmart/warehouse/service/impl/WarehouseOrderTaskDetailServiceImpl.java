package com.simpmart.warehouse.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;

import com.simpmart.warehouse.dao.WarehouseOrderTaskDetailDao;
import com.simpmart.warehouse.entity.WarehouseOrderTaskDetailEntity;
import com.simpmart.warehouse.service.WarehouseOrderTaskDetailService;


@Service("warehouseOrderTaskDetailService")
public class WarehouseOrderTaskDetailServiceImpl extends ServiceImpl<WarehouseOrderTaskDetailDao, WarehouseOrderTaskDetailEntity> implements WarehouseOrderTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarehouseOrderTaskDetailEntity> page = this.page(
                new Query<WarehouseOrderTaskDetailEntity>().getPage(params),
                new QueryWrapper<WarehouseOrderTaskDetailEntity>()
        );

        return new PageUtils(page);
    }

}