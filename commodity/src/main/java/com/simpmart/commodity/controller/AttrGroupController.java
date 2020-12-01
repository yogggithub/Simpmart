package com.simpmart.commodity.controller;

import com.simpmart.commodity.entity.AttrGroupEntity;
import com.simpmart.commodity.service.AttrAttrgroupRelationService;
import com.simpmart.commodity.service.AttrGroupService;
import com.simpmart.commodity.service.AttrService;
import com.simpmart.commodity.service.CategoryService;
import com.simpmart.commodity.vo.AttrGroupRelationVo;
import com.simpmart.commodity.vo.AttrGroupWithAttrsVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


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

    @Autowired
    CategoryService categoryService;

    /**
     * Information
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("commodity:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        attrGroup.setCatalogPath(
                categoryService.findCategoryPath(attrGroup.getCatalogId()));

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

    /**
     * List
     */
    @RequestMapping("/list/{catalogId}")
    //@RequiresPermissions("commodity:attrgroup:list")
    public R listByCategoryId(@RequestParam Map<String, Object> params,
                              @PathVariable("catalogId") Long catalogId) {
        PageUtils page = attrGroupService.queryPage(params, catalogId);

        return R.ok().put("page", page);
    }

    @Autowired
    AttrService attrService;

    /**
     * get all attributes that related to given group
     *
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrGroupId) {
        return R.ok()
                .put("data", attrService.getAttrRelation(attrGroupId));
    }

    /**
     * delete attribute-group relationship
     *
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(
            @RequestBody List<AttrGroupRelationVo> vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * get all attribute that dont related to any group
     *
     * @param params
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrGroupId) {
        return R.ok()
                .put("page", attrService.getAttrNoRelation(params, attrGroupId));
    }

    @Autowired
    AttrAttrgroupRelationService relationService;

    /**
     * build a relationship connect an anttribute to a group
     *
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        relationService.saveBatch(vos);
        return R.ok();
    }

    @GetMapping("/{catalogId}/withattr")
    public R getGroupWithAttr(@PathVariable("catalogId") Long catalogId){
        List<AttrGroupWithAttrsVo> vos =
                attrGroupService.getGroupWithAttr(catalogId);
        return R.ok().put("data", vos);
    }
}
