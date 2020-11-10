package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * sku images
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_sku_images")
public class SkuImagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * sku id
	 */
	private Long skuId;
	/**
	 * sku images link
	 */
	private String imgUrl;
	/**
	 * sku sequence
	 */
	private Integer imgSort;
	/**
	 * is default image [0->no, 1->yes]
	 */
	private Integer defaultImg;

}
