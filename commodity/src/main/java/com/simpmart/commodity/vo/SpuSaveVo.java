/**
 * Copyright 2019 bejson.com
 */
package com.simpmart.commodity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpuSaveVo {
    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> description;
    private List<String> images;
    private BoundsVo bounds;
    private List<BaseAttrsVo> baseAttrs;
    private List<SkusVo> skus;
}