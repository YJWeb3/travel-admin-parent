package com.zheng.travel.admin.generator.config;

import com.zheng.travel.admin.generator.freemarker.tool.*;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.generator.builder.ITravelConfigBuilder;
import com.zheng.travel.admin.generator.pojo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
public class TravelViewConfig {

    private TravelDataSourceConfig travelDataSourceConfig;
    private TravelDataTableSourceConfig travelDataTableSourceConfig;
    private TravelGlobalConfig travelGlobalConfig;
    private TravelPackageConfig travelPackageConfig;

    /**
     * 创建文件所在的位置
     * 建议是： System.getProperty("user.dir")
     */
    private String outputDir;

    /**
     * 生成指定的项目名称
     */
    private String projectName;

    /*
     * 模块名称
     * */
    private String model;

    /**
     * 视图文件
     */
    private String viewPath = "src.views";

    /**
     * api的帮助文档
     */
    private String apidocPath = "src.api";

    /**
     * router的帮助文档
     */
    private String routerPath = "src.router.config";

    /*
     * api目录
     */
    private String apiPath = "src.service";

    /**
     * 是否需要富文本
     */
    private boolean isoverride = false;

    /**
     * 是否需要富文本
     */
    private boolean isEditor = false;

    /**
     * 是否需要文件上传
     */
    private boolean isUpload = false;

    /**
     * 是否单页 控制添加页面的步骤
     */
    private boolean isSingle = true;

    /**
     * 是否需要分类
     */
    private boolean isCategory = false;
    /**
     * 对应模板
     */
    private String template;
    /**
     * 文件目录
     */
    private String filepath;
    /**
     * 最终文件名
     */
    private String classname;


    public static Builder builder() {
        return new Builder();
    }


    private static String replaceLine(String content) {
        return content.replaceAll("\\.", "/");
    }


    public static class Builder implements ITravelConfigBuilder<TravelViewConfig> {

        private final TravelViewConfig travelViewConfig;

        public Builder() {
            this.travelViewConfig = new TravelViewConfig();
        }

        /**
         * 覆盖已有文件
         */
        public Builder isoverride(boolean override) {
            this.travelViewConfig.isoverride = override;
            return this;
        }

        public Builder dataTableSourceConfig(TravelDataTableSourceConfig dataTableSourceConfig) {
            travelViewConfig.travelDataTableSourceConfig = dataTableSourceConfig;
            return this;
        }

        public Builder dataSourceConfig(TravelDataSourceConfig dataSourceConfig) {
            travelViewConfig.travelDataSourceConfig = dataSourceConfig;
            return this;
        }

        public Builder globalConfig(TravelGlobalConfig globalConfig) {
            travelViewConfig.travelGlobalConfig = globalConfig;
            return this;
        }

        public Builder travelPackageConfig(TravelPackageConfig travelPackageConfig) {
            travelViewConfig.travelPackageConfig = travelPackageConfig;
            return this;
        }

        /**
         * 是否上传
         */
        public Builder isUpload(boolean isUpload) {
            this.travelViewConfig.isUpload = isUpload;
            return this;
        }

        /**
         * 是否单页 控制添加页面的步骤
         */
        public Builder isSingle(boolean isSingle) {
            this.travelViewConfig.isSingle = isSingle;
            return this;
        }

        /**
         * 是否富文本
         */
        public Builder isEditor(boolean isEditor) {
            this.travelViewConfig.isEditor = isEditor;
            return this;
        }

        /**
         * 是否分类
         */
        public Builder isCategory(boolean isCategory) {
            this.travelViewConfig.isCategory = isCategory;
            return this;
        }

        /**
         * 对应模块
         */
        public Builder model(String model) {
            this.travelViewConfig.model = model.toLowerCase();
            return this;
        }

        /**
         * 指定输出目录
         */
        public Builder outputDir(String outputDir) {
            this.travelViewConfig.outputDir = outputDir;
            return this;
        }

        /**
         * 指定工程名
         */
        public Builder projectName(String projectName) {
            this.travelViewConfig.projectName = projectName;
            return this;
        }

        /**
         * 视图目录
         */
        public Builder viewPath(String viewPath) {
            this.travelViewConfig.viewPath = viewPath;
            return this;
        }


        /**
         * api目录
         */
        public Builder apiPath(String apiPath) {
            this.travelViewConfig.apiPath = apiPath;
            return this;
        }

        /**
         * api目录
         */
        public Builder routerPath(String routerPath) {
            this.travelViewConfig.routerPath = routerPath;
            return this;
        }

        public Builder routerCompare(String... name) {
            this.travelViewConfig.classname = Vsserts.isNullArray(name) ? "menulist.js" : name[0];
            this.travelViewConfig.routerPath = replaceLine(
                    this.travelViewConfig.projectName + "." + this.travelViewConfig.routerPath);
            return this;
        }


        public Builder apiCompare(String... name) {
            this.travelViewConfig.classname = Vsserts.isNullArray(name) ? "index.js" : name[0];
            this.travelViewConfig.filepath = replaceLine(
                    this.travelViewConfig.projectName + "." + this.travelViewConfig.apiPath + "." + this.travelViewConfig.model);
            this.travelViewConfig.template = "/vue/service.tml";
            return this;
        }

        public Builder listCompare(String... name) {
            this.travelViewConfig.classname = Vsserts.isNullArray(name) ? "index.vue" : name[0];
            this.travelViewConfig.filepath = replaceLine(
                    this.travelViewConfig.projectName + "." + this.travelViewConfig.viewPath + "." + this.travelViewConfig.model);
            this.travelViewConfig.template = "/vue/index.tml";
            return this;
        }

        public Builder addCompare(String... name) {
            this.travelViewConfig.classname = Vsserts.isNullArray(name) ? "add.vue" : name[0];
            this.travelViewConfig.filepath = replaceLine(
                    this.travelViewConfig.projectName + "." + this.travelViewConfig.viewPath + "." + this.travelViewConfig.model);
            this.travelViewConfig.template = "/vue/add.tml";
            return this;
        }

        public Builder apiDoc(String apiDocPath) {
            this.travelViewConfig.apidocPath = apiDocPath;
            return this;
        }

        public Builder apiDocCompare(String... name) {
            this.travelViewConfig.classname = Vsserts.isNullArray(name) ? "api.html" : name[0];
            this.travelViewConfig.filepath = replaceLine(
                    this.travelViewConfig.projectName + "." + this.travelViewConfig.apidocPath + "." + this.travelViewConfig.model);
            this.travelViewConfig.template = "/api/template.html";
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
                root.put("tablename", travelViewConfig.travelDataTableSourceConfig.getTablename());

                root.put("author", travelViewConfig.travelGlobalConfig.getAuthor());
                root.put("datetime", travelViewConfig.travelGlobalConfig.getDatetime());
                root.put("title", travelViewConfig.travelGlobalConfig.getTitle());
                root.put("burl", travelViewConfig.travelGlobalConfig.getUrl());
                root.put("version", travelViewConfig.travelGlobalConfig.getVersion());
                root.put("bootversion", travelViewConfig.travelGlobalConfig.getBootversion());
                root.put("gitlink", travelViewConfig.travelGlobalConfig.getGitlink());
                root.put("apititle", travelViewConfig.travelGlobalConfig.getApititle());
                root.put("apidesc", travelViewConfig.travelGlobalConfig.getApidesc());
                root.put("apiurl", travelViewConfig.travelGlobalConfig.getApiurl());
                root.put("apiversion", travelViewConfig.travelGlobalConfig.getApiversion());
                root.put("apimemebers", travelViewConfig.travelGlobalConfig.getApimemebers());

                root.put("beanModel", travelViewConfig.getTravelPackageConfig().getModel());
                root.put("model", travelViewConfig.getTravelPackageConfig().getModel().toLowerCase());


                root.put("rootPath", "${rootPath}");


                root.put("url", travelViewConfig.travelDataSourceConfig.getUrl());
                root.put("dbusername", travelViewConfig.travelDataSourceConfig.getUsername());
                root.put("dbpwd", travelViewConfig.travelDataSourceConfig.getPassword());


                List<TableInfo> tableInfos = travelViewConfig.travelDataTableSourceConfig.getTableInfos();
                long count = tableInfos.stream().filter(item -> item.getName().equals("pid") || item.getName().equals("parentId")).count();
                long count2 = tableInfos.stream().filter(item -> item.getName().equals("categoryid") || item.getName().equals("categoryId") || item.getName().equals("cid")).count();
                root.put("istree", count > 0);
                root.put("isEditor", travelViewConfig.isEditor());
                root.put("isCategory", count2 > 0);
                root.put("isSingle", travelViewConfig.isSingle());


                root.put("tableInfos", tableInfos);
                root.put("fields", tableInfos);
                root.put("kuohao", new AddKuohu());
                root.put("kuohao2", new AddKuohu2());
                root.put("kuohao3", new AddKuohu3());
                root.put("listtag", new ListTag());
                root.put("Include", new IncludeTagMethod());


                Template temp = cfg.getTemplate(this.travelViewConfig.template);

                // 指定最终渲染的页面存储的位置

                File rootPath = new File(this.travelViewConfig.outputDir, this.travelViewConfig.filepath);
                if (!rootPath.exists()) {
                    rootPath.mkdirs();
                }

                File targetFile = new File(rootPath, this.travelViewConfig.classname);
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


        public Builder addMenu() {
            try {
                String title = travelViewConfig.getTravelGlobalConfig().getTitle();
                String model = travelViewConfig.getModel();
                // 1: 转换成map
                Connection connection = travelViewConfig.getTravelDataSourceConfig().getConn();
                PreparedStatement statement1 = connection.prepareStatement("select count(1) from travel_nav_menu where path ='/" + model + "'");
                PreparedStatement statement2 = connection.prepareStatement("select count(1) from travel_nav_menu");
                PreparedStatement statement3 = connection.prepareStatement("select max(indexon) from travel_nav_menu");
                ResultSet rs = statement1.executeQuery();
                ResultSet rs2 = statement2.executeQuery();
                ResultSet rs3 = statement3.executeQuery();
                rs.next();
                rs2.next();
                rs3.next();
                int count = rs.getInt(1);
                int count2 = rs2.getInt(1) + 2;
                int sorted = rs3.getInt(1);
                if (count == 0) {
                    StringBuilder builder = new StringBuilder();
                    int index = count2 - 1;
                    int count3 = count2;
                    builder.append("(" + count2 + ",'" + title + "管理'," + (sorted + 2) + ",'/" + model + "','icon-dashboard',1,now(),now(),0,'@/views/admin/" + model + "/index.vue','" + model + "','layout'," + (sorted + 1) + ",1,0),");
                    builder.append("(" + (++count3) + ",'" + title + "列表',1,'/" + model + "/list','',1,now(),now()," + count2 + ",'@/views/admin/" + model + "/index.vue','" + model + "list','layout'," + (sorted + 1) + ",1,0),");
                    builder.append("(" + (++count3) + ",'" + title + "添加',2,'/" + model + "/add','',1,now(),now()," + count2 + ",'@/views/admin/" + model + "/add.vue','" + model + "add','layout'," + (sorted + 1) + ",1,0),");
                    builder.append("(" + (++count3) + ",'" + title + "编辑',3,'/" + model + "/edit/:id','',1,now(),now()," + count2 + ",'@/views/admin/" + model + "/add.vue','" + model + "edit','layout'," + (sorted + 1) + ",0,0)");
                    String sql = "INSERT INTO `travel_nav_menu` (id,name,sorted,path,icon,status,create_time,update_time,pid,componentname,pathname,layout,indexon,showflag,isdelete)  VALUES " + builder.toString();
                    PreparedStatement statement = connection.prepareStatement(sql);
                    int update = statement.executeUpdate();
                    List<String> lines = new ArrayList<>();
                    if (update > 0) {
                        System.out.println(title + "菜单添加成功!!!");
                    }
                    statement.close();
                } else {
                    System.out.println(title + "已经添加过了....");
                }
                rs2.close();
                rs.close();
                statement2.close();
                statement1.close();
                connection.close();
                return this;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Builder addPermission() {
            try {
                String title = travelViewConfig.getTravelGlobalConfig().getTitle();
                String model = travelViewConfig.getModel();
                // 1: 转换成map
                Connection connection = travelViewConfig.getTravelDataSourceConfig().getConn();
                PreparedStatement statement1 = connection.prepareStatement("select count(1) from travel_permission where url ='/" + model + "'");
                PreparedStatement statement2 = connection.prepareStatement("select count(1) from travel_permission");
                PreparedStatement statement3 = connection.prepareStatement("select count(1) from travel_permission where pid = 0");
                ResultSet rs = statement1.executeQuery();
                ResultSet rs2 = statement2.executeQuery();
                ResultSet rs3 = statement3.executeQuery();
                rs.next();
                rs2.next();
                rs3.next();
                int count = rs.getInt(1);
                if (count == 0) {
                    int count2 = rs2.getInt(1) + 1;
                    int sorted = rs3.getInt(1);
                    StringBuilder builder = new StringBuilder();
                    int index = count2 - 1;
                    int count3 = count2;
                    builder.append("(" + count2 + ",'" + title + "管理'," + (sorted + 1) + ",'/" + model + "',1,now(),now(),0,'" + model + "',0,'"+count2+"'),");
                    builder.append("(" + (++count3) + ",'" + title + "列表',1,'/" + model + "/list',1,now(),now()," + count2 + ",'" + model + "list',0,'"+count2+"01'),");
                    builder.append("(" + (++count3) + ",'" + title + "添加',2,'/" + model + "/add',1,now(),now()," + count2 + ",'" + model + "add',0,'"+count2+"02'),");
                    builder.append("(" + (++count3) + ",'" + title + "明细',3,'/" + model + "/get',1,now(),now()," + count2 + ",'" + model + "get',0,'"+count2+"03'),");
                    builder.append("(" + (++count3) + ",'" + title + "删除',4,'/" + model + "/delete',1,now(),now()," + count2 + ",'" + model + "delete',0,'"+count2+"04'),");
                    builder.append("(" + (++count3) + ",'" + title + "批量删除',5,'/" + model + "/delBatch',1,now(),now()," + count2 + ",'" + model + "delBatch',0,'"+count2+"05'),");
                    builder.append("(" + (++count3) + ",'" + title + "保存',6,'/" + model + "/save',1,now(),now()," + count2 + ",'" + model + "save',0,'"+count2+"06'),");
                    builder.append("(" + (++count3) + ",'" + title + "更新',7,'/" + model + "/update',1,now(),now()," + count2 + ",'" + model + "update',0,'"+count2+"07'),");
                    builder.append("(" + (++count3) + ",'" + title + "更新保存',8,'/" + model + "/saveupdate',1,now(),now()," + count2 + ",'" + model + "saveupdate',0,'"+count2+"08')");
                    String sql = "INSERT INTO `travel_permission` (id,name,sorted,url,status,create_time,update_time,pid,pathname,isdelete,code)  VALUES " + builder.toString();
                    System.out.println(sql);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    int update = statement.executeUpdate();
                    List<String> lines = new ArrayList<>();
                    if (update > 0)
                    statement.close();
                } else {
                    System.out.println(title + "已经添加过了....");
                }
                rs2.close();
                rs.close();
                statement2.close();
                statement1.close();{
                    System.out.println(title + "菜单添加成功!!!");
                }
                connection.close();
                return this;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public Builder toRouter() {
            try {
                // 1: 转换成map
                Connection connection = travelViewConfig.getTravelDataSourceConfig().getConn();
                PreparedStatement statement = connection.prepareStatement("select * from travel_nav_menu");
                ResultSet rs = statement.executeQuery();
                StringBuilder builder = new StringBuilder();
                builder.append("import transferRouter from \"@/utils/menu\";\n");
                builder.append("\nconst MenuList = [\n");
                while (rs.next()) {
                    builder.append("    transferRouter(" + rs.getLong("id") + ", " + rs.getLong("pid") + ", \"" + rs.getString("name") + "\", \"" + rs.getString("pathname") + "\", \"" + rs.getString("path") + "\", import('" + rs.getString("componentname") + "'), \"" + rs.getString("icon") + "\", " + rs.getInt("indexon") + "," + (rs.getInt("showflag") == 1) + "),\n");
                }
                String str = builder.toString();
                String appenstr = str.substring(0, str.length() - 2);
                StringBuilder builder1 = new StringBuilder();
                builder1.append(appenstr);
                builder1.append("\n];\n\n");
                builder1.append("export default MenuList;");

                rs.close();
                statement.close();
                connection.close();
                File rootPath = new File(this.travelViewConfig.outputDir, this.travelViewConfig.routerPath);
                if (!rootPath.exists()) {
                    rootPath.mkdirs();
                }
                File targetFile = new File(rootPath, this.travelViewConfig.classname);
                FileUtils.writeStringToFile(targetFile, builder1.toString());

            } catch (Exception ex) {
                System.out.println("数据库出错了...");
            }

            return this;
        }


        /**
         * 开始构建
         */
        @Override
        public TravelViewConfig build() {
            return travelViewConfig;
        }
    }
}
