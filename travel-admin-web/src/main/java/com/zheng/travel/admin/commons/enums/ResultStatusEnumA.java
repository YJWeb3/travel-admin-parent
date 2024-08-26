package com.zheng.travel.admin.commons.enums;

/**
 * Description:
 * Author: 政哥 Administrator
 * Version: 1.0
 * Create Date Time: 2023/12/16 21:05.
 * Update Date Time:
 *
 * @see
 */
public enum ResultStatusEnumA implements IResultEnum{

    ID_NOT_EMPTY("701", "100", "id不允许为空"),
    SERVER_DB_ERROR("702", "100", "数据库服务器出现故障"),
    USER_PWD_STATUS("601", "100", "用户密码有误"),
    USER_USERNAME_STATUS("602", "100", "请输入用户名"),
    USER_PWD_STATUS_INPUT("603", "100", "请输入密码"),
    USER_CODE_STATUS_INPUT("604", "100", "请输入验证码"),
    USER_CODE_INPUT_ERROR("605", "100", "验证码输入有误"),
    USER_CODE_ERROR_CACHE("606", "100", "缓存验证码为空"),
    TOKEN_EMPTY("607", "100", "Token不能为空!"),
    TOKEN_EMPTY_EXPIRED("608", "100", "Token已过期!"),
    TOKEN_USER_EMPTY("609", "100", "用户不存在!"),
    TOKEN_UN_VALID("610", "100", "Token无效!"),
    EMPTY_IDS_VALID("700", "100", "没有要删除的数据!"),
    ORDER_ERROR_STATUS("611", "100", "订单有误"),
    NO_PERMISSION("708", "100", "没有访问权限"),
    NULL_DATA("709", "100", "找不到数据!");

    public static final Integer A1 = 100; // 用户登录
    public static final Integer A2 = 101; // 支付
    public static final Integer A3 = 102; // 产品课程

    ResultStatusEnumA(String status, String bcode, String message) {
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
