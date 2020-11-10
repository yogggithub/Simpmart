package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member price
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_member_price")
public class MemberPriceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku id
	 */
	private Long skuId;
	/**
	 * member level id
	 */
	private Long memberLevelId;
	/**
	 * member level name
	 */
	private String memberLevelName;
	/**
	 * member price
	 */
	private BigDecimal memberPrice;
	/**
	 * can used with other bargains [0->no, 1->yes]
	 */
	private Integer addOther;

}
