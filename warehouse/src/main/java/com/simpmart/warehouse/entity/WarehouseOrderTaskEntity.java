package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ware task
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Data
@TableName("ware_warehouse_order_task")
public class WarehouseOrderTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * order sn
	 */
	private String orderSn;
	/**
	 * consignee name
	 */
	private String consignee;
	/**
	 * consignee phone number
	 */
	private String consigneeTel;
	/**
	 * shipping address
	 */
	private String deliveryAddress;
	/**
	 * order note
	 */
	private String orderComment;
	/**
	 * payment method [1 -> online payment, 2 -> cash on delivery]
	 */
	private Integer paymentWay;
	/**
	 * task status
	 */
	private Integer taskStatus;
	/**
	 * order description
	 */
	private String orderBody;
	/**
	 * logistic number
	 */
	private String trackingNo;
	/**
	 * create time
	 */
	private Date createTime;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * task note
	 */
	private String taskComment;

}
