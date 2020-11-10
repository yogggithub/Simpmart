package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * coupon information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_coupon")
public class CouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * coupontype [0->store wide bonus, 1->member gift, 2->shopping bonus, 3->register gift]
	 */
	private Integer couponType;
	/**
	 * coupon image
	 */
	private String couponImg;
	/**
	 * coupon name
	 */
	private String couponName;
	/**
	 * coupon quantity
	 */
	private Integer num;
	/**
	 * coupon amount
	 */
	private BigDecimal amount;
	/**
	 * limitatiton per customer
	 */
	private Integer perLimit;
	/**
	 * usage threshold
	 */
	private BigDecimal minPoint;
	/**
	 * coupon start time
	 */
	private Date startTime;
	/**
	 * coupon end time
	 */
	private Date endTime;
	/**
	 * usage type [0->store wide, 1->specific catory, 2->specific item]
	 */
	private Integer useType;
	/**
	 * coupon note
	 */
	private String note;
	/**
	 * publish quantity
	 */
	private Integer publishCount;
	/**
	 * used quantity
	 */
	private Integer useCount;
	/**
	 * collected quantity
	 */
	private Integer receiveCount;
	/**
	 * collection period start time
	 */
	private Date enableStartTime;
	/**
	 * collection period end time
	 */
	private Date enableEndTime;
	/**
	 * promo code
	 */
	private String code;
	/**
	 * member level threshold [0->no limitatiton, other->corresponding member level]
	 */
	private Integer memberLevel;
	/**
	 * publish status [0->unpublished, 1->published]
	 */
	private Integer publish;

}
