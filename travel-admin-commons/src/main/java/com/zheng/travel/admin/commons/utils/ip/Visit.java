package com.zheng.travel.admin.commons.utils.ip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 使用了 Lombok 来节省代码*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visit {
    /** 主键ID */
    private Integer id;
    /** IP地址 */
    private String ip;
    /** 请求头 */
    private String userAgent;
    /** 城市 */
    private String city;
    /** 访问地址 */
    private String url;
    /** 浏览器类型 */
    private String browserType;
    /** 操作系统类型 */
    private String platformType;
    /** 访问时间 */
    private Date time;

    public Visit(String browserType, String platformType) {
        this.browserType = browserType;
        this.platformType = platformType;
    }
}

