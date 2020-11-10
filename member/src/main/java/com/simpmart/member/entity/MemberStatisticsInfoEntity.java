package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member statistics
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
@Data
@TableName("memb_member_statistics_info")
public class MemberStatisticsInfoEntity implements Serializable {
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
	 * total spent amount
	 */
	private BigDecimal consumeAmount;
	/**
	 * total discount amount
	 */
	private BigDecimal couponAmount;
	/**
	 * order quantity
	 */
	private Integer orderCount;
	/**
	 * coupon quantity
	 */
	private Integer couponCount;
	/**
	 * review quantity
	 */
	private Integer commentCount;
	/**
	 * return quantity
	 */
	private Integer returnOrderCount;
	/**
	 * login quantity
	 */
	private Integer loginCount;
	/**
	 * favorite quantity
	 */
	private Integer attendCount;
	/**
	 * fans quantity
	 */
	private Integer fansCount;
	/**
	 * production favorite quantity
	 */
	private Integer collectProductCount;
	/**
	 * topic favorite quantity
	 */
	private Integer collectSubjectCount;
	/**
	 * review favorite quantity
	 */
	private Integer collectCommentCount;
	/**
	 * refer friends quantity
	 */
	private Integer inviteFriendCount;

}
