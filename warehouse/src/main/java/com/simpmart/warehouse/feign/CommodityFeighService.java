package com.simpmart.warehouse.feign;

import com.simpmart.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gateway")
public interface CommodityFeighService {
    @RequestMapping("/api/commodity/skuinfo/info/{skuId}")
    //@RequiresPermissions("commodity:skuinfo:info")
    R info(@PathVariable("skuId") Long skuId);
}
