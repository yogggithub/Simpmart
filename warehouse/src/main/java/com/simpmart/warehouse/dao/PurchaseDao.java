package com.simpmart.warehouse.dao;

import com.simpmart.warehouse.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * purchase information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:01
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
