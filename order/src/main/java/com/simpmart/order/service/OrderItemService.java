package com.simpmart.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * order item
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

