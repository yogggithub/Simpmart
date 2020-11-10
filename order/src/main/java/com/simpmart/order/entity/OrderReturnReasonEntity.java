package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * return reason
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_order_return_reason")
public class OrderReturnReasonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * return reason
	 */
	private String name;
	/**
	 * reason sequence
	 */
	private Integer sort;
	/**
	 * reason status
	 */
	private Integer status;
	/**
	 * reason create time
	 */
	private Date createTime;

}
