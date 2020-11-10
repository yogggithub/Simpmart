package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.common.utils.PageUtils;
import com.simpmart.commodity.entity.SpuInfoEntity;

import java.util.Map;

/**
 * standard product unit infomation
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}
