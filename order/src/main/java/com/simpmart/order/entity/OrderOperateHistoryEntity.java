package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order operate history
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_order_operate_history")
public class OrderOperateHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order no.
	 */
	private Long orderId;
	/**
	 * operator[member, system, administrator]
	 */
	private String operateMan;
	/**
	 * operating time
	 */
	private Date createTime;
	/**
	 * order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]
	 */
	private Integer orderStatus;
	/**
	 * order comments
	 */
	private String note;

}
