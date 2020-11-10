package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * purchase detail
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Data
@TableName("ware_purchase_detail")
public class PurchaseDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * purchase order id
	 */
	private Long purchaseId;
	/**
	 * purchased goods id
	 */
	private Long skuId;
	/**
	 * purchased quantity
	 */
	private Integer skuNum;
	/**
	 * purchased amount
	 */
	private BigDecimal skuPrice;
	/**
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * purchase order status [0 -> new, 1 -> assigned, 2 -> purchasing, 3 -> completed, 4 -> failure]
	 */
	private Integer status;

}
