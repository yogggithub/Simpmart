package com.simpmart.warehouse.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;

import com.simpmart.warehouse.dao.WarehouseOrderTaskDao;
import com.simpmart.warehouse.entity.WarehouseOrderTaskEntity;
import com.simpmart.warehouse.service.WarehouseOrderTaskService;


@Service("warehouseOrderTaskService")
public class WarehouseOrderTaskServiceImpl extends ServiceImpl<WarehouseOrderTaskDao, WarehouseOrderTaskEntity> implements WarehouseOrderTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarehouseOrderTaskEntity> page = this.page(
                new Query<WarehouseOrderTaskEntity>().getPage(params),
                new QueryWrapper<WarehouseOrderTaskEntity>()
        );

        return new PageUtils(page);
    }

}