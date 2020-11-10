package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.SpuImagesEntity;
import com.simpmart.commodity.service.SpuImagesService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * spu image
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/spuimages")
public class SpuImagesController {
    @Autowired
    private SpuImagesService spuImagesService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:spuimages:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:spuimages:info")
    public R info(@PathVariable("id") Long id) {
            SpuImagesEntity spuImages = spuImagesService.getById(id);

        return R.ok().put("spuImages", spuImages);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:spuimages:save")
    public R save(@RequestBody SpuImagesEntity spuImages) {
            spuImagesService.save(spuImages);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spuimages:update")
    public R update(@RequestBody SpuImagesEntity spuImages) {
            spuImagesService.updateById(spuImages);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spuimages:delete")
    public R delete(@RequestBody Long[] ids) {
            spuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
