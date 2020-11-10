package com.simpmart.coupon.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.coupon.entity.CouponHistoryEntity;
import com.simpmart.coupon.service.CouponHistoryService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * coupon collection history
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@RestController
@RequestMapping("coupon/couponhistory")
public class CouponHistoryController {
    @Autowired
    private CouponHistoryService couponHistoryService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:couponhistory:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:couponhistory:info")
    public R info(@PathVariable("id") Long id) {
            CouponHistoryEntity couponHistory = couponHistoryService.getById(id);

        return R.ok().put("couponHistory", couponHistory);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:couponhistory:save")
    public R save(@RequestBody CouponHistoryEntity couponHistory) {
            couponHistoryService.save(couponHistory);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public R update(@RequestBody CouponHistoryEntity couponHistory) {
            couponHistoryService.updateById(couponHistory);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:couponhistory:delete")
    public R delete(@RequestBody Long[] ids) {
            couponHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
