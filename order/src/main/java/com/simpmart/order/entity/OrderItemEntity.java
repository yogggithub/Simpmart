package com.simpmart.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * order item
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:12:35
 */
@Data
@TableName("ordr_order_item")
public class OrderItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * order_id
	 */
	private Long orderId;
	/**
	 * order_sn
	 */
	private String orderSn;
	/**
	 * spu_id
	 */
	private Long spuId;
	/**
	 * spu_name
	 */
	private String spuName;
	/**
	 * spu_pic
	 */
	private String spuPic;
	/**
	 * brand
	 */
	private String spuBrand;
	/**
	 * category id
	 */
	private Long categoryId;
	/**
	 * sku no.
	 */
	private Long skuId;
	/**
	 * sku name
	 */
	private String skuName;
	/**
	 * sku picture
	 */
	private String skuImg;
	/**
	 * sku price
	 */
	private BigDecimal skuPrice;
	/**
	 * item(s) ordered quantity
	 */
	private Integer skuQuantity;
	/**
	 * sku attributes combination (JSON)
	 */
	private String skuAttrsVals;
	/**
	 * promotion amount
	 */
	private BigDecimal promotionAmount;
	/**
	 * coupon amount
	 */
	private BigDecimal couponAmount;
	/**
	 * member rewards amount
	 */
	private BigDecimal integrationAmount;
	/**
	 * grand total after deduction
	 */
	private BigDecimal realAmount;
	/**
	 * bonus member rewards
	 */
	private Integer giftIntegration;
	/**
	 * bonus growth points
	 */
	private Integer giftGrowth;

}
