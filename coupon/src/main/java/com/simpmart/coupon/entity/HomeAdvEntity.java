package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * home page advertisement
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_home_adv")
public class HomeAdvEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * ad name
	 */
	private String name;
	/**
	 * ad image link
	 */
	private String pic;
	/**
	 * ad start time
	 */
	private Date startTime;
	/**
	 * ad end time
	 */
	private Date endTime;
	/**
	 * ad status
	 */
	private Integer status;
	/**
	 * ad click count
	 */
	private Integer clickCount;
	/**
	 * ad hyperlink
	 */
	private String url;
	/**
	 * ad note
	 */
	private String note;
	/**
	 * ad sequence
	 */
	private Integer sort;
	/**
	 * advertiser
	 */
	private Long publisherId;
	/**
	 * ad authorization
	 */
	private Long authId;

}
