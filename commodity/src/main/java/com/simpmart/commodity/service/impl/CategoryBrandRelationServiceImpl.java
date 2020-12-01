package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.BrandDao;
import com.simpmart.commodity.dao.CategoryBrandRelationDao;
import com.simpmart.commodity.dao.CategoryDao;
import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.entity.CategoryBrandRelationEntity;
import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.commodity.service.CategoryBrandRelationService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl
        extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    BrandDao brandDao;

    @Autowired
    CategoryDao categoryDao;

    /**
     * The brand name and category name do not need to be provided
     * when creating new brand-category relationships
     * so when saving, I need to get the appropriate information from the database
     *
     * @param entity New brand-category relationship
     * @return
     */
    public void saveDetail(CategoryBrandRelationEntity entity) {
        Long brandId = entity.getBrandId();
        Long categoryId = entity.getCatalogId();

        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(categoryId);

        entity.setBrandName(brandEntity.getName());
        entity.setCatalogName(categoryEntity.getName());

        this.save(entity);
    }

    /**
     * Update when brand info has been changed in brand table
     *
     * @param brand
     */
    @Override
    public void updateBrand(BrandEntity brand) {
        CategoryBrandRelationEntity entity =
                new CategoryBrandRelationEntity();
        entity.setBrandId(brand.getBrandId());
        entity.setBrandName(brand.getName());
        this.update(entity,
                    new LambdaUpdateWrapper<CategoryBrandRelationEntity>()
                            .eq(CategoryBrandRelationEntity::getBrandId, brand.getBrandId()));
    }

    /**
     * Update when brand info has been changed in brand table
     *
     * @param category
     */
    @Override
    public void updateCategory(CategoryEntity category) {
        /* the following is one of the ways to update category info cascade
        CategoryBrandRelationEntity entity =
                new CategoryBrandRelationEntity();
        entity.setBrandId(category.getCatId());
        entity.setBrandName(category.getName());
        this.update(entity,
                    new LambdaUpdateWrapper<CategoryBrandRelationEntity>()
                            .eq(CategoryBrandRelationEntity::getCatalogId, category.getCatId()));
         */

        // here is another way to update category info cascade
        this.baseMapper.updateCategory(category.getCatId(), category.getName());
    }

    @Autowired
    CategoryBrandRelationDao relationDao;

    /**
     * @param catId
     * @return
     */
    @Override
    public List<BrandEntity> getBransById(Long catId) {
        List<CategoryBrandRelationEntity> relationEntityList = relationDao.selectList(
                new LambdaQueryWrapper<CategoryBrandRelationEntity>()
                        .eq(CategoryBrandRelationEntity::getCatalogId, catId)
        );
        return relationEntityList.stream()
                          .map(entity -> brandDao.selectById(entity.getBrandId())
        ).collect(Collectors.toList());
    }
}