package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.vo.AttrGroupWithAttrsVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.commodity.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * attribute groups
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catalogId);

    List<AttrGroupWithAttrsVo> getGroupWithAttr(Long catalogId);
}

