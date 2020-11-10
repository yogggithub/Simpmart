package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ware task detail
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Data
@TableName("ware_warehouse_order_task_detail")
public class WarehouseOrderTaskDetailEntity implements Serializable {
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
	 * sku name
	 */
	private String skuName;
	/**
	 * purchase quantity
	 */
	private Integer skuNum;
	/**
	 * task id
	 */
	private Long taskId;

}
