package com.zheng.travel.admin.controller.upload;

import com.alibaba.fastjson.JSONObject;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.service.upload.OssUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
public class OssController extends BaseController {

    @Autowired
    OssUploadService ossUploadService;

    @PostMapping("/upload/oss/editor")
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        String dir = Optional.ofNullable(request.getParameter("dir")).orElse("bbs");
        // 上传图片文件到oss
        String url = ossUploadService.uploadfile(file, dir);
        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url", url);
        res.put("success", "1");
        res.put("message", "upload success!");
        return res;
    }

    @PostMapping("/upload/oss/file")
    public JSONObject fileUpload2(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        String dir = Optional.ofNullable(request.getParameter("dir")).orElse("bbs");
        // 上传图片文件到oss
        String url = ossUploadService.uploadfile(file, dir);
        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url", url);
        res.put("success", "1");
        res.put("message", "upload success!");
        return res;
    }
}
