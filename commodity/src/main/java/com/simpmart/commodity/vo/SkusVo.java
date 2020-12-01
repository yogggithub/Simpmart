/**
  * Copyright 2019 bejson.com 
  */
package com.simpmart.commodity.vo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkusVo {
    private List<LessAttrVo> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<ImagesVo> images;
    private List<String> descar;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPriceVo> memberPrice;
}