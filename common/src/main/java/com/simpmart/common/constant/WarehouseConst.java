package com.simpmart.common.constant;

public class WarehouseConst {
    public enum PurchaseStatusEnum {
        CREATED(0, "created"),
        ASSIGNED(1, "assigned"),
        RECEIVED(2, "received"),
        FINISHED(3, "finished"),
        Failure(4, "failure");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
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

    public enum PurchaseDetailStatusEnum {
        CREATED(0, "created"),
        ASSIGNED(1, "assigned"),
        PURCHASING(2, "purchasing"),
        FINISHED(3, "finished"),
        Failure(4, "failure");
        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg) {
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
