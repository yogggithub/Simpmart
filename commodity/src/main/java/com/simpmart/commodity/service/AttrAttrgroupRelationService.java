package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.entity.AttrAttrgroupRelationEntity;
import com.simpmart.commodity.vo.AttrGroupRelationVo;
import com.simpmart.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * attributes & attribute group relationship
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveBatch(List<AttrGroupRelationVo> vos);
}

