package com.zheng.travel.admin.controller.upload;

import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private UploadService uploadService;

    /**
     * 本地文件直接返回图片地址
     */
    @PostMapping("/upload/file")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException, ServletException {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String dir = request.getParameter("dir");
        if(StringUtils.isEmpty(dir)){
            dir = "course";
        }
        // handler调用文件上传的service 得到文件的虚拟路径
        String filepath = uploadService.uploadImg(file,dir);
        return filepath;
    }


    /**
     * 本地文件上传返回json
     * @param file
     * @param request
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping("/upload/filemap")
    @ResponseBody
    public Map<String,Object> uploadJson(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException, ServletException {
        if (file.isEmpty()) {
            return null;
        }
        String dir = request.getParameter("dir");
        if(StringUtils.isEmpty(dir)){
            dir = "course";
        }
        // handler调用文件上传的service 得到文件的虚拟路径
        String filepath = uploadService.uploadImg(file,dir);
        Map<String,Object> map = new HashMap<>();
        map.put("path",filepath);
        map.put("url",filepath);
        return map;
    }
}
