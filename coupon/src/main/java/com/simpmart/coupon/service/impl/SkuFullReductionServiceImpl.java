package com.simpmart.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.common.to.SkuPromotionTo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import com.simpmart.coupon.dao.SkuFullReductionDao;
import com.simpmart.coupon.entity.MemberPriceEntity;
import com.simpmart.coupon.entity.SkuFullReductionEntity;
import com.simpmart.coupon.entity.SkuLadderEntity;
import com.simpmart.coupon.service.MemberPriceService;
import com.simpmart.coupon.service.SkuFullReductionService;
import com.simpmart.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    SkuLadderService skuLadderService;
    @Autowired
    SkuFullReductionService skuFullReductionService;
    @Autowired
    MemberPriceService memberPriceService;

    /**
     * @param skuPromotionTo
     */
    @Override
    public void saveSkuPromotion(SkuPromotionTo skuPromotionTo) {
        // sku_ladder: ladder price, buy exceed ** count, get ** off
        if (skuPromotionTo.getFullCount() > 0) {
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            skuLadderEntity.setSkuId(skuPromotionTo.getSkuId());
            skuLadderEntity.setFullCount(skuPromotionTo.getFullCount());
            skuLadderEntity.setDiscount(skuPromotionTo.getDiscount());
            skuLadderEntity.setAddOther(skuPromotionTo.getCountStatus());

            skuLadderService.save(skuLadderEntity);
        }
        // sku_full_reduction: full reduction, pay over ** dollar, get ** reduction
        if (skuPromotionTo.getFullPrice().compareTo(new BigDecimal(0)) > 0) {
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuPromotionTo, skuFullReductionEntity);

            skuFullReductionService.save(skuFullReductionEntity);
        }

        // member_price: special price for member, may vary for different member level

        memberPriceService.saveBatch(
                skuPromotionTo.getMemberPrice()
                              .stream()
                              .map(price -> {
                                  MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                                  memberPriceEntity.setSkuId(skuPromotionTo.getSkuId());
                                  memberPriceEntity.setMemberLevelId(price.getId());
                                  memberPriceEntity.setMemberLevelName(price.getName());
                                  memberPriceEntity.setMemberPrice(price.getPrice());
                                  memberPriceEntity.setAddOther(1);
                                  // default value: 1, means could enjoy with other promotions
                                  return memberPriceEntity;
                              })
                              .filter(price ->
                                              price.getMemberPrice()
                                                   .compareTo(new BigDecimal(0)) > 0)
                              .collect(Collectors.toList()));
    }

}