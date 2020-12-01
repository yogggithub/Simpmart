package com.simpmart.coupon.controller;

import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import com.simpmart.coupon.entity.CouponEntity;
import com.simpmart.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * coupon information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@RestController
@RequestMapping("coupon/coupon")
@RefreshScope
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:coupon:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:coupon:info")
    public R info(@PathVariable("id") Long id) {
            CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:coupon:save")
    public R save(@RequestBody CouponEntity coupon) {
            couponService.save(coupon);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:coupon:update")
    public R update(@RequestBody CouponEntity coupon) {
            couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:coupon:delete")
    public R delete(@RequestBody Long[] ids) {
            couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * Test the OpenFeign module
     */
    @RequestMapping("/member/couponlist")
    public R memberCoupon(){
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setCouponName("Test - 20% off");
        return R.ok()
                .put("coupons", Arrays.asList(couponEntity));
    }

    // Get properties value using SpEL
    @Value("${coupon.user.name}")
    private String name;

    /**
     * Test Nacos Configuration
     */
    @RequestMapping("/testconfig")
    public R testConfig(){
        return R.ok()
                .put("name", name);
    }
}
