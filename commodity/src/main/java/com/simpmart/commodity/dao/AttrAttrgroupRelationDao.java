package com.simpmart.commodity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simpmart.commodity.entity.AttrAttrgroupRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * attributes & attribute group relationship
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelation(
            @Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
