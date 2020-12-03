package com.simpmart.warehouse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.simpmart.warehouse.entity.WarehouseSkuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * production inventory
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Mapper
public interface WarehouseSkuDao extends BaseMapper<WarehouseSkuEntity> {

    void addStock(@Param("skuId") Long skuId,
                  @Param("wareId") Long wareId,
                  @Param("skuNum") Integer skuNum);
}
