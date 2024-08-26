package com.zheng.travel.admin;

import com.zheng.travel.admin.generator.config.*;


public class GeneratorProjects {

    public static void main(String[] args) {

        String tablename = "travel_system_logs";
        String model = "SystemLogs";
        String title = "酒店订单";

        // 初始化数据源
        TravelDataSourceConfig dataSourceConfig = TravelDataSourceConfig.builder()
                .url("jdbc:mysql://127.0.0.1:3306/ksd-Travel-travel?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf-8&useSSL=false")
                .username("root")
                .password("mkxiaoer").build();
        // 获取数据表信息
        TravelDataTableSourceConfig travelDataTableSourceConfig = TravelDataTableSourceConfig.builder().tablename(tablename).buildTableInfo(dataSourceConfig);
        // 查询所有的表信息
        //TravelDataTableSourceConfig TravelDataTableSourceConfig1 = TravelDataTableSourceConfig.builder().buildTables(dataSourceConfig);
        // 全局设置
        TravelGlobalConfig travelGlobalConfig = TravelGlobalConfig.builder()
                .isoverride()
                .author("yykk")
                .gitlink("前台代码 git clone https://gitee.com/kekesam/kuangstudy-Travel-ui.git")
                .url("后台代码 git clone https://gitee.com/kekesam/kuangstudy-Travel-parent.git")
                .title(title)
                .commentDate("yyyy-MM-dd HH:mm:ss")
                .version("1.0.0")
                .port("9000")
                .redisport("6379")
                .redisip("120.77.34.190")
                .redispwd("mkxiaoer1986.")
                .bootversion("2.5.8")
                .apititle("学相伴在线接口文档")
                .apidesc("学相伴在线接口文档我是一个描述")
                .apimemebers("yykk,kuangshen")
                .apiversion("1.0.0")
                .apiurl("http://localhost:9000")
                .build();

        // 开始设置实体名称和包
        TravelPackageConfig travelPackageConfig = TravelPackageConfig.builder()
                .dataSourceConfig(dataSourceConfig)
                .dataTableSourceConfig(travelDataTableSourceConfig)
                .globalConfig(travelGlobalConfig)
                .outputDir()
                .model(model)
                .istree(false)
                .entity("pojo").template("pojo.tml").projectName("kuangstudy-Travel-pojo").rootPackage("com.ksd.Travel").compareEnity().create()
                .mapper("mapper").template("mapper.tml").projectName("kuangstudy-Travel-orm-mybatis-mp").rootPackage("com.ksd.orm.Travel").compareMapper().create()
                .mapperxml("mapper").template("mapperxml.tml").projectName("kuangstudy-Travel-orm-mybatis-mp").compareMapperXml().create()

                .vo("vo").template("vo.tml").projectName("kuangstudy-Travel-web-admin").rootPackage("com.ksd.Travel").compareVo().create()
                .service("service").template("service2.tml").projectName("kuangstudy-Travel-web-admin").rootPackage("com.ksd.Travel").compareService().create()
                .serviceImpl("service").template("serviceImpl2.tml").projectName("kuangstudy-Travel-web-admin").rootPackage("com.ksd.Travel").compareServiceImpl().create()
                .controller("controller").template("controller2.tml").projectName("kuangstudy-Travel-web-admin").rootPackage("com.ksd.Travel").compareController().create()
                .api().template("/api/template.html").projectName("kuangstudy-Travel-web-admin").compareApi().create().build();



        // 菜单信息
        TravelViewConfig.builder()
                .dataSourceConfig(dataSourceConfig)
                .dataTableSourceConfig(travelDataTableSourceConfig)
                .globalConfig(travelGlobalConfig)
                .travelPackageConfig(travelPackageConfig)
                .outputDir("C:/yykk/学相伴/projects/")
                .projectName("kuangstudy-pug-ui")
                .isoverride(true)
                .isCategory(true)
                .isUpload(true)
                .isSingle(false)
                .isEditor(true)
                .model(model)
                .apiPath("src.services").apiCompare().create()
                .viewPath("src.views.admin").listCompare().create()
                .viewPath("src.views.admin").addCompare().create()
                .apiDoc("src.api").apiDocCompare().create()
                .routerPath("src.router.config").routerCompare()
                .addPermission()
                .addMenu()
                .toRouter()
                .build();

    }
}
