package com.simpmart.commodity.feign;

import com.simpmart.common.to.SkuPromotionTo;
import com.simpmart.common.to.SpuBoundsTo;
import com.simpmart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("coupon")
public interface CouponSpuFeignService {
    @PostMapping("coupon/spubounds/save/to")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("coupon/spubounds")
    R saveSkuPromotion(@RequestBody SkuPromotionTo skuPromotionTo);
}
