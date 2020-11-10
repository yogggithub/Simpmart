package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * attribute
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * attribute id
	 */
	@TableId
	private Long attrId;
	/**
	 * attribute name
	 */
	private String attrName;
	/**
	 * need to search [0->no, 1->yes]
	 */
	private Integer searchType;
	/**
	 * attribute icon
	 */
	private String icon;
	/**
	 * List of available values, seperated by comma
	 */
	private String valueSelect;
	/**
	 * attribute type [0->sales, 1->basic, 2->both]
	 */
	private Integer attrType;
	/**
	 * attribute status [0->disable, 1->enable]
	 */
	private Long enable;
	/**
	 * attribute category id
	 */
	private Long catelogId;
	/**
	 * displayed on the product, can also be adjusted in spu [0->no, 1->yes]
	 */
	private Integer showDesc;

}
