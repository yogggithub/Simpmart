package com.simpmart.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import com.simpmart.warehouse.dao.WarehouseInfoDao;
import com.simpmart.warehouse.entity.WarehouseInfoEntity;
import com.simpmart.warehouse.service.WarehouseInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("warehouseInfoService")
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoDao, WarehouseInfoEntity> implements WarehouseInfoService {

    /**
     * update to implement fuzzy search
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<WarehouseInfoEntity> wrapper =
                new LambdaQueryWrapper<WarehouseInfoEntity>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.eq(WarehouseInfoEntity::getId, key)
                   .or().like(WarehouseInfoEntity::getName, key)
                   .or().like(WarehouseInfoEntity::getAddress, key)
                   .or().eq(WarehouseInfoEntity::getAreacode, key);
        }
        IPage<WarehouseInfoEntity> page = this.page(
                new Query<WarehouseInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}