package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.SkuImagesEntity;
import com.simpmart.commodity.service.SkuImagesService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * sku images
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/skuimages")
public class SkuImagesController {
    @Autowired
    private SkuImagesService skuImagesService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:skuimages:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:skuimages:info")
    public R info(@PathVariable("id") Long id) {
            SkuImagesEntity skuImages = skuImagesService.getById(id);

        return R.ok().put("skuImages", skuImages);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:skuimages:save")
    public R save(@RequestBody SkuImagesEntity skuImages) {
            skuImagesService.save(skuImages);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:skuimages:update")
    public R update(@RequestBody SkuImagesEntity skuImages) {
            skuImagesService.updateById(skuImages);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:skuimages:delete")
    public R delete(@RequestBody Long[] ids) {
            skuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
