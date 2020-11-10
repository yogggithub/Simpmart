package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member growth point history
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@Data
@TableName("memb_growth_change_history")
public class GrowthChangeHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member id
	 */
	private Long memberId;
	/**
	 * member create time
	 */
	private Date createTime;
	/**
	 * changd value (postive / negatvie)
	 */
	private Integer changeCount;
	/**
	 * member note
	 */
	private String note;
	/**
	 * point source [0 -> shopping, 1 -> manual addition]
	 */
	private Integer sourceType;

}
