package com.zheng.travel.admin.commons.ex;

import com.zheng.travel.admin.commons.enums.IResultEnum;

public class TravelBussinessException extends RuntimeException {

    private int status;
    private String msg;


    public TravelBussinessException(IResultEnum resultEnum) {
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
