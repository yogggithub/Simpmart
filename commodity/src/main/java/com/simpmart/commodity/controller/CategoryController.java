package com.simpmart.commodity.controller;

import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.commodity.service.CategoryService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
        categoryService.updateCascade(category);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:category:delete")
    public R delete(@RequestBody Long[] catIds) {
        /*
         * change original auto-generated delete method
         * because I need check whether the category is related to others
         */
        categoryService.removeCategoryByIds(Arrays.asList(catIds));

        return R.ok();
    }

    /**
     * Query all the categories and its children categories
     * and wrap them into a tree
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("commodity:category:list")
    public R list() {
        List<CategoryEntity> categoryEntityList =
                categoryService.listWithTree();

        return R.ok().put("data", categoryEntityList);
    }

    /**
     * Batch update after drag and drop a category on webpage
     * Focus on update sort information
     *
     */
    @RequestMapping("/update/sort")
    //@RequiresPermissions("commodity:category:update")
    public R update(@RequestBody CategoryEntity[] category) {
        categoryService.updateBatchById(Arrays.asList(category));

        return R.ok();
    }
}
