package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.BrandDao;
import com.simpmart.commodity.entity.BrandEntity;
import com.simpmart.commodity.service.BrandService;
import com.simpmart.commodity.service.CategoryBrandRelationService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    /**
     * Fuzzy search
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<BrandEntity> lambdaWrapper =
                new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            lambdaWrapper.and(wrapper -> {
                wrapper.like(BrandEntity::getName, key)
                       .or().like(BrandEntity::getDescription, key);
                if (key.matches("^[0-9]+$")) {
                    wrapper.or().eq(BrandEntity::getBrandId, key);
                }
            });
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                lambdaWrapper
        );
        return new PageUtils(page);
    }

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
    /**
     * Also update related tables
     * @param brand the brand entity to be updated
     */
    @Transactional
    @Override
    public void updateCascade(BrandEntity brand) {
        // update brand table
        this.updateById(brand);

        // update category_brand_relation table
        if (!StringUtils.isEmpty(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand);
        }
    }

}