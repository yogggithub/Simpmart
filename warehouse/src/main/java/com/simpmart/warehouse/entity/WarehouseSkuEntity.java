package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * production inventory
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Data
@TableName("ware_warehouse_sku")
public class WarehouseSkuEntity implements Serializable {
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
	 * warehouse id
	 */
	private Long wareId;
	/**
	 * quantity in stock
	 */
	private Integer stock;
	/**
	 * sku name
	 */
	private String skuName;
	/**
	 * locked inventory
	 */
	private Integer stockLocked;

}
