package com.zheng.travel.admin.generator.config;

import com.zheng.travel.admin.generator.builder.ITravelConfigBuilder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class TravelGlobalConfig {


    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean isoverride = false;


    /**
     * 作者
     */
    private String author = "作者";

    /**
     * gitlink
     */
    private String gitlink;

    /**
     * url地址
     */
    private String url;

    /**
     * 时间
     */
    private String datetime;

    /**
     * 标题
     */
    private String title;

    /**
     * 版本号
     */
    private String version = "1.0.0";

    /**
     * boot版本
     */
    private String bootversion = "2.5.8";
    /**
     * 端口
     */
    private String port = "9000";
    /**
     * redisip
     */
    private String redisip = "127.0.0.1";
    /**
     * redis端口
     */
    private String redisport = "6379";
    /**
     * redis密码
     */
    private String redispwd = "";
    /**
     * api标题
     */
    private String apititle = "学相伴travelAdmin文档接口";
    /**
     * api描述
     */
    private String apidesc = "学相伴travelAdmin文档接口描述";
    /**
     * api版本
     */
    private String apiversion = "1.0.0";
    /**
     * apiurl地址
     */
    private String apiurl = "1.0.0";
    /*
     * api开发成员
     * */
    private String apimemebers = "yykk";

    /**
     * 是否开启属性注释
     */
    private boolean isComment = true;


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder implements ITravelConfigBuilder<TravelGlobalConfig> {

        private final TravelGlobalConfig travelGlobalConfig;

        public Builder() {
            this.travelGlobalConfig = new TravelGlobalConfig();
        }

        /**
         * 覆盖已有文件
         */
        public Builder isoverride() {
            this.travelGlobalConfig.isoverride = true;
            return this;
        }


        /**
         * 作者
         */
        public Builder author(String author) {
            this.travelGlobalConfig.author = author;
            return this;
        }

        /**
         * 标题
         */
        public Builder title(String title) {
            this.travelGlobalConfig.title = title;
            return this;
        }

        /**
         * 版本号
         */
        public Builder version(String version) {
            this.travelGlobalConfig.version = version;
            return this;
        }


        /**
         * 版本号
         */
        public Builder bootversion(String bootversion) {
            this.travelGlobalConfig.bootversion = bootversion;
            return this;
        }

        /**
         * 服务端口
         */
        public Builder port(String port) {
            this.travelGlobalConfig.port = port;
            return this;
        }

        /**
         * redisip
         */
        public Builder redisip(String redisip) {
            this.travelGlobalConfig.redisip = redisip;
            return this;
        }

        /**
         * redis密码
         */
        public Builder redispwd(String redispwd) {
            this.travelGlobalConfig.redispwd = redispwd;
            return this;
        }


        /**
         * redis端口
         */
        public Builder redisport(String redisport) {
            this.travelGlobalConfig.redisport = redisport;
            return this;
        }


        /**
         * 作者
         */
        public Builder gitlink(String gitlink) {
            this.travelGlobalConfig.gitlink = gitlink;
            return this;
        }

        /**
         * 作者
         */
        public Builder url(String url) {
            this.travelGlobalConfig.url = url;
            return this;
        }


        /**
         * 标题
         */
        public Builder apititle(String apititle) {
            this.travelGlobalConfig.apititle = apititle;
            return this;
        }


        /**
         * 描述
         */
        public Builder apidesc(String apidesc) {
            this.travelGlobalConfig.apidesc = apidesc;
            return this;
        }



        /**
         * 版本
         */
        public Builder apiversion(String apiversion) {
            this.travelGlobalConfig.apiversion = apiversion;
            return this;
        }



        /**
         * 成员
         */
        public Builder apimemebers(String apimemebers) {
            this.travelGlobalConfig.apimemebers = apimemebers;
            return this;
        }


        /**
         * api路径
         */
        public Builder apiurl(String apiurl) {
            this.travelGlobalConfig.apiurl = apiurl;
            return this;
        }

        /**
         * 时间类型对应策略
         */
        public Builder datetime(String datetime) {
            this.travelGlobalConfig.datetime = datetime;
            return this;
        }

        /**
         * 指定注释日期格式化
         *
         * @param pattern 格式
         * @return this
         * @since 3.5.0
         */
        public Builder commentDate(String pattern) {
            this.travelGlobalConfig.datetime = new SimpleDateFormat(pattern).format(new Date());
            return this;
        }


        @Override
        public TravelGlobalConfig build() {
            return this.travelGlobalConfig;
        }
    }

}
