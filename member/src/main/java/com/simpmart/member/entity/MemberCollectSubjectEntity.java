package com.simpmart.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member favorite topic
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-10 09:40:15
 */
@Data
@TableName("memb_member_collect_subject")
public class MemberCollectSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * topic id
	 */
	private Long subjectId;
	/**
	 * topic name
	 */
	private String subjectName;
	/**
	 * topic image link
	 */
	private String subjectImg;
	/**
	 * topic content link
	 */
	private String subjectUrl;

}
