package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.entity.ProductAttrValueEntity;
import com.simpmart.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * spu attributes
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProdcutAttr(List<ProductAttrValueEntity> productAttrValueEntities);
}

