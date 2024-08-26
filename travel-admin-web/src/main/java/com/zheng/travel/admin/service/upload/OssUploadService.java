package com.zheng.travel.admin.service.upload;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssUploadService {

    public static void main(String[] args) {
        String uploadfile = new OssUploadService().uploadfile(new File("f://404.png"));
        System.out.println(uploadfile);
    }

    public String uploadfile(File multipartFile) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOssConstans.endPoint;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOssConstans.accessKeyId;
        String accessKeySecret =  AliyunOssConstans.accessKeySecret;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = AliyunOssConstans.bucketName;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            // 2: 获取文件上传的流
            InputStream inputStream = new FileInputStream(multipartFile);
            // 3：构建日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());// 日期目录：2021/10/27

            // 4: 获取文件名
            String originname = multipartFile.getName();
            String filename = UUID.randomUUID().toString();
            String suffix = originname.substring(originname.lastIndexOf("."));
            String newName = filename + suffix;
            String fileUrl = datePath + "/" + newName;
            //5：文件上传到阿里云服务器
            ossClient.putObject(bucketName, fileUrl, inputStream);
            return "https://" + bucketName + "." + endpoint + "/" + fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        } finally {
            ossClient.shutdown();
        }
    }

    public  String uploadfile(MultipartFile multipartFile,String dir) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOssConstans.endPoint;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOssConstans.accessKeyId;
        String accessKeySecret =  AliyunOssConstans.accessKeySecret;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = AliyunOssConstans.bucketName;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 2: 获取文件上传的流
            InputStream inputStream = multipartFile.getInputStream();
            // 3：构建日期目录
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());// 日期目录：2021/10/27

            // 4: 获取文件名
            String originname = multipartFile.getOriginalFilename();
            String filename = UUID.randomUUID().toString();
            String suffix = originname.substring(originname.lastIndexOf("."));
            String newName = filename + suffix;
            String fileUrl = dir+"/"+datePath + "/" + newName;
            //5：文件上传到阿里云服务器
            ossClient.putObject(bucketName, fileUrl, inputStream);

            return "https://" + bucketName + "." + endpoint + "/" + fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        } finally {
            ossClient.shutdown();
        }

    }
}
