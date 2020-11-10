package com.simpmart.coupon.dao;

import com.simpmart.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * coupon information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
