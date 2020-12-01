/**
  * Copyright 2019 bejson.com 
  */
package com.simpmart.commodity.vo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class MemberPriceVo {
    private Long id;
    private String name;
    private BigDecimal price;
}