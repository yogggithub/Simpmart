package com.simpmart.member.dao;

import com.simpmart.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * member
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
