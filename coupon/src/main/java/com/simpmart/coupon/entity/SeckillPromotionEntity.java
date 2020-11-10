package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * seckill activity
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_seckill_promotion")
public class SeckillPromotionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * seckill activity title
	 */
	private String title;
	/**
	 * seckill activity start time
	 */
	private Date startTime;
	/**
	 * seckill activity end time
	 */
	private Date endTime;
	/**
	 * seckill activity status
	 */
	private Integer status;
	/**
	 * seckill activity create time
	 */
	private Date createTime;
	/**
	 * seckill activity creator
	 */
	private Long userId;

}
