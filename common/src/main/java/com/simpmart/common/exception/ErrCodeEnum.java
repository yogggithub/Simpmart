package com.simpmart.common.exception;

/**
 * Rules for system exception codes
 * 1. Error code would have 4 digits
 *      the first two would indicate the module
 *      the last two would specify error type
 * 2. Module code: match the port that module using
 *      90: Common Error
 *      10: Commodity
 *      20: Coupon
 *      30: Member
 *      40: Order
 *      50: Warehouse
 *      60: Thirdparty
 *      88: Gateway
 * 3. Error code:
 *      01: Validation error
 *      99: Unknown exception
 */
public enum ErrCodeEnum {
    /**
     * 90: Common Error
     */
    VALID_EXCEPTION(9001, "Validation Failed"),
    UNKNOWN_EXCEPTION(9099, "Unknown Exception");

    private int code;
    private String errMsg;

    ErrCodeEnum(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
