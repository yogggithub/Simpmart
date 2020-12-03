package com.simpmart.commodity.controller;

import com.simpmart.commodity.entity.SpuInfoEntity;
import com.simpmart.commodity.service.SpuInfoService;
import com.simpmart.commodity.vo.SpuSaveVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * standard product unit infomation
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * List
     * modified to achieve fuzzy and complicate search
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * Information
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
            SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * Save
     * Because attributes need to saved is very large
     * so create a series of vo entities to complete save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:spuinfo:save")
    public R save(@RequestBody SpuSaveVo vo) {
            spuInfoService.saveSpuInfo(vo);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
            spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
            spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
