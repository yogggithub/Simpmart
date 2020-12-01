package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.SpuInfoDao;
import com.simpmart.commodity.dao.SpuInfoDescDao;
import com.simpmart.commodity.entity.*;
import com.simpmart.commodity.feign.CouponSpuFeignService;
import com.simpmart.commodity.service.*;
import com.simpmart.commodity.vo.ImagesVo;
import com.simpmart.commodity.vo.SkusVo;
import com.simpmart.commodity.vo.SpuSaveVo;
import com.simpmart.common.to.SkuPromotionTo;
import com.simpmart.common.to.SpuBoundsTo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    SpuImagesService spuImagesService;

    @Autowired
    AttrService attrService;

    @Autowired
    ProductAttrValueService attrValueService;

    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImagesService;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    CouponSpuFeignService couponSpuFeignService;

    /**
     * @param vo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {

        // 1. save spu basic info: spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        // 2. save images that used in spu description: spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDescription(String.join(",",
                                                     vo.getSpuDescription()));
        this.saveSpuInfoDesc(spuInfoDescEntity);

        // 3. save images that used as product post: spu_images
        spuImagesService.saveImages(spuInfoEntity.getId(), vo.getImages());

        // 4. save spu's base attributes: product_attr_value

        List<ProductAttrValueEntity> productAttrValueEntities =
                vo.getBaseAttrs().stream().map(attr -> {
                    ProductAttrValueEntity valueEntity =
                            new ProductAttrValueEntity();
                    valueEntity.setAttrId(attr.getAttrId());
                    valueEntity.setAttrName(
                            attrService.getById(attr.getAttrId()).getAttrName());
                    valueEntity.setAttrValue(attr.getAttrValues());
                    valueEntity.setQuickShow(attr.getShowDesc());
                    valueEntity.setSpuId(spuInfoEntity.getId());
                    return valueEntity;
                }).collect(Collectors.toList());
        attrValueService.saveProdcutAttr(productAttrValueEntities);

        // 5. save all skus belong to current spu:
        List<SkusVo> skusVos = vo.getSkus();
        if (skusVos != null && skusVos.size() > 0) {

            // sku_info
            skusVos.stream().map(skusVo -> {
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(skusVo, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L); // 0 as default value
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                String defaultImg = "";
                for (ImagesVo image : skusVo.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        defaultImg = image.getImgUrl();
                    }
                }
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(skuInfoEntity);

                // sku_images
                List<SkuImagesEntity> imagesEntities =
                        skusVo.getImages().stream().map(img -> {
                            SkuImagesEntity imagesEntity = new SkuImagesEntity();
                            imagesEntity.setSkuId(skuInfoEntity.getSkuId());
                            imagesEntity.setImgUrl(img.getImgUrl());
                            imagesEntity.setDefaultImg(img.getDefaultImg());
                            return imagesEntity;
                        }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);

                //sku_sale_attr_value
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities =
                        skusVo.getAttr().stream().map(attr -> {
                            SkuSaleAttrValueEntity skuSaleAttrValueEntity
                                    = new SkuSaleAttrValueEntity();
                            BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                            skuSaleAttrValueEntity.setSkuId(skuInfoEntity.getSkuId());
                            return skuSaleAttrValueEntity;
                        }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);


                // 6. sales info: related to other database simp_prom
                // sku_ladder / sku_full_reduction / member_price
                // spu_bounds
                SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
                spuBoundsTo.setSpuId(spuInfoEntity.getId());
                BeanUtils.copyProperties(vo.getBounds(), spuBoundsTo);
                couponSpuFeignService.saveSpuBounds(spuBoundsTo);

                SkuPromotionTo skuPromotionTo = new SkuPromotionTo();
                BeanUtils.copyProperties(skusVo, skuPromotionTo);
                skuPromotionTo.setSkuId(skuInfoEntity.getSkuId());
                couponSpuFeignService.saveSkuPromotion(skuPromotionTo);

                return null;
            });

        }
    }

    /**
     * @param spuInfoEntity
     */
    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Autowired
    SpuInfoDescDao spuInfoDescDao;

    /**
     * @param spuInfoDescEntity
     */
    @Override
    public void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity) {
        this.spuInfoDescDao.insert(spuInfoDescEntity);
    }


}