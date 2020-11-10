package com.simpmart.coupon.dao;

import com.simpmart.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * member price
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {
	
}
