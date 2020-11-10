package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.SkuInfoEntity;
import com.simpmart.commodity.service.SkuInfoService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * stock keeping unit information
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:skuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{skuId}")
    //@RequiresPermissions("commodity:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId) {
            SkuInfoEntity skuInfo = skuInfoService.getById(skuId);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:skuinfo:save")
    public R save(@RequestBody SkuInfoEntity skuInfo) {
            skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:skuinfo:update")
    public R update(@RequestBody SkuInfoEntity skuInfo) {
            skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:skuinfo:delete")
    public R delete(@RequestBody Long[] skuIds) {
            skuInfoService.removeByIds(Arrays.asList(skuIds));

        return R.ok();
    }

}
