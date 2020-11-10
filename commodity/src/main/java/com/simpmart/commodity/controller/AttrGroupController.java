package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.AttrGroupEntity;
import com.simpmart.commodity.service.AttrGroupService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * attribute groups
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("commodity:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
            AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
            attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
            attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
            attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
