package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.service.BrandService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * brand
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("commodity:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
            BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:brand:save")
    public R save(@RequestBody BrandEntity brand) {
            brandService.save(brand);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:brand:update")
    public R update(@RequestBody BrandEntity brand) {
            brandService.updateById(brand);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
            brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
