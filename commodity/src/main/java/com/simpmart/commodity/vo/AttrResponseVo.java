package com.simpmart.commodity.vo;

import lombok.Data;

@Data
public class AttrResponseVo extends AttrVo {
    private String catalogName, groupName;
    private Long[] catalogPath;
}
