package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * stock keeping unit information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_sku_info")
public class SkuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * skuId
	 */
	@TableId
	private Long skuId;
	/**
	 * spu Id
	 */
	private Long spuId;
	/**
	 * sku name
	 */
	private String skuName;
	/**
	 * sku description
	 */
	private String skuDesc;
	/**
	 * category id
	 */
	private Long catalogId;
	/**
	 * brand id
	 */
	private Long brandId;
	/**
	 * default image
	 */
	private String skuDefaultImg;
	/**
	 * sku title
	 */
	private String skuTitle;
	/**
	 * sku sub title
	 */
	private String skuSubtitle;
	/**
	 * price
	 */
	private BigDecimal price;
	/**
	 * sales volume
	 */
	private Long saleCount;

}
