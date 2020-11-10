package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member level
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
@Data
@TableName("memb_member_level")
public class MemberLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * level name
	 */
	private String name;
	/**
	 * required growth point
	 */
	private Integer growthPoint;
	/**
	 * default level [0 -> no, 1 -> yes]
	 */
	private Integer defaultStatus;
	/**
	 * standard for free shipping
	 */
	private BigDecimal freeFreightPoint;
	/**
	 * growth point gained per review
	 */
	private Integer commentGrowthPoint;
	/**
	 * free shipping or not [0 -> no, 1 -> yes]
	 */
	private Integer priviledgeFreeFreight;
	/**
	 * member specific price or not [0 -> no, 1 -> yes]
	 */
	private Integer priviledgeMemberPrice;
	/**
	 * birth day specific discount or not [0 -> no, 1 -> yes]
	 */
	private Integer priviledgeBirthday;
	/**
	 * not
	 */
	private String note;

}
