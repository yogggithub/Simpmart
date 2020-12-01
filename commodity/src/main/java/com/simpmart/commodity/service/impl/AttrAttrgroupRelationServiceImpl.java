package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.AttrAttrgroupRelationDao;
import com.simpmart.commodity.entity.AttrAttrgroupRelationEntity;
import com.simpmart.commodity.service.AttrAttrgroupRelationService;
import com.simpmart.commodity.vo.AttrGroupRelationVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * batch save
     *
     * @param vos
     */
    @Override
    public void saveBatch(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> relationEntityList =
                vos.stream().map(vo -> {
                    AttrAttrgroupRelationEntity entity =
                            new AttrAttrgroupRelationEntity();
                    BeanUtils.copyProperties(vo, entity);
                    return entity;
                }).collect(Collectors.toList());
        this.saveBatch(relationEntityList);
    }
}