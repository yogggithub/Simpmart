package com.simpmart.member.dao;

import com.simpmart.member.entity.GrowthChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * member growth point history
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@Mapper
public interface GrowthChangeHistoryDao extends BaseMapper<GrowthChangeHistoryEntity> {
	
}
