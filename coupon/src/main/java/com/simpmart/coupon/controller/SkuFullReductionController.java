package com.simpmart.coupon.controller;

import com.simpmart.common.to.SkuPromotionTo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import com.simpmart.coupon.entity.SkuFullReductionEntity;
import com.simpmart.coupon.service.SkuFullReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * price-break discount
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SkuFullReductionController {
    @Autowired
    private SkuFullReductionService skuFullReductionService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:skufullreduction:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuFullReductionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:skufullreduction:info")
    public R info(@PathVariable("id") Long id) {
            SkuFullReductionEntity skuFullReduction = skuFullReductionService.getById(id);

        return R.ok().put("skuFullReduction", skuFullReduction);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:skufullreduction:save")
    public R save(@RequestBody SkuFullReductionEntity skuFullReduction) {
            skuFullReductionService.save(skuFullReduction);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:skufullreduction:update")
    public R update(@RequestBody SkuFullReductionEntity skuFullReduction) {
            skuFullReductionService.updateById(skuFullReduction);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:skufullreduction:delete")
    public R delete(@RequestBody Long[] ids) {
            skuFullReductionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * Save general promotions info
     */
    @PostMapping("/saveinfo")
    //@RequiresPermissions("coupon:skufullreduction:save")
    public R saveSkuPromotion(@RequestBody SkuPromotionTo skuPromotionTo) {
        skuFullReductionService.saveSkuPromotion(skuPromotionTo);

        return R.ok();
    }

}
