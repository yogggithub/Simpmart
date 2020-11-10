package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * return application
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_order_return_apply")
public class OrderReturnApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * return order no.
	 */
	private Long orderId;
	/**
	 * return item sku id
	 */
	private Long skuId;
	/**
	 * original order no.
	 */
	private String orderSn;
	/**
	 * apply time
	 */
	private Date createTime;
	/**
	 * member username
	 */
	private String memberUsername;
	/**
	 * refund amount
	 */
	private BigDecimal returnAmount;
	/**
	 * return applicant name
	 */
	private String returnName;
	/**
	 * return applicant phone number
	 */
	private String returnPhone;
	/**
	 * application status[0->awaiting treatment, 1->Returning, 2->completed, 3->refused]
	 */
	private Integer status;
	/**
	 * application handle time
	 */
	private Date handleTime;
	/**
	 * sku picture
	 */
	private String skuImg;
	/**
	 * sku name
	 */
	private String skuName;
	/**
	 * sku brand
	 */
	private String skuBrand;
	/**
	 * sku attributes combination (JSON)
	 */
	private String skuAttrsVals;
	/**
	 * return quantity
	 */
	private Integer skuCount;
	/**
	 * sku price
	 */
	private BigDecimal skuPrice;
	/**
	 * grand total after deduction
	 */
	private BigDecimal skuRealPrice;
	/**
	 * return reason
	 */
	private String reason;
	/**
	 * application description
	 */
	private String description;
	/**
	 * application picture
	 */
	private String descPics;
	/**
	 * handling comment
	 */
	private String handleNote;
	/**
	 * application handler
	 */
	private String handleMan;
	/**
	 * consignee name
	 */
	private String receiveMan;
	/**
	 * delivery time
	 */
	private Date receiveTime;
	/**
	 * shipping comment
	 */
	private String receiveNote;
	/**
	 * consignee phone number
	 */
	private String receivePhone;
	/**
	 * company delivery address
	 */
	private String companyAddress;

}
