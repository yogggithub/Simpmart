package com.simpmart.commodity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.entity.CategoryBrandRelationEntity;
import com.simpmart.commodity.service.CategoryBrandRelationService;
import com.simpmart.commodity.vo.BrandVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * brand & category relationship
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 14:22:38
 */
@RestController
@RequestMapping("commodity/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * List
     */
    @RequestMapping("/list")
    //@RequiresPermissions("commodity:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * Infomation
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("commodity:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * Save
     */
    @RequestMapping("/save")
    //@RequiresPermissions("commodity:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * Update
     */
    @RequestMapping("/update")
    //@RequiresPermissions("commodity:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * Delete
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("commodity:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * List
     */
    @GetMapping("/catalog/list")
    //@RequiresPermissions("commodity:categorybrandrelation:list")
    public R catalogList(@RequestParam("brandId") Long brandId) {
        List<CategoryBrandRelationEntity> list =
                categoryBrandRelationService.list(
                        new LambdaQueryWrapper<CategoryBrandRelationEntity>()
                                .eq(CategoryBrandRelationEntity::getBrandId, brandId)
                );

        return R.ok().put("data", list);
    }

    /**
     * @return
     */
    @GetMapping("/brand/list")
    public R relatedBrandList(@RequestParam("catId") Long catId) {
        List<BrandEntity> brandEntityList =
                categoryBrandRelationService.getBransById(catId);
        List<Object> brandVoList = brandEntityList.stream()
                                              .map(brand -> {
                                                  BrandVo vo = new BrandVo();
                                                  vo.setBrandId(brand.getBrandId());
                                                  vo.setBrandName(brand.getName());
                                                  return vo;
                                              }).collect(Collectors.toList());
        return R.ok().put("data", brandVoList);
    }

}
