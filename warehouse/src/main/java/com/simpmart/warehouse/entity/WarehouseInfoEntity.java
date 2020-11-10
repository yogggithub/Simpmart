package com.simpmart.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * warehouse information
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 10:14:00
 */
@Data
@TableName("ware_warehouse_info")
public class WarehouseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * warehouse name
	 */
	private String name;
	/**
	 * warehouse address
	 */
	private String address;
	/**
	 * warehouse areacode
	 */
	private String areacode;

}
