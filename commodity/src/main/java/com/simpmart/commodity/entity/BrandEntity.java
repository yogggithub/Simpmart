package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * brand
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * brand id
	 */
	@TableId
	private Long brandId;
	/**
	 * brand name
	 */
	private String name;
	/**
	 * brand logo link
	 */
	private String logo;
	/**
	 * brand description
	 */
	private String descript;
	/**
	 * showing status [0->no, 1->yes]
	 */
	private Integer showStatus;
	/**
	 * search for initial letter
	 */
	private String firstLetter;
	/**
	 * brand sequence
	 */
	private Integer sort;

}
