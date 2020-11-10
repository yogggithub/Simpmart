package com.simpmart.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.warehouse.entity.WarehouseInfoEntity;

import java.util.Map;

/**
 * warehouse information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
public interface WarehouseInfoService extends IService<WarehouseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

