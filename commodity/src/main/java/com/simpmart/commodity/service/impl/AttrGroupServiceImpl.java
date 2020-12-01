package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.AttrGroupDao;
import com.simpmart.commodity.entity.AttrGroupEntity;
import com.simpmart.commodity.service.AttrGroupService;
import com.simpmart.commodity.service.AttrService;
import com.simpmart.commodity.vo.AttrGroupWithAttrsVo;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catalogId) {
        /*
         * If the request body contains a key param,
         * then fuzzy search every column
         * the sql statement should be:
         * select * from comm_attr_group where catalog_id = ? and
         * (attr_group_name like '%keyword%' or description like '%keyword%')
         * when the key is numeric, extra search attr_group_id
         *
         * especially, when catalogId is 0, ignore the catalog_id, and
         * fuzzy search through all attribute groups
         */
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> lambdaWrapper = new LambdaQueryWrapper<>();

        if (catalogId != 0) {
            lambdaWrapper.eq(AttrGroupEntity::getCatalogId, catalogId);
        }
        if (!StringUtils.isEmpty(key)) {
            lambdaWrapper.and(wrapper -> {
                wrapper.like(AttrGroupEntity::getAttrGroupName, key)
                       .or().like(AttrGroupEntity::getDescription, key);
                if (key.matches("^[0-9]+$")) {
                    wrapper.or().eq(AttrGroupEntity::getAttrGroupId, key);
                }
            });
        }

        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                lambdaWrapper
        );
        return new PageUtils(page);

        /*
         * QueryWrapper need to get column name literal and correct,
         * so change to LambdaQueryWrapper provided by MybatisPlus
         */
            /*QueryWrapper<AttrGroupEntity> wrapper =
                    new QueryWrapper<AttrGroupEntity>()
                            .eq("catalog_id", catalogId);
            if (!StringUtils.isEmpty(key)) {
                wrapper.and((obj) -> {
                    obj.like("attr_group_name", key)
                            .or().like("description", key);
                    if (key.matches("^[0-9]+$")) {
                        obj.or().eq("attr_group_id", key);
                    }
                });
            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);*/
    }

    @Autowired
    AttrService attrService;

    /**
     * @param catalogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getGroupWithAttr(Long catalogId) {
        // get all groups
        List<AttrGroupEntity> groupEntities = this.list(
                new LambdaUpdateWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCatalogId, catalogId));
        // get all attrs
        return groupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, vo);
            vo.setAttrs(attrService.getAttrRelation(vo.getAttrGroupId()));
            return vo;
        }).collect(Collectors.toList());

    }
}