package com.simpmart.member.feign;

import com.simpmart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("coupon")
// argument is the name of the serice be called
public interface CouponFeignService {
    /**
     * Copy full signature of the method be called
     * note: url must be complete
     * i.e. there are prefix for the controller in coupon model
     * @return
     */
    @RequestMapping("/coupon/coupon/member/couponlist")
    R memberCoupon();
}
