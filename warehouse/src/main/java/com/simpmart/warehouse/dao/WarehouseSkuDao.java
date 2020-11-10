package com.simpmart.warehouse.dao;

import com.simpmart.warehouse.entity.WarehouseSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * production inventory
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Mapper
public interface WarehouseSkuDao extends BaseMapper<WarehouseSkuEntity> {
	
}
