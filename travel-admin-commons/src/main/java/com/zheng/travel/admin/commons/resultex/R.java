package com.zheng.travel.admin.commons.resultex;

import com.zheng.travel.admin.commons.enums.IResultEnum;
import lombok.Data;


@Data
public class R implements java.io.Serializable{

    // 状态码
    private Integer status;
    // 返回信息
    private String msg;
    // 返回数据，因为返回数据是不确定类型，所以只能考虑Object或者泛型
    private Object data;

    // 全部约束使用方法区执行和返回，不允许到到外部去new
    private R() {

    }

    /**
     * 方法封装 :
     * - R 大量的大量的入侵
     * - 解决调用方便的问题
     *
     * @param obj
     * @return
     */
    public static R success(Object obj) {
        // 200写死，原因很简单：在开发成功只允许只有一种声音，不允许多种
        return restResult(obj, IResultEnum.SUCCESS, IResultEnum.SUCCESS_TEXT);
    }

    public static R success(Object obj, String msg) {
        return restResult(obj,  IResultEnum.SUCCESS, IResultEnum.SUCCESS_TEXT);
    }

    // 错误为什么传递status。成功只有一种，但是错误有N状态
    public static R error(Integer status, String msg) {
        return restResult(null, status, msg);
    }

    public static R error(Integer status, String msg, Object obj) {
        return restResult(obj, status, msg);
    }

    public static R error(IResultEnum result) {
        return restResult(null, result.status(), result.message());
    }

    private static R restResult(Object data, Integer status, String msg) {
        R r = new R();
        r.setStatus(status);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
