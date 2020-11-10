package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * payment information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:34
 */
@Data
@TableName("ordr_payment_info")
public class PaymentInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order no.
	 */
	private String orderSn;
	/**
	 * order id
	 */
	private Long orderId;
	/**
	 * transaction no.
	 */
	private String paymentTradeNo;
	/**
	 * transaction total amount
	 */
	private BigDecimal totalAmount;
	/**
	 * transaction content
	 */
	private String subject;
	/**
	 * transaction status
	 */
	private String paymentStatus;
	/**
	 * transaction create time
	 */
	private Date createTime;
	/**
	 * transaction confirmation time
	 */
	private Date confirmTime;
	/**
	 * callback content
	 */
	private String callbackContent;
	/**
	 * callback time
	 */
	private Date callbackTime;

}
