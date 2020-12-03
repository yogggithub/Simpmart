package com.simpmart.common.constant;

public class CommodityConst {
    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "Basic Attribute"),
        ATTR_TYPE_SALE(0, "Sale Attribute");
        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
