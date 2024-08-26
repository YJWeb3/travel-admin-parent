package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Component("mkurl")
public class TmURLMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 0)
            throw new TemplateModelException("Wrong arguments!");
        return "t=" + (new Random(10000).nextLong() + new Date().getTime()) * -1;
    }
}
