package com.simpmart.commodity.vo;

import com.simpmart.commodity.entity.AttrEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo {
    /**
     * attribute group id
     */
    private Long attrGroupId;
    /**
     * group name
     */
    private String attrGroupName;
    /**
     * group sequence
     */
    private Integer sort;
    /**
     * group name
     */
    private String description;
    /**
     * group icon
     */
    private String icon;
    /**
     * attribute category id
     */
    private Long catalogId;

    private List<AttrEntity> attrs;
}
