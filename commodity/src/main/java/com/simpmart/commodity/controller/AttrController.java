package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.AttrEntity;
import com.simpmart.commodity.service.AttrService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


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
            AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:attr:save")
    public R save(@RequestBody AttrEntity attr) {
            attrService.save(attr);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attr:update")
    public R update(@RequestBody AttrEntity attr) {
            attrService.updateById(attr);

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

}
