package com.simpmart.order.dao;

import com.simpmart.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * order
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
