package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * product review
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_spu_comment")
public class SpuCommentEntity implements Serializable {
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
	 * spu id
	 */
	private Long spuId;
	/**
	 * product name
	 */
	private String spuName;
	/**
	 * member nick name
	 */
	private String memberNickName;
	/**
	 * review rating
	 */
	private Integer star;
	/**
	 * member ip address
	 */
	private String memberIp;
	/**
	 * review create time
	 */
	private Date createTime;
	/**
	 * review showing status [0->no, 1->yes]
	 */
	private Integer showStatus;
	/**
	 * sku attributes combination when purchased
	 */
	private String spuAttributes;
	/**
	 * likes of the review
	 */
	private Integer likesCount;
	/**
	 * replies of the review
	 */
	private Integer replyCount;
	/**
	 * comment with image or video [JSON, [{type:file type, url:file address}]]
	 */
	private String resources;
	/**
	 * review content
	 */
	private String content;
	/**
	 * profile picture
	 */
	private String memberIcon;
	/**
	 * comment type [0->review to the product, 1->reply to review]
	 */
	private Integer commentType;

}
