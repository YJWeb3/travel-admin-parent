package com.zheng.travel.admin;

import com.zheng.travel.admin.generator.config.TravelDataSourceConfig;
import com.zheng.travel.admin.generator.config.TravelDataTableSourceConfig;
import com.zheng.travel.admin.generator.freemarker.tool.AddKuohu2;
import com.zheng.travel.admin.generator.pojo.TableInfo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 飞哥
 * @Title: 学相伴出品
 * @Description: 飞哥B站地址：https://space.bilibili.com/490711252
 * 记得关注和三连哦！
 * @Description: 我们有一个学习网站：https://www.kuangstudy.com
 * @date 2022/2/16$ 21:24$
 */
public class Test {

    public static void main(String[] args) {
        try {

            // 初始化数据源
            TravelDataSourceConfig dataSourceConfig = TravelDataSourceConfig.builder()
                    .url("jdbc:mysql://127.0.0.1:3306/ksd-pug-travel?serverTimezone=GMT%2b8&useUnicode=true&characterEncoding=utf-8&useSSL=false")
                    .username("root")
                    .password("mkxiaoer").build();
            // 获取数据表信息

            TravelDataTableSourceConfig pugDataTableSourceConfig = TravelDataTableSourceConfig
                    .builder().tablename("travel_user").buildTableInfo(dataSourceConfig);


            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
            // 指定模板文件从何处加载的数据，这里设置成一个文件目录。
            File file = new ClassPathResource("/templates").getFile();
            // 设置模板存储目录位置
            cfg.setDirectoryForTemplateLoading(file);
            // 指定模板如何检索数据模型，这是一个高级的主题了… // 但先可以这么来用：
            cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
            // 创建根哈希表
            Map<String, Object> map = new HashMap();
            map.put("packagename", "com.pug.pojo");
            map.put("tablename", "travel_user");
            map.put("model", "User");
            map.put("kuohao2", new AddKuohu2());

            List<TableInfo> tableInfos = pugDataTableSourceConfig.getTableInfos();
            // 放入到数据模型中
            map.put("fields", tableInfos);

            // 找模板
            Template temp = cfg.getTemplate("testpojoc.tml");
            // 指定渲染以后页面生成的位置
            File targetFile = new File("e://User.java");
            Writer out = new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8);
            // freemakrer讲map数据和template=testpojo.tml进行替换渲染，然后把渲染以后的内容写入到你指定位置也好久是：e://User.java
            temp.process(map, out);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
