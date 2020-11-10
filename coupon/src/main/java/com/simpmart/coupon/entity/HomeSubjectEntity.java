package com.simpmart.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * home page topic
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:27:22
 */
@Data
@TableName("prom_home_subject")
public class HomeSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * topic name
	 */
	private String name;
	/**
	 * topic title
	 */
	private String title;
	/**
	 * topic sub title
	 */
	private String subTitle;
	/**
	 * topic showing status
	 */
	private Integer status;
	/**
	 * topic content link
	 */
	private String url;
	/**
	 * topic sequence
	 */
	private Integer sort;
	/**
	 * topic image link
	 */
	private String img;

}
