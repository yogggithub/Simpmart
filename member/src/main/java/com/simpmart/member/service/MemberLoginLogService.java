package com.simpmart.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * member login history
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

