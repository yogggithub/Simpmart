package com.simpmart.warehouse.vo;

import lombok.Data;

@Data
public class ItemVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
