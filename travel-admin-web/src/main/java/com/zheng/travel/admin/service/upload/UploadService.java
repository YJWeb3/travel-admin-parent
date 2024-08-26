package com.zheng.travel.admin.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.staticPath}")
    private String staticPath;

    /**
     * MultipartFile 这个对象是springMvc提供的文件上传的接受的类，
     * 它的底层自动会去和HttpServletRequest request中的request.getInputStream()融合
     * 从而达到文件上传的效果。也就是告诉你一个道理：
     * 文件上传底层原理是：request.getInputStream()
     *
     * @param multipartFile
     * @param dir
     * @return
     */
    public String uploadImg(MultipartFile multipartFile, String dir) {
        try {
            String realfilename = multipartFile.getOriginalFilename(); // 上传的文件：aaa.jpg
            // 2:截图文件名的后缀
            String imgSuffix = realfilename.substring(realfilename.lastIndexOf("."));// 拿到：.jpg
            // 3:生成的唯一的文件名：能不能用中文名：不能因为统一用英文命名。
            String newFileName = UUID.randomUUID().toString()+imgSuffix;// 将aaa.jpg改写成：SD23423k324-23423ms.jpg
            // 4：日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());// 日期目录：2021/10/27
            // 5: 指定文件上传以后的目录
            String servrepath = uploadFolder;// 这不是tomcat服务目录，别人不认识
            File targetPath = new File(servrepath+dir,datePath);// 生成一个最终目录:F://tmp/avatar/2021/10/27
            if(!targetPath.exists()){
                targetPath.mkdirs(); // 如果目录不存在：F://tmp/avatar/2021/10/27 递归创建
            }
            // 6: 指定文件上传以后的服务器的完整的文件名
            File targetFileName = new File(targetPath,newFileName);// 文件上传以后在服务器上最终文件名和目录是：F://tmp/avatar/2021/10/27/SD23423k324-23423ms.jpg
            // 7: 文件上传到指定的目录
            multipartFile.transferTo(targetFileName);//将用户选择的aaa.jpg上传到F://tmp/avatar/2021/10/27/SD23423k324-23423ms.jpg
            // 可访问的路径要返回页面
            String filename = dir+"/"+datePath+"/"+newFileName;
            return staticPath+"/upimages/"+filename;
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }


    /**
     * MultipartFile 这个对象是springMvc提供的文件上传的接受的类，
     * 它的底层自动会去和HttpServletRequest request中的request.getInputStream()融合
     * 从而达到文件上传的效果。也就是告诉你一个道理：
     * 文件上传底层原理是：request.getInputStream()
     *
     * @param multipartFile
     * @param dir
     * @return
     */
    public Map<String,Object> uploadImgMap(MultipartFile multipartFile, String dir) {
        try {
            String realfilename = multipartFile.getOriginalFilename(); // 上传的文件：aaa.jpg
            // 2:截图文件名的后缀
            String imgSuffix = realfilename.substring(realfilename.lastIndexOf("."));// 拿到：.jpg
            // 3:生成的唯一的文件名：能不能用中文名：不能因为统一用英文命名。
            String newFileName = UUID.randomUUID().toString()+imgSuffix;// 将aaa.jpg改写成：SD23423k324-23423ms.jpg
            // 4：日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());// 日期目录：2021/10/27
            // 5: 指定文件上传以后的目录
            String servrepath = uploadFolder;// 这不是tomcat服务目录，别人不认识
            File targetPath = new File(servrepath+dir,datePath);// 生成一个最终目录:F://tmp/avatar/2021/10/27
            if(!targetPath.exists())targetPath.mkdirs(); // 如果目录不存在：F://tmp/avatar/2021/10/27 递归创建
            // 6: 指定文件上传以后的服务器的完整的文件名
            File targetFileName = new File(targetPath,newFileName);// 文件上传以后在服务器上最终文件名和目录是：F://tmp/avatar/2021/10/27/SD23423k324-23423ms.jpg
            // 7: 文件上传到指定的目录
            multipartFile.transferTo(targetFileName);//将用户选择的aaa.jpg上传到F://tmp/avatar/2021/10/27/SD23423k324-23423ms.jpg
            // 可访问的路径要返回页面
            // http://localhpst:8777/bbs/2021/10/27/5f61dea2-4b77-4068-8d0b-fdf415eac6df.png
            String filename = dir+"/"+datePath+"/"+newFileName;

            Map<String,Object> map = new HashMap<>();
            map.put("url",staticPath+"/upimages/"+filename);
            map.put("size",multipartFile.getSize());
            map.put("ext",imgSuffix);
            map.put("filename",realfilename);
            map.put("rpath",dir+"/"+datePath+"/"+newFileName);

            // ftp 远程服务器文件io
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
