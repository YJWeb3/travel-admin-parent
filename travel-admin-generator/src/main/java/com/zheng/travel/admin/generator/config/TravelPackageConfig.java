package com.zheng.travel.admin.generator.config;

import com.zheng.travel.admin.generator.freemarker.tool.*;
import com.zheng.travel.admin.commons.utils.StringUtils;
import com.zheng.travel.admin.generator.builder.ITravelConfigBuilder;
import com.zheng.travel.admin.generator.pojo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class TravelPackageConfig {

    private TravelDataSourceConfig pugDataSourceConfig;
    private TravelDataTableSourceConfig pugDataTableSourceConfig;
    private TravelGlobalConfig pugGlobalConfig;

    /**
     * 创建文件所在的位置
     * 建议是： System.getProperty("user.dir")
     */
    private String outputDir;

    /**
     * 生成指定的项目名称
     */
    private String projectName;

    /**
     * maven的java资源路径
     */
    private String mavenJavaPackage = "src.main.java";

    /**
     * maven的资源路径
     */
    private String mavenResourcePackage = "src.main.resources";

    /**
     * 根包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String rootPackage;
    /**
     * entity核心包
     */
    private String entityPackage;
    /**
     * vo核心包
     */
    private String voPackage;
    /**
     * service核心包
     */
    private String servicePackage;
    /**
     * serviceImpl核心包
     */
    private String serviceImplPackage;
    /**
     * mapper核心包
     */
    private String mapperPackage;
    /**
     * controller核心包
     */
    private String controllerPackage;

    /**
     * 模块实体
     */
    private String model;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 包名
     */
    private String packagename;

    /*
     *模块名
     * */
    private String classname;

    /**
     * entity
     */
    private String entity;

    /*
     *实体模块
     */
    private String service;
    /*
     *实体实现类模块
     */
    private String serviceimpl;

    /**
     * mapper
     */
    private String mapper;

    /**
     * mapperxml
     */
    private String mapperxml;

    /**
     * controller
     */
    private String controller;

    /**
     * 页面模块
     */
    private String page;

    /**
     * 页面模块
     */
    private String vo;

    /**
     * 模板的名称
     */
    private String template;

    /**
     * 文件的最终路径
     */
    private String filepath;

    /**
     * 子路径
     */
    private String childpackage;

    /**
     * 获取项目java的完整路径
     */
    private String projectJavaPath;

    /**
     * 获取项目resources的完整路径
     */
    private String projectResourcePath;

    /**
     * 是否生成tree
     */
    private Boolean istree = false;


    public static Builder builder() {
        return new Builder();
    }


    private static String replaceLine(String content) {
        return content.replaceAll("\\.", "/");
    }


    public static class Builder implements ITravelConfigBuilder<TravelPackageConfig> {

        private final TravelPackageConfig pugPackageConfig;

        public Builder() {
            this.pugPackageConfig = new TravelPackageConfig();
        }


        public Builder dataTableSourceConfig(TravelDataTableSourceConfig dataTableSourceConfig) {
            pugPackageConfig.pugDataTableSourceConfig = dataTableSourceConfig;
            return this;
        }

        public Builder dataSourceConfig(TravelDataSourceConfig dataSourceConfig) {
            pugPackageConfig.pugDataSourceConfig = dataSourceConfig;
            return this;
        }

        public Builder globalConfig(TravelGlobalConfig globalConfig) {
            pugPackageConfig.pugGlobalConfig = globalConfig;
            return this;
        }

        public Builder outputDir(String... outputDir) {
            pugPackageConfig.outputDir = StringUtils.isEmpty(outputDir) ? System.getProperty("user.dir") : outputDir[0];
            return this;
        }

        public Builder projectName(String projectName) {
            pugPackageConfig.projectName = projectName;
            return this;
        }

        public Builder mavenJavaPackage() {
            pugPackageConfig.mavenJavaPackage = "src/main/java";
            return this;
        }

        public Builder mavenResourcePackage() {
            pugPackageConfig.mavenResourcePackage = "src/main/resources";
            return this;
        }


        public Builder rootPackage(String rootPackage) {
            pugPackageConfig.rootPackage = rootPackage;
            return this;
        }

        public Builder model(String model) {
            pugPackageConfig.model = model;
            return this;
        }

        public Builder vo(String vo) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.vo = vo;
            pugPackageConfig.template = "vo.tml";
            return this;
        }

        public Builder filepath(String filepath) {
            pugPackageConfig.filepath = filepath;
            return this;
        }

        public Builder childpackage(String childpackage) {
            pugPackageConfig.childpackage = childpackage;
            return this;
        }

        public Builder entity(String entityPackage) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.entity = entityPackage;
            pugPackageConfig.template = "pojo.tml";
            return this;
        }

        public Builder mapper(String mapper) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.mapper = mapper;
            pugPackageConfig.template = "mapper.tml";
            return this;
        }

        public Builder mapperxml(String mapperxml) {
            pugPackageConfig.suffix = "xml";
            pugPackageConfig.mapperxml = mapperxml;
            pugPackageConfig.template = "mapperxml.tml";
            return this;
        }

        public Builder service(String mapper) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.service = mapper;
            pugPackageConfig.template = "service.tml";
            return this;
        }

        public Builder serviceImpl(String mapper) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.serviceimpl = mapper;
            pugPackageConfig.template = "serviceImpl.tml";
            return this;
        }

        public Builder controller(String mapper) {
            pugPackageConfig.suffix = "java";
            pugPackageConfig.controller = mapper;
            pugPackageConfig.template = "controller.tml";
            return this;
        }

        public Builder page(String mapper) {
            pugPackageConfig.suffix = ".vue";
            pugPackageConfig.page = mapper;
            return this;
        }

        public Builder template(String template) {
            pugPackageConfig.template = template;
            return this;
        }

        public Builder compareVo(String... name) {
            pugPackageConfig.voPackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.vo;
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model + "Vo" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.vo);
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.vo;
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "vo.tml" : pugPackageConfig.template;
            return this;

        }


        public Builder compareService(String... name) {
            pugPackageConfig.servicePackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.service;
            pugPackageConfig.classname = "I" + (StringUtils.isEmpty(name) ? pugPackageConfig.model + "Service" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.service + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage));
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.service + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage);
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "service.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder compareServiceImpl(String... name) {
            pugPackageConfig.serviceImplPackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.serviceimpl;
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model + "ServiceImpl" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.serviceimpl + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage));
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.serviceimpl + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage);
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "serviceImpl.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder compareController(String... name) {
            pugPackageConfig.controllerPackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.controller;
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model + "Controller" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.controller + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage));
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.controller + (StringUtils.isEmpty(pugPackageConfig.childpackage) ? "." + pugPackageConfig.model.toLowerCase() : "." + pugPackageConfig.childpackage);
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "controller.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder compareMapper(String... name) {
            pugPackageConfig.mapperPackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.mapper;
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model + "Mapper" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.mapper);
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.mapper;
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "mapper.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder compareMapperXml(String... name) {
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model + "Mapper" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.getMavenResourcePackage() + "." + pugPackageConfig.mapperxml);
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "mapperxml.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder compareEnity(String... name) {
            pugPackageConfig.entityPackage = pugPackageConfig.rootPackage + "." + pugPackageConfig.entity;
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? pugPackageConfig.model : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                            "." + pugPackageConfig.rootPackage + "." + pugPackageConfig.entity);
            pugPackageConfig.packagename = pugPackageConfig.rootPackage + "." + pugPackageConfig.entity;
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "pojo.tml" : pugPackageConfig.template;
            return this;
        }

        public Builder pom() {
            pugPackageConfig.suffix = "xml";
            pugPackageConfig.template = "pom.tml";
            return this;
        }


        public Builder comparePom() {
            pugPackageConfig.classname = "pom." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = pugPackageConfig.projectName;
            pugPackageConfig.packagename = pugPackageConfig.rootPackage;
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "pom.tml" : pugPackageConfig.template;
            return this;
        }


        public Builder api() {
            pugPackageConfig.suffix = "html";
            return this;
        }


        public Builder compareApi(String... name) {
            pugPackageConfig.classname = (StringUtils.isEmpty(name) ? "api" : name[0]) + "." + pugPackageConfig.suffix;
            pugPackageConfig.filepath = replaceLine(
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenResourcePackage +
                            ".templates." + pugPackageConfig.model.toLowerCase());
            pugPackageConfig.template = StringUtils.isEmpty(pugPackageConfig.template) ? "/api/template.html" : pugPackageConfig.template;
            return this;
        }


        public Builder suffix(String suffix) {
            pugPackageConfig.suffix = suffix;
            return this;
        }

        public Builder istree(boolean istree) {
            pugPackageConfig.istree = istree;
            return this;
        }

        public Builder create() {
            try {
                Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
                // 指定模板文件从何处加载的数据源，这里设置成一个文件目录。
                File file = new ClassPathResource("/templates").getFile();
                cfg.setDirectoryForTemplateLoading(file);
                // 指定模板如何检索数据模型，这是一个高级的主题了… // 但先可以这么来用：
                cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));

                // 创建根哈希表
                Map root = new HashMap();
                // 在根中放入字符串"user"
                root.put("tablename", pugPackageConfig.pugDataTableSourceConfig.getTablename());

                root.put("author", pugPackageConfig.pugGlobalConfig.getAuthor());
                root.put("datetime", pugPackageConfig.pugGlobalConfig.getDatetime());
                root.put("title", pugPackageConfig.pugGlobalConfig.getTitle());
                root.put("burl", pugPackageConfig.pugGlobalConfig.getUrl());
                root.put("version", pugPackageConfig.pugGlobalConfig.getVersion());
                root.put("bootversion", pugPackageConfig.pugGlobalConfig.getBootversion());
                root.put("gitlink", pugPackageConfig.pugGlobalConfig.getGitlink());
                root.put("apititle", pugPackageConfig.pugGlobalConfig.getApititle());
                root.put("apidesc", pugPackageConfig.pugGlobalConfig.getApidesc());
                root.put("apiurl", pugPackageConfig.pugGlobalConfig.getApiurl());
                root.put("apiversion", pugPackageConfig.pugGlobalConfig.getApiversion());
                root.put("apimemebers", pugPackageConfig.pugGlobalConfig.getApimemebers());


                root.put("beanModel", this.pugPackageConfig.model);
                root.put("model", this.pugPackageConfig.model.toLowerCase());
                root.put("entity", this.pugPackageConfig.entity);
                root.put("vo", this.pugPackageConfig.vo);
                root.put("mapper", this.pugPackageConfig.mapper);
                root.put("service", this.pugPackageConfig.service);
                root.put("serviceimpl", this.pugPackageConfig.serviceimpl);
                root.put("controller", this.pugPackageConfig.controller);
                root.put("page", this.pugPackageConfig.page);


                root.put("rootPath", "${rootPath}");
                root.put("packagename", this.pugPackageConfig.packagename);
                root.put("projectName", this.pugPackageConfig.projectName);
                root.put("rootPackage", this.pugPackageConfig.rootPackage);
                root.put("entityPackage", this.pugPackageConfig.entityPackage);
                root.put("voPackage", this.pugPackageConfig.voPackage);
                root.put("servicePackage", this.pugPackageConfig.servicePackage);
                root.put("serviceImplPackage", this.pugPackageConfig.serviceImplPackage);
                root.put("mapperPackage", this.pugPackageConfig.mapperPackage);
                root.put("controllerPackage", this.pugPackageConfig.controllerPackage);


                root.put("url", pugPackageConfig.pugDataSourceConfig.getUrl());
                root.put("dbusername", pugPackageConfig.pugDataSourceConfig.getUsername());
                root.put("dbpwd", pugPackageConfig.pugDataSourceConfig.getPassword());
                root.put("istree", this.pugPackageConfig.istree);


                List<TableInfo> tableInfos = pugPackageConfig.pugDataTableSourceConfig.getTableInfos();
                long count = tableInfos.stream().filter(item -> item.getName().equals("pid") || item.getName().equals("parentId")).count();
                root.put("istree", this.pugPackageConfig.istree);
                root.put("istreecount", count > 0);

                root.put("fields", pugPackageConfig.pugDataTableSourceConfig.getTableInfos());
                root.put("kuohao", new AddKuohu());
                root.put("kuohao2", new AddKuohu2());
                root.put("kuohao3", new AddKuohu3());
                root.put("listtag", new ListTag());
                root.put("Include", new IncludeTagMethod());


                Template temp = cfg.getTemplate(this.pugPackageConfig.template);

                // 指定最终渲染的页面存储的位置

                File rootPath = new File(this.pugPackageConfig.outputDir, this.pugPackageConfig.filepath);
                if (!rootPath.exists()) {
                    rootPath.mkdirs();
                }

                File targetFile = new File(rootPath, this.pugPackageConfig.classname);
                Writer out = new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8);
                // freemaker的模板渲染替换
                temp.process(root, out);
                out.flush();

                return this;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public TravelPackageConfig build() {
            pugPackageConfig.projectJavaPath = replaceLine(pugPackageConfig.getOutputDir() + "." +
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenJavaPackage +
                    "." + pugPackageConfig.rootPackage);
            pugPackageConfig.projectResourcePath = replaceLine(pugPackageConfig.getOutputDir() + "." +
                    pugPackageConfig.projectName + "." + pugPackageConfig.mavenResourcePackage);
            return pugPackageConfig;
        }
    }
}
