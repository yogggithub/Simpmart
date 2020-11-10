package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * refund information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_refund_info")
public class RefundInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * return id
	 */
	private Long orderReturnId;
	/**
	 * refund amount
	 */
	private BigDecimal refund;
	/**
	 * refund transaction no.
	 */
	private String refundSn;
	/**
	 * refund status
	 */
	private Integer refundStatus;
	/**
	 * refund method [1->credit card, 2->Paypal, 3->debit card, 4->cheque]
	 */
	private Integer refundChannel;
	/**
	 * refund content
	 */
	private String refundContent;

}
