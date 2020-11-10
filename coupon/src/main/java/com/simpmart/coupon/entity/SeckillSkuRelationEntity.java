package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * seckill activity & product relationship
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * seckill activity id
	 */
	private Long promotionId;
	/**
	 * seckill activity section id
	 */
	private Long promotionSessionId;
	/**
	 * product id
	 */
	private Long skuId;
	/**
	 * seckill price
	 */
	private BigDecimal seckillPrice;
	/**
	 * seckill quantity
	 */
	private BigDecimal seckillCount;
	/**
	 * purchase limit per person
	 */
	private BigDecimal seckillLimit;
	/**
	 * seckill sequence
	 */
	private Integer seckillSort;

}
