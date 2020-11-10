package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * seckill activity section
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_seckill_session")
public class SeckillSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * seckill section name
	 */
	private String name;
	/**
	 * seckill section start time
	 */
	private Date startTime;
	/**
	 * seckill section end time
	 */
	private Date endTime;
	/**
	 * seckill section status
	 */
	private Integer status;
	/**
	 * seckill section create time
	 */
	private Date createTime;

}
