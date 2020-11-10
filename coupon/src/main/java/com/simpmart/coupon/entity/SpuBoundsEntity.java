package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu bonus settings
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_spu_bounds")
public class SpuBoundsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long spuId;
	/**
	 * member growth point
	 */
	private BigDecimal growBounds;
	/**
	 * member shopping point
	 */
	private BigDecimal buyBounds;
	/**
	 * promotion status, 4 bits [from left to right, first->no discount, bonus growth point [0->no, 1->yes], second->no discount, bonus shopping point [0->no, 1->yes], third->discounted, bonus growth point [0->no, 1->yes], forth->discounted, bonus shopping point [0->no, 1->yes]
	 */
	private Integer work;

}
