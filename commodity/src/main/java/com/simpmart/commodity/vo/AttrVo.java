package com.simpmart.commodity.vo;

import lombok.Data;

@Data
public class AttrVo {
    private static final long serialVersionUID = 1L;

    /**
     * attribute id
     */
    private Long attrId;
    /**
     * attribute name
     */
    private String attrName;
    /**
     * need to search [0->no, 1->yes]
     */
    private Integer searchType;
    /**
     * attribute icon
     */
    private String icon;
    /**
     * List of available values, seperated by comma
     */
    private String valueSelect;
    /**
     * attribute type [0->sales, 1->basic, 2->both]
     */
    private Integer attrType;
    /**
     * attribute status [0->disable, 1->enable]
     */
    private Long enable;
    /**
     * attribute category id
     */
    private Long catalogId;
    /**
     * displayed on the product, can also be adjusted in spu [0->no, 1->yes]
     */
    private Integer showDesc;
    /**
     * value type allowed [0->single value, 1->multiple value]
     */
    private Integer valueType;

    private Long attrGroupId;

}
