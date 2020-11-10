package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * spu image
 * 
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_spu_images")
public class SpuImagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * spu id
	 */
	private Long spuId;
	/**
	 * image name
	 */
	private String imgName;
	/**
	 * image link
	 */
	private String imgUrl;
	/**
	 * image sequence
	 */
	private Integer imgSort;
	/**
	 * is default image [0->no, 1->yes]
	 */
	private Integer defaultImg;

}
