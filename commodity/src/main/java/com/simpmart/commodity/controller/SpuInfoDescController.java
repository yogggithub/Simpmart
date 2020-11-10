package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.SpuInfoDescEntity;
import com.simpmart.commodity.service.SpuInfoDescService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * spu description
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/spuinfodesc")
public class SpuInfoDescController {
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:spuinfodesc:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoDescService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{spuId}")
    //@RequiresPermissions("commodity:spuinfodesc:info")
    public R info(@PathVariable("spuId") Long spuId) {
            SpuInfoDescEntity spuInfoDesc = spuInfoDescService.getById(spuId);

        return R.ok().put("spuInfoDesc", spuInfoDesc);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:spuinfodesc:save")
    public R save(@RequestBody SpuInfoDescEntity spuInfoDesc) {
            spuInfoDescService.save(spuInfoDesc);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spuinfodesc:update")
    public R update(@RequestBody SpuInfoDescEntity spuInfoDesc) {
            spuInfoDescService.updateById(spuInfoDesc);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spuinfodesc:delete")
    public R delete(@RequestBody Long[] spuIds) {
            spuInfoDescService.removeByIds(Arrays.asList(spuIds));

        return R.ok();
    }

}
