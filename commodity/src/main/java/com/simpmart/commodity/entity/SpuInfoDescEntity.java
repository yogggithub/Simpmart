package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu description
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_spu_info_desc")
public class SpuInfoDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * product id
	 * this column is not auto incremental
	 * so need to specified type
	 */
	@TableId(type = IdType.INPUT)
	private Long spuId;
	/**
	 * product description
	 */
	private String description;

}
