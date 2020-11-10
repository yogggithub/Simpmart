package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@Data
@TableName("memb_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * member level id
	 */
	private Long levelId;
	/**
	 * username
	 */
	private String username;
	/**
	 * password
	 */
	private String password;
	/**
	 * nickname
	 */
	private String nickname;
	/**
	 * phone number
	 */
	private String mobile;
	/**
	 * email
	 */
	private String email;
	/**
	 * profile image
	 */
	private String header;
	/**
	 * gender
	 */
	private Integer gender;
	/**
	 * day of birth
	 */
	private Date birth;
	/**
	 * city
	 */
	private String city;
	/**
	 * occupation
	 */
	private String job;
	/**
	 * personalized signature
	 */
	private String sign;
	/**
	 * member source
	 */
	private Integer sourceType;
	/**
	 * member rewards
	 */
	private Integer integration;
	/**
	 * member growth point
	 */
	private Integer growth;
	/**
	 * member status
	 */
	private Integer status;
	/**
	 * register time
	 */
	private Date createTime;

}
