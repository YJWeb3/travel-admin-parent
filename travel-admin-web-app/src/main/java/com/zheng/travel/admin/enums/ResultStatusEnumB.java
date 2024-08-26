package com.zheng.travel.admin.enums;


import com.zheng.travel.admin.commons.enums.IResultEnum;

/**
 * Description:
 * Author: yykk Administrator
 * Version: 1.0
 * Create Date Time: 2021/12/16 21:05.
 * Update Date Time:
 *
 * @see
 */
public enum ResultStatusEnumB implements IResultEnum {

    USER_PWR_STATUS("601", "100", "用户密码有误"),
    USER_PWR_STATUS_INPUT("603", "100", "请输入密码"),
    TOKEN_EMPTY("604", "100", "token is empty!!!"),
    TOKEN_USER_EMPTY("605", "100", "User is not exisit!!!"),
    TOKEN_UN_VALID("606", "100", "token unvalid!!!"),
    ORDER_ERROR_STATUS("602", "102", "订单有误");

    public static final Integer A1 = 100; // 用户登录
    public static final Integer A2 = 101; // 支付
    public static final Integer A3 = 102; // 产品课程

    ResultStatusEnumB(String status, String bcode, String message) {
        this.status = Integer.parseInt(IResultEnum.A.concat(bcode).concat(status));
        this.message = message;
    }

    private int status;
    private String message;

    @Override
    public int status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
