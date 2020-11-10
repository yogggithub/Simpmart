package com.simpmart.warehouse.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;

import com.simpmart.warehouse.dao.WarehouseInfoDao;
import com.simpmart.warehouse.entity.WarehouseInfoEntity;
import com.simpmart.warehouse.service.WarehouseInfoService;


@Service("warehouseInfoService")
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoDao, WarehouseInfoEntity> implements WarehouseInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WarehouseInfoEntity> page = this.page(
                new Query<WarehouseInfoEntity>().getPage(params),
                new QueryWrapper<WarehouseInfoEntity>()
        );

        return new PageUtils(page);
    }

}