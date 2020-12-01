package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.CategoryDao;
import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.commodity.service.CategoryBrandRelationService;
import com.simpmart.commodity.service.CategoryService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        /*
         * Step 1: get all the categories
         *
         * Note:
         * 1. baseMapper
         * This class inherit ServiceImpl,
         * and there is a baseMapper which related to the generic arguments
         * In this specific class, the baseMapper is CategoryDao, and can manipulate
         * the CategoryEntity
         *
         * 2. baseMapper.selectList()
         * Without any condition, this method would query all of the Category.
         * So, can given null as argument
         */
        List<CategoryEntity> categoryEntityList
                = baseMapper.selectList(null);

        /*
         * Step 2: store them into a tree structure
         * 2.1 find all level 1 menus whose parent id is 0
         * 2.2 find the children categories of the category
         * 2.3 sort the categories
         * 2.4 convert into a list, and return
         */
        return categoryEntityList.stream()
                                 // Find catory entities whose parent id is 0
                                 .filter(categoryEntity ->
                                                 categoryEntity.getParentCid() == 0)
                                 // Using map() to find children categories
                                 // and store them into current menu object
                                 .peek(categoryEntity ->
                                               categoryEntity.setChildrenCategory(
                                                       findChildrenCategory(categoryEntity, categoryEntityList)))
                                 // Sort the menu
                                 .sorted(Comparator.comparingInt(CategoryEntity::getSort)
                                         // can be replaced by Comparator.comparingInt
                                         // (previousMenu, nextMenu) -> {
                                         //     return previousMenu.getSort() - nextMenu.getSort();
                                         // }
                                 )
                                 // Wrap them to a list
                                 .collect(Collectors.toList());
    }

    /**
     * Recursively find all of the children categories of current category
     *
     * @param currentCategory            Current category,
     *                                   also is the parent category
     * @param findFromCategoryEntityList The complete list,
     *                                   from which to find sub-menus
     * @return The list of current category's children categories
     */
    private List<CategoryEntity> findChildrenCategory(
            CategoryEntity currentCategory,
            List<CategoryEntity> findFromCategoryEntityList) {
        return findFromCategoryEntityList.stream()
                                         .filter(categoryEntity ->
                                                         categoryEntity.getParentCid()
                                                                       .equals(currentCategory.getCatId()))
                                         // Recursively find the children categories
                                         .peek(categoryEntity ->
                                                       categoryEntity.setChildrenCategory(
                                                               findChildrenCategory(categoryEntity,
                                                                                    findFromCategoryEntityList)))
                                         .sorted(Comparator.comparingInt(CategoryEntity::getSort))
                                         .collect(Collectors.toList());
    }

    /**
     * @param asList
     */
    @Override
    public void removeCategoryByIds(List<Long> asList) {

        // Step 1: check for relevance to other categories

        /*
         * Step 2: only logical delete categories
         * not physical delete from database
         * i.e. change value of show_status column
         * make the categories wouldn't display on webpages
         */
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * Find all tree info of the given category
     *
     * @param catalogId categoryId of specified category
     * @return tree info, format: [parent, child, grandchild]
     */
    @Override
    public Long[] findCategoryPath(Long catalogId) {
        List<Long> path = new ArrayList<>();
        while (catalogId != 0) {
            path.add(catalogId);
            catalogId = this.getById(catalogId).getParentCid();
        }
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    /**
     * Also update related tables
     * @param category the category entity to be updated
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        // update brand table
        this.updateById(category);

        // update category_brand_relation table
        if (!StringUtils.isEmpty(category.getName())) {
            categoryBrandRelationService.updateCategory(category);
        }
    }

}