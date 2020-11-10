package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member login history
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
@Data
@TableName("memb_member_login_log")
public class MemberLoginLogEntity implements Serializable {
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
	 * log create time
	 */
	private Date createTime;
	/**
	 * login ip address
	 */
	private String ip;
	/**
	 * login city
	 */
	private String city;
	/**
	 * login type [1 -> pc, 2 -> mobile]
	 */
	private Integer loginType;

}
