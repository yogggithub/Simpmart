package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * purchase information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:01
 */
@Data
@TableName("ware_purchase")
public class PurchaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * purchase order id
	 */
	@TableId
	private Long id;
	/**
	 * purchasing agent id
	 */
	private Long assigneeId;
	/**
	 * purchasing agent name
	 */
	private String assigneeName;
	/**
	 * purchasing agent phone number
	 */
	private String phone;
	/**
	 * priority
	 */
	private Integer priority;
	/**
	 * status
	 */
	private Integer status;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * total purchasing amount
	 */
	private BigDecimal amount;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * update time
	 */
	private Date updateTime;

}
