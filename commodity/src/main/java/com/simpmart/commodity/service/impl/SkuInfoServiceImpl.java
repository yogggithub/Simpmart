package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.SkuInfoDao;
import com.simpmart.commodity.entity.SkuInfoEntity;
import com.simpmart.commodity.service.SkuInfoService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @param skuInfoEntity
     */
    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<SkuInfoEntity>();
        String key = (String) params.get("key");

        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> {
                w.like(SkuInfoEntity::getSkuName, key)
                 .or().like(SkuInfoEntity::getSkuSubtitle, key);
                if (key.matches("^[0-9]+$")) {
                    w.or().eq(SkuInfoEntity::getSkuId, key)
                     .or().eq(SkuInfoEntity::getBrandId, key)
                     .or().eq(SkuInfoEntity::getCatalogId, key);
                }
            });
        }
        String catalogId = (String) params.get("catalogId");
        if (!StringUtils.isEmpty(catalogId) &&
            !catalogId.equalsIgnoreCase("0")) {
            wrapper.eq(SkuInfoEntity::getCatalogId, catalogId);
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId) &&
            !brandId.equalsIgnoreCase("0")) {
            wrapper.eq(SkuInfoEntity::getBrandId, brandId);
        }
        String min = (String) params.get("min");
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(min)) {
            wrapper.gt(SkuInfoEntity::getPrice, min);
        }

        if (!StringUtils.isEmpty(max)) {
            // there is no meaning when max is 0
            BigDecimal maxPrice = new BigDecimal(max);
            if (maxPrice.compareTo(new BigDecimal(0)) > 0) {
                wrapper.lt(SkuInfoEntity::getPrice, max);
            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

}