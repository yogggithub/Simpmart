/**
 * Copyright 2019 bejson.com
 */
package com.simpmart.commodity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BoundsVo {
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}