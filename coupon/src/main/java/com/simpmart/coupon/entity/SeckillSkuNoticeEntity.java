package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * seckill subcribe
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_seckill_sku_notice")
public class SeckillSkuNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member id
	 */
	private Long memberId;
	/**
	 * sku id
	 */
	private Long skuId;
	/**
	 * seckill section id
	 */
	private Long sessionId;
	/**
	 * member subcribe time
	 */
	private Date subcribeTime;
	/**
	 * subcribe notify time
	 */
	private Date sendTime;
	/**
	 * notify method [0->text, 1->email]
	 */
	private Integer noticeType;

}
