package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.entity.CategoryBrandRelationEntity;
import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * brand & category relationship
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(BrandEntity brand);

    void updateCategory(CategoryEntity category);

    List<BrandEntity> getBransById(Long catId);
}

