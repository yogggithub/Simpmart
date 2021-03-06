package com.simpmart.commodity.feign;

import com.simpmart.common.to.SkuPromotionTo;
import com.simpmart.common.to.SpuBoundsTo;
import com.simpmart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gateway")
public interface CouponSpuFeignService {
    @PostMapping("/api/coupon/spubounds/save/to")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("/api/coupon/skufullreduction/saveinfo")
    R saveSkuPromotion(@RequestBody SkuPromotionTo skuPromotionTo);
}
