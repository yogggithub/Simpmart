package com.simpmart.commodity.dao;

import com.simpmart.commodity.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * category
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
