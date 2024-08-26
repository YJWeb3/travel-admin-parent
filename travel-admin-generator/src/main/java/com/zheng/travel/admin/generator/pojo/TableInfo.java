package com.zheng.travel.admin.generator.pojo;

import lombok.Data;

@Data
public class TableInfo {
    //数据库原有的名字
    private String column;
    //小写名字，
    private String name;
    //大写的名字，服务于set get
    private String hname;
    // 字段长度
    private String clen ;
    //字段对应的java类型
    private String type;
    // 注释
    private String comment;
    // 对应的java类型
    private String dtype;
    // 没有包的类型名字
    private String ctype;
    // 是不是注解
    private boolean isPrimarkey;
    // 是否是时间
    private boolean isDate;
    // 是否是null
    private boolean isNull;
    // 数据库类型
    private String dbType;
    // 数据库字段名
    private String dbField;
    // 数据库的默认值
    private String defaultVal;
}
