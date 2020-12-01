package com.simpmart.commodity.controller;

import com.simpmart.commodity.service.AttrService;
import com.simpmart.commodity.vo.AttrResponseVo;
import com.simpmart.commodity.vo.AttrVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * attribute
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("commodity:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {

        AttrResponseVo responseVo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", responseVo);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttrVo(attr);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attr:update")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

    @GetMapping("/{attrType}/list/{catalogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catalogId") Long catalogId,
                          @PathVariable("attrType") String type) {
        PageUtils page = attrService.queryBaseAttrPage(params, catalogId, type);
        return R.ok().put("page", page);
    }

}
