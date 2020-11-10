package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order setting information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:34
 */
@Data
@TableName("ordr_order_setting")
public class OrderSettingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * lightning deal timeout (minutes)
	 */
	private Integer flashOrderOvertime;
	/**
	 * regular timeout (minutes)
	 */
	private Integer normalOrderOvertime;
	/**
	 * automatic confirmation time (days)
	 */
	private Integer confirmOvertime;
	/**
	 * auto completion time, return window closed time (days)
	 */
	private Integer finishOvertime;
	/**
	 * automatically review time after order completion (days)
	 */
	private Integer commentOvertime;
	/**
	 * required member level[0->unlimited, other->corresponding member level]
	 */
	private Integer memberLevel;

}
