package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * quantity discount
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_sku_ladder")
public class SkuLadderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * spu id
	 */
	private Long skuId;
	/**
	 * bought quantity
	 */
	private Integer fullCount;
	/**
	 * discount
	 */
	private BigDecimal discount;
	/**
	 * price after discount
	 */
	private BigDecimal price;
	/**
	 * eligible for other offers [0->no, 1->yes]
	 */
	private Integer addOther;

}
