package com.zheng.travel.admin.commons.ex;//package com.kuangstudy.config;


import com.zheng.travel.admin.commons.enums.IResultEnum;

/**
 * Description: 自定义异常
 */
public class TravelValidationException extends RuntimeException {

    private int status;
    private String msg;

    public TravelValidationException(int status, String message) {
        super(message);
        this.status = status;
        this.msg = message;
    }

    public TravelValidationException(IResultEnum resultEnum) {
        super(resultEnum.message());
        this.status = resultEnum.status();
        this.msg = resultEnum.message();
    }


    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
