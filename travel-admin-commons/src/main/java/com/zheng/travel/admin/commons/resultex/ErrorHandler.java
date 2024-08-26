package com.zheng.travel.admin.commons.resultex;

import com.zheng.travel.admin.commons.enums.IResultEnum;
import lombok.Data;

@Data
public class ErrorHandler {

    // 状态码
    private Integer status;
    // 返回信息
    private String msg;
    // 返回数据，因为返回数据是不确定类型，所以只能考虑Object或者泛型
    private Object data;

    // 全部约束使用方法区执行和返回，不允许到到外部去new
    private ErrorHandler() {

    }

    /**
     * 方法封装 :
     * - R 大量的大量的入侵
     * - 解决调用方便的问题
     *
     * @return
     */
    // 错误为什么传递status。成功只有一种，但是错误有N状态
    public static ErrorHandler error(Integer status, String msg) {
        return restResult(null, status, msg);
    }

    public static ErrorHandler error(Integer status, String msg, Object obj) {
        return restResult(obj, status, msg);
    }

    public static ErrorHandler error(IResultEnum resultEnum) {
        return restResult(null, resultEnum.status(), resultEnum.message());
    }

    private static ErrorHandler restResult(Object data, Integer status, String msg) {
        ErrorHandler r = new ErrorHandler();
        r.setStatus(status);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
