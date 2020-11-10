package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * category
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * category id
	 */
	@TableId
	private Long catId;
	/**
	 * category name
	 */
	private String name;
	/**
	 * parent category id
	 */
	private Long parentCid;
	/**
	 * category level
	 */
	private Integer catLevel;
	/**
	 * showing status [0->no, 1->yes]
	 */
	private Integer showStatus;
	/**
	 * category sequence
	 */
	private Integer sort;
	/**
	 * category icon link
	 */
	private String icon;
	/**
	 * product unit
	 */
	private String productUnit;
	/**
	 * product quantity
	 */
	private Integer productCount;

}
