package com.simpmart.commodity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.simpmart.commodity.entity.SpuInfoDescEntity;
import com.simpmart.commodity.vo.SpuSaveVo;
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

    void saveSpuInfo(SpuSaveVo vo);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

