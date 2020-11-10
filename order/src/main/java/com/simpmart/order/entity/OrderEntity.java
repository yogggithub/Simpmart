package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_order")
public class OrderEntity implements Serializable {
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
	 * order no.
	 */
	private String orderSn;
	/**
	 * coupon used
	 */
	private Long couponId;
	/**
	 * order create time
	 */
	private Date createTime;
	/**
	 * member username
	 */
	private String memberUsername;
	/**
	 * item(s) subtotal amount
	 */
	private BigDecimal totalAmount;
	/**
	 * grand total amount
	 */
	private BigDecimal payAmount;
	/**
	 * shipping and handling costs
	 */
	private BigDecimal freightAmount;
	/**
	 * promotional Optimization Amount
	 */
	private BigDecimal promotionAmount;
	/**
	 * membership rewards
	 */
	private BigDecimal integrationAmount;
	/**
	 * coupon credit
	 */
	private BigDecimal couponAmount;
	/**
	 * adjusted discounts of order
	 */
	private BigDecimal discountAmount;
	/**
	 * payment method [1->credit card, 2->Paypal, 3->debit card, 4->cash on delivery]
	 */
	private Integer payType;
	/**
	 * order source[0->pc, 1->mobile]
	 */
	private Integer sourceType;
	/**
	 * order status[0->pending payment, 1->awaiting delivery, 2->shipped, 3->completed, 4->closed, 5->invalid]
	 */
	private Integer status;
	/**
	 * logistics company
	 */
	private String deliveryCompany;
	/**
	 * shipment number
	 */
	private String deliverySn;
	/**
	 * automatic confirmation time (days)
	 */
	private Integer autoConfirmDay;
	/**
	 * member rewards to be earned
	 */
	private Integer integration;
	/**
	 * growth points to be earned
	 */
	private Integer growth;
	/**
	 * invoice type[0->not required, 1->electronic, 2->printed]
	 */
	private Integer billType;
	/**
	 * invoice recipient
	 */
	private String billHeader;
	/**
	 * contents of invoice
	 */
	private String billContent;
	/**
	 * recipient phone number
	 */
	private String billReceiverPhone;
	/**
	 * recipient email
	 */
	private String billReceiverEmail;
	/**
	 * consignee name
	 */
	private String receiverName;
	/**
	 * consignee phone number
	 */
	private String receiverPhone;
	/**
	 * consignee postcode
	 */
	private String receiverPostCode;
	/**
	 * state/province/region/territory
	 */
	private String receiverProvince;
	/**
	 * city
	 */
	private String receiverCity;
	/**
	 * address line 1 (street address/P.O. box)
	 */
	private String receiverRegion;
	/**
	 * address line 2 (appartment/floor/suite/unit)
	 */
	private String receiverDetailAddress;
	/**
	 * order comments
	 */
	private String note;
	/**
	 * confirm status[0->unconfirmed, 1->confirmed]
	 */
	private Integer confirmStatus;
	/**
	 * delete status[0->non-deleted, 1->deleted]
	 */
	private Integer deleteStatus;
	/**
	 * member rewards used when place order
	 */
	private Integer useIntegration;
	/**
	 * payment time
	 */
	private Date paymentTime;
	/**
	 * shipping time
	 */
	private Date deliveryTime;
	/**
	 * shipped confirmation time
	 */
	private Date receiveTime;
	/**
	 * review time
	 */
	private Date commentTime;
	/**
	 * review modify time
	 */
	private Date modifyTime;

}
