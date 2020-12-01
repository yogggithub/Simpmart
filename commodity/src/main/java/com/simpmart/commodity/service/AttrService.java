package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.entity.AttrEntity;
import com.simpmart.commodity.vo.AttrGroupRelationVo;
import com.simpmart.commodity.vo.AttrResponseVo;
import com.simpmart.commodity.vo.AttrVo;
import com.simpmart.common.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * attribute
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String type);

    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getAttrRelation(Long attrGroupId);

    void deleteRelation(List<AttrGroupRelationVo> vos);

    PageUtils getAttrNoRelation(Map<String, Object> params, Long attrGroupId);
}

