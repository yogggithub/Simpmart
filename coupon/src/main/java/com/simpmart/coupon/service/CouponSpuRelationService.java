package com.simpmart.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.coupon.entity.CouponSpuRelationEntity;

import java.util.Map;

/**
 * coupon & spu relationship
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

