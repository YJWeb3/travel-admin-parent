package com.zheng.travel.admin.controller.code;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zheng.travel.admin.commons.utils.pwd.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class KaptchaCodeController {

    @Autowired
    private DefaultKaptcha producer;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @RequestMapping("/admin/captcha")
    public Map<String, Object> captcha(HttpSession session) throws IOException {
        /**
         * 前后端分离 登录验证码 方案
         * 后端生成图片 验证码字符串 uuid
         * uuid为key  验证码字符串为value
         * 传递bs64图片 和uuid给前端
         * 前端在登录的时候 传递 账号 密码 验证码 uuid
         * 通过uuid获取 验证码 验证
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //获取验证码
        String text = producer.createText();
        log.info("1--------------->登录验证码：" + text);

        BufferedImage image = producer.createImage(text);
        ImageIO.write(image, "png", out);
        String base64bytes = Base64.encode(out.toByteArray());

        //该字符串传输至前端放入src即可显示图片，安卓可以去掉data:image/png;base64,
        String src = "data:image/png;base64," + base64bytes;

        String redisTokenKey = UUID.randomUUID().toString();
        Map<String, Object> map = new HashMap<>(2);
        map.put("token", redisTokenKey);
        map.put("img", src);
        // 把生成的验证码放入到session中 spring-session
        //session.setAttribute("code", text);// 自动放入到redis
        log.info("2--------------->验证码生成完毕");
        // 这里为什么要设置时间，因为如果不设置时间，验证生成很频繁，其实一直放在内存中其实没必要的事情，所有设置一个有效期，自动从redis内存中删除
        stringRedisTemplate.opsForValue().set(redisTokenKey, text,5, TimeUnit.MINUTES);
        return map;
    }
}
