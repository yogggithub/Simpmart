package com.simpmart.commodity.controller;

import java.util.Arrays;
import java.util.Map;

    import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.commodity.service.CategoryService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;


/**
 * category
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:category:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("commodity:category:info")
    public R info(@PathVariable("catId") Long catId) {
            CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:category:save")
    public R save(@RequestBody CategoryEntity category) {
            categoryService.save(category);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:category:update")
    public R update(@RequestBody CategoryEntity category) {
            categoryService.updateById(category);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:category:delete")
    public R delete(@RequestBody Long[] catIds) {
            categoryService.removeByIds(Arrays.asList(catIds));

        return R.ok();
    }

}
