package com.zheng.travel.admin.generator.config;

import com.zheng.travel.admin.generator.freemarker.tool.*;
import com.zheng.travel.admin.generator.builder.ITravelConfigBuilder;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelCommonConfig {

    private TravelPackageConfig pugPackageConfig;
    private Boolean isoverride = false;


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements ITravelConfigBuilder<TravelCommonConfig> {
        private TravelCommonConfig pugCommonConfig;

        public Builder() {
            this.pugCommonConfig = new TravelCommonConfig();
        }

        @Override
        public TravelCommonConfig build() {
            return pugCommonConfig;
        }
    }

    public TravelCommonConfig create(TravelPackageConfig pugPackageConfig) {
        this.pugPackageConfig = pugPackageConfig;
        this.isoverride = isFileExist();
        return this;
    }

    private boolean isFileExist(){
        String rootPath = replaceLine(this.pugPackageConfig.getOutputDir()+"."+this.pugPackageConfig.getProjectName()+"."+this.pugPackageConfig.getMavenJavaPackage()+"."+
                this.pugPackageConfig.getRootPackage());
        File file = new File(rootPath,"PugWebApplication.java");
        return file.exists();
    }

    public TravelCommonConfig copyUtils() {
        template("utils", pugPackageConfig.getProjectJavaPath());
        return this;
    }

    public TravelCommonConfig copyConfig() {
        template("config", pugPackageConfig.getProjectJavaPath());
        return this;
    }

    public TravelCommonConfig copymain() {
        template("PugWebApplication.java", pugPackageConfig.getProjectJavaPath());
        return this;
    }

    public TravelCommonConfig copyIndexController() {
        template("controller", pugPackageConfig.getProjectJavaPath());
        return this;
    }


    public TravelCommonConfig copyService() {
        template("service", pugPackageConfig.getProjectJavaPath());
        return this;
    }


    public TravelCommonConfig copyLua() {
        templateResource("lua", pugPackageConfig.getProjectResourcePath());
        return this;
    }

    public TravelCommonConfig copyIp() {
        templateResource("ip", pugPackageConfig.getProjectResourcePath());
        return this;
    }

    public TravelCommonConfig copyStatic() {
        templateResource("static", pugPackageConfig.getProjectResourcePath());
        return this;
    }

    public TravelCommonConfig copyTemplates() {
        templateResource("templates", pugPackageConfig.getProjectResourcePath());
        return this;
    }

    public TravelCommonConfig copyApplicationxml() {
        template("application.yml", pugPackageConfig.getProjectResourcePath());
        template("application-dev.yml", pugPackageConfig.getProjectResourcePath());
        template("application-prod.yml", pugPackageConfig.getProjectResourcePath());
        return this;
    }


    public TravelCommonConfig templateResource(String target, String writerTargetPath) {
        try {
//            if(this.isoverride){
//                return this;
//            }
            // 指定模板文件从何处加载的数据源，这里设置成一个文件目录。
            File file = new ClassPathResource("/templates/").getFile();
            List<Map<String, String>> allFilePath = getAllFilePath(file + "\\" + target);
            allFilePath.forEach(filetemplate -> {
                try {
                    File targetFile = new File(writerTargetPath, filetemplate.get("relativepath"));
                    File parentFile = targetFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    FileUtils.copyFile(new File(filetemplate.get("filepath")), targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public TravelCommonConfig template(String target, String writerTargetPath) {
        try {
//            if(this.isoverride){
//                return this;
//            }

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
            // 指定模板文件从何处加载的数据源，这里设置成一个文件目录。
            File file = new ClassPathResource("/templates").getFile();
            cfg.setDirectoryForTemplateLoading(file);
            // 指定模板如何检索数据模型，这是一个高级的主题了… // 但先可以这么来用：
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));

            // 创建根哈希表
            Map root = new HashMap();
            // 在根中放入字符串"user"
            root.put("tablename", pugPackageConfig.getPugDataTableSourceConfig().getTablename());

            root.put("author", pugPackageConfig.getPugGlobalConfig().getAuthor());
            root.put("datetime", pugPackageConfig.getPugGlobalConfig().getDatetime());
            root.put("title", pugPackageConfig.getPugGlobalConfig().getTitle());
            root.put("burl", pugPackageConfig.getPugGlobalConfig().getUrl());
            root.put("version", pugPackageConfig.getPugGlobalConfig().getVersion());
            root.put("bootversion", pugPackageConfig.getPugGlobalConfig().getBootversion());
            root.put("gitlink", pugPackageConfig.getPugGlobalConfig().getGitlink());
            root.put("port", pugPackageConfig.getPugGlobalConfig().getPort());
            root.put("redispwd", pugPackageConfig.getPugGlobalConfig().getRedispwd());
            root.put("redisip", pugPackageConfig.getPugGlobalConfig().getRedisip());
            root.put("redisport", pugPackageConfig.getPugGlobalConfig().getRedisport());

            root.put("istree", pugPackageConfig.getIstree());

            root.put("projectName", pugPackageConfig.getProjectName());
            root.put("rootPackage", pugPackageConfig.getRootPackage());


            root.put("url", pugPackageConfig.getPugDataSourceConfig().getUrl());
            root.put("dbusername", pugPackageConfig.getPugDataSourceConfig().getUsername());
            root.put("dbpwd", pugPackageConfig.getPugDataSourceConfig().getPassword());


            root.put("kuohao", new AddKuohu());
            root.put("kuohao2", new AddKuohu2());
            root.put("kuohao3", new AddKuohu3());
            root.put("listtag", new ListTag());
            root.put("Include", new IncludeTagMethod());


            List<Map<String, String>> allFilePath = getAllFilePath(file + "/" + target);
            allFilePath.forEach(filetemplate -> {
                try {
                    Template temp = cfg.getTemplate(filetemplate.get("relativepath"));
                    File targetFile = new File(writerTargetPath, filetemplate.get("relativepath"));
                    File parentFile = targetFile.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    Writer out = new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8);
                    temp.process(root, out);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取给定文件夹下所有文件路径
     *
     * @param pathDir
     */
    public static List<Map<String, String>> getAllFilePath(File pathDir) {
        List<Map<String, String>> files = new ArrayList<>();

        if (pathDir.isFile()) {
            Map<String, String> map = new HashMap<>();
            map.put("filepath", pathDir.getAbsolutePath().replaceAll("\\\\", "/"));
            map.put("filename", pathDir.getName());
            map.put("relativepath", pathDir.getAbsolutePath().substring(pathDir.getAbsolutePath().indexOf("templates") + 9).replaceAll("\\\\", "/"));
            files.add(map);
            return files;
        }

        //递归
        recursionFile(pathDir.getAbsolutePath(), files);
        return files;
    }

    /**
     * 获取给定文件夹下所有文件路径
     *
     * @param pathDir
     */
    public static List<Map<String, String>> getAllFilePath(String pathDir) {
        return getAllFilePath(new File(pathDir));
    }

    /**
     * 递归获取文件夹下所有文件
     *
     * @param parentPath
     */
    public static void recursionFile(String parentPath, List<Map<String, String>> files) {
        //文件夹路径为文件对象
        File dir = new File(parentPath);
        //文件夹下所有的文件以及文件夹
        File[] listFiles = dir.listFiles();
        //遍历
        for (File file : listFiles) {
            //如果为文件
            if (file.isFile()) {
                Map<String, String> map = new HashMap<>();
                map.put("filepath", file.getAbsolutePath().replaceAll("\\\\", "/"));
                map.put("filename", file.getName());
                map.put("relativepath", file.getAbsolutePath().substring(file.getAbsolutePath().indexOf("templates") + 9).replaceAll("\\\\", "/"));
                files.add(map);
            } else {//为文件夹,调用递归
                recursionFile(parentPath + "\\" + file.getName() + "\\", files);
            }
        }
    }

    private static String replaceLine(String content) {
        return content.replaceAll("\\.", "/");
    }


}
