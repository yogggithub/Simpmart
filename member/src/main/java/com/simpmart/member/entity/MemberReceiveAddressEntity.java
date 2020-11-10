package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member shipment address
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:16
 */
@Data
@TableName("memb_member_receive_address")
public class MemberReceiveAddressEntity implements Serializable {
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
	 * consignee name
	 */
	private String name;
	/**
	 * consignee phone number
	 */
	private String phone;
	/**
	 * postcode
	 */
	private String postCode;
	/**
	 * state/province/region/territory
	 */
	private String province;
	/**
	 * city
	 */
	private String city;
	/**
	 * address line 1 (street address/P.O. box)
	 */
	private String region;
	/**
	 * address line 2 (appartment/floor/suite/unit)
	 */
	private String detailAddress;
	/**
	 * abbreviation of state/province/region/territory
	 */
	private String areacode;
	/**
	 * default address [0 -> no, 1 -> yes]
	 */
	private Integer defaultStatus;

}
