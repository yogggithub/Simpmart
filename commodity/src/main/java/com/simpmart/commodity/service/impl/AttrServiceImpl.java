package com.simpmart.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simpmart.commodity.dao.AttrAttrgroupRelationDao;
import com.simpmart.commodity.dao.AttrDao;
import com.simpmart.commodity.dao.AttrGroupDao;
import com.simpmart.commodity.dao.CategoryDao;
import com.simpmart.commodity.entity.AttrAttrgroupRelationEntity;
import com.simpmart.commodity.entity.AttrEntity;
import com.simpmart.commodity.entity.AttrGroupEntity;
import com.simpmart.commodity.entity.CategoryEntity;
import com.simpmart.commodity.service.AttrService;
import com.simpmart.commodity.service.CategoryService;
import com.simpmart.commodity.vo.AttrGroupRelationVo;
import com.simpmart.commodity.vo.AttrResponseVo;
import com.simpmart.commodity.vo.AttrVo;
import com.simpmart.common.constant.CommodityConst;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.common.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }


    @Autowired
    AttrAttrgroupRelationDao relationDao;

    /**
     * cascade update attribute entity and
     * attribute-group-relationship
     *
     * @param attr
     */
    @Transactional
    @Override
    public void saveAttrVo(AttrVo attr) {
        // save base column
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.save(attrEntity);

        // save relationship
        if (attr.getAttrType() ==
            CommodityConst.AttrEnum.ATTR_TYPE_BASE.getCode() &&
            attr.getAttrGroupId() != null) {

            AttrAttrgroupRelationEntity relationEntity =
                    new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;

    /**
     * Get base attribute list
     * implements fuzzy search
     *
     * @param params
     * @param catalogId
     * @param type
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catalogId, String type) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrEntity> lambdaWrapper =
                new LambdaQueryWrapper<AttrEntity>()
                        .eq(AttrEntity::getAttrType,
                            "base".equalsIgnoreCase(type) ?
                                    CommodityConst.AttrEnum.ATTR_TYPE_BASE.getCode() :
                                    CommodityConst.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (catalogId != 0) {
            lambdaWrapper.eq(AttrEntity::getCatalogId, catalogId);
        }
        if (!StringUtils.isEmpty(key)) {
            lambdaWrapper.and(wrapper -> {
                wrapper.like(AttrEntity::getAttrName, key);
                if (key.matches("^[0-9]+$")) {
                    wrapper.or().eq(AttrEntity::getAttrId, key);
                }
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                lambdaWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> responseVos =
                records.stream()
                       .map(entity -> {
                           AttrResponseVo attrRespVo = new AttrResponseVo();
                           BeanUtils.copyProperties(entity, attrRespVo);

                           if ("base".equalsIgnoreCase(type)) {
                               // update group names
                               AttrAttrgroupRelationEntity relationEntity =
                                       relationDao.selectOne(
                                               new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                                                       .eq(AttrAttrgroupRelationEntity::getAttrId,
                                                           attrRespVo.getAttrId()));
                               if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                                   attrRespVo.setGroupName(
                                           attrGroupDao
                                                   .selectById(relationEntity.getAttrGroupId())
                                                   .getAttrGroupName());
                               }
                           }
                           // update category name
                           CategoryEntity categoryEntity =
                                   categoryDao.selectById(entity.getCatalogId());
                           if (categoryEntity != null) {
                               attrRespVo.setCatalogName(categoryEntity.getName());
                           }
                           return attrRespVo;
                       }).collect(Collectors.toList());
        pageUtils.setList(responseVos);
        return pageUtils;
    }

    @Autowired
    CategoryService categoryService;

    /**
     * @param attrId
     * @return
     */
    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        AttrResponseVo responseVo = new AttrResponseVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity, responseVo);

        if (attrEntity.getAttrType() ==
            CommodityConst.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // update group info
            AttrAttrgroupRelationEntity relationEntity =
                    relationDao.selectOne(
                            new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                                    .eq(AttrAttrgroupRelationEntity::getAttrId,
                                        responseVo.getAttrId()));
            if (relationEntity != null) {
                responseVo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity groupEntity =
                        attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (groupEntity != null) {
                    responseVo.setGroupName(groupEntity.getAttrGroupName());
                }
            }
        }
        // update full category path
        Long[] catalogPath =
                categoryService.findCategoryPath(attrEntity.getCatalogId());
        responseVo.setCatalogPath(catalogPath);

        return responseVo;
    }

    /**
     * @param attr
     */
    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attr.getAttrType() ==
            CommodityConst.AttrEnum.ATTR_TYPE_BASE.getCode()) {
            // update attribute-group-relationship
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attr.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            if (relationDao.selectCount(
                    new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                            .eq(AttrAttrgroupRelationEntity::getAttrId,
                                attr.getAttrId())) > 0) {
                relationDao.update(relationEntity,
                                   new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>()
                                           .eq(AttrAttrgroupRelationEntity::getAttrId,
                                               attr.getAttrId()));
            } else {
                relationDao.insert(relationEntity);
            }
        }
    }

    /**
     * delete attribute-group relationship
     *
     * @param vos
     */
    @Override
    public void deleteRelation(List<AttrGroupRelationVo> vos) {
        List<AttrAttrgroupRelationEntity> relationEntityList = vos.stream().map(vo -> {
            AttrAttrgroupRelationEntity relationEntity =
                    new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(vo, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(relationEntityList);
    }

    /**
     * get all attributes that related to given group
     *
     * @param attrGroupId
     * @return
     */
    @Override
    public List<AttrEntity> getAttrRelation(Long attrGroupId) {
        List<Long> list = relationDao.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId))
                                     .stream()
                                     .map(AttrAttrgroupRelationEntity::getAttrId)
                                     .collect(Collectors.toList());
        if (list.size() == 0) {
            return null;
        }
        return this.listByIds(list);
    }


    /**
     * get all attribute that dont related to any group
     *
     * @param params
     * @param attrGroupId
     * @return
     */
    @Override
    public PageUtils getAttrNoRelation(Map<String, Object> params, Long attrGroupId) {

        /*
           rules:
           1. All group only can connect to the attributes that belong to this group's category
           2. All group only can connect to the attributes currently do not have connection
         */
        Long catalogId = attrGroupDao.selectById(attrGroupId).getCatalogId();

        // 2.1 the other groups belong to this group's category
        List<AttrGroupEntity> group = attrGroupDao.selectList(
                new LambdaQueryWrapper<AttrGroupEntity>()
                        .eq(AttrGroupEntity::getCatalogId, catalogId));
        List<Long> otherGroups = group.stream()
                                      .map(AttrGroupEntity::getAttrGroupId)
                                      .collect(Collectors.toList());
        // 2.2 all attributes connected to the other groups
        List<Long> attrList = relationDao.selectList(
                new LambdaQueryWrapper<AttrAttrgroupRelationEntity>()
                        .in(AttrAttrgroupRelationEntity::getAttrGroupId, otherGroups))
                                         .stream()
                                         .map(AttrAttrgroupRelationEntity::getAttrId)
                                         .collect(Collectors.toList());
        // 2.3 exclude those attributes that already get connected
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<AttrEntity>()
                .eq(AttrEntity::getCatalogId, catalogId)
                .eq(AttrEntity::getAttrType,
                    CommodityConst.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (!CollectionUtils.isEmpty(attrList)) {
            wrapper.notIn(AttrEntity::getAttrId, attrList);
        }

        // fuzzy search
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> w.eq(AttrEntity::getAttrId, key)
                              .or().like(AttrEntity::getAttrName, key)
            );
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params), wrapper
        );

        return new PageUtils(page);
    }
}