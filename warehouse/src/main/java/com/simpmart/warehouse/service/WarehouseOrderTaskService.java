package com.simpmart.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.warehouse.entity.WarehouseOrderTaskEntity;

import java.util.Map;

/**
 * ware task
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
public interface WarehouseOrderTaskService extends IService<WarehouseOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

