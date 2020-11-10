package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu attributes
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_product_attr_value")
public class ProductAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * product id
	 */
	private Long spuId;
	/**
	 * attribute id
	 */
	private Long attrId;
	/**
	 * attribute name
	 */
	private String attrName;
	/**
	 * attribute value
	 */
	private String attrValue;
	/**
	 * attribute sequence
	 */
	private Integer attrSort;
	/**
	 * displayed on the product [0->no, 1->yes]
	 */
	private Integer quickShow;

}
