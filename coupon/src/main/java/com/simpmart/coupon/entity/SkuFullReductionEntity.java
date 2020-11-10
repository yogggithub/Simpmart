package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * price-break discount
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_sku_full_reduction")
public class SkuFullReductionEntity implements Serializable {
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
	 * discount breakpoint amount
	 */
	private BigDecimal fullPrice;
	/**
	 * discount amount
	 */
	private BigDecimal reducePrice;
	/**
	 * eligible for other offers [0->no, 1->yes]
	 */
	private Integer addOther;

}
