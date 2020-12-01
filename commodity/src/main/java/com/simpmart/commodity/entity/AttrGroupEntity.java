package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * attribute groups
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		/**
		 * attribute group id
		 */
		@TableId
		private Long attrGroupId;
		/**
		 * group name
		 */
		private String attrGroupName;
		/**
		 * group sequence
		 */
		private Integer sort;
		/**
		 * group name
		 */
		private String description;
		/**
		 * group icon
		 */
		private String icon;
		/**
		 * attribute category id
		 */
		private Long catalogId;

		/**
		 * Store the tree info of this AttrGroup instance
		 */
		@TableField(exist = false)
		private Long[] catalogPath;

}
