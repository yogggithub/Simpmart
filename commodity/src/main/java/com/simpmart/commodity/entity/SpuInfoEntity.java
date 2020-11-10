package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * standard product unit infomation
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_spu_info")
public class SpuInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * spu id
	 */
	@TableId
	private Long id;
	/**
	 * spu name
	 */
	private String spuName;
	/**
	 * spu description
	 */
	private String spuDescription;
	/**
	 * category id
	 */
	private Long catalogId;
	/**
	 * brand id
	 */
	private Long brandId;
	/**
	 * product weight
	 */
	private BigDecimal weight;
	/**
	 * Is the product on sale [0->no, 1->yes]
	 */
	private Integer publishStatus;
	/**
	 * spu create time
	 */
	private Date createTime;
	/**
	 * spu update time
	 */
	private Date updateTime;

}
