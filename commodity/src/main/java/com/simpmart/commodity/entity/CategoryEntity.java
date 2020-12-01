package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * category
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_category")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * category id
     */
    @TableId
    private Long catId;
    /**
     * category name
     */
    private String name;
    /**
     * parent category id
     */
    private Long parentCid;
    /**
     * category level
     */
    private Integer catLevel;
    /**
     * showing status [0->no, 1->yes]
     *
     * @TableLogic indicate this is a iconic column
     * for logical delete function
     * because when I design the database, this column stores
     * 1 means display current category, 0 means dont display
     * that is reversed to the MybatisPlus setting
     * so, I have to set value and delval
     */
    @TableLogic(value = "1", delval = "0")
    private Integer showStatus;
    /**
     * category sequence
     */
    private Integer sort;
    /**
     * category icon link
     */
    private String icon;
    /**
     * product unit
     */
    private String productUnit;
    /**
     * product quantity
     */
    private Integer productCount;

    /**
     * List of children categories
     *
     * @TableField(exist = false) means this attribute
     *                             doesn't exist in the database
     * @JsonInclude(JsonInclude.Include.NON_EMPTY)
     *                             means response should exclude this attribute
     *                             when list is empty
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryEntity> childrenCategory;
}
