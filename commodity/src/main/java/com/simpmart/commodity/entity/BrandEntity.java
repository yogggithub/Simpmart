package com.simpmart.commodity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.simpmart.common.validation.annotation.ListValue;
import com.simpmart.common.validation.group.AddGroup;
import com.simpmart.common.validation.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * brand
 *
 * @author EricWu
 * @email ericwoodenman@gmail.com
 * @date 2020-11-09 12:19:43
 */
@Data
@TableName("comm_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * brand id
     */
    @TableId
    @Null(groups = {AddGroup.class})    // must be null when create new
    @NotNull(groups = {UpdateGroup.class})  // must not be null when update
    private Long brandId;
    /**
     * brand name
     */
    @NotBlank(groups = {AddGroup.class, UpdateGroup.class})
    // not be null and contain at least one non blank character
    private String name;
    /**
     * brand logo link
     */
    @NotBlank(groups = {AddGroup.class})
    @URL(groups = {AddGroup.class, UpdateGroup.class})
    private String logo;
    /**
     * brand description
     */
    private String description;
    /**
     * showing status [0->no, 1->yes]
     */
    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    @ListValue(values = {0, 1}, groups = {AddGroup.class, UpdateGroup.class})
    private Integer showStatus;
    /**
     * search for initial letter
     */
    @NotBlank(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * brand sequence
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;

}
