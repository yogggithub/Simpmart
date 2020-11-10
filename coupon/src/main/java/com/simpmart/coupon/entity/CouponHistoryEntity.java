package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * coupon collection history
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_coupon_history")
public class CouponHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * coupon id
	 */
	private Long couponId;
	/**
	 * member id
	 */
	private Long memberId;
	/**
	 * member name
	 */
	private String memberNickName;
	/**
	 * coupon1 collection method [0->passivity, 1->activity]
	 */
	private Integer getType;
	/**
	 * coupon create time
	 */
	private Date createTime;
	/**
	 * used status [0->unused, 1->used, 2->expired]
	 */
	private Integer useType;
	/**
	 * used time
	 */
	private Date useTime;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * order no.
	 */
	private Long orderSn;

}
