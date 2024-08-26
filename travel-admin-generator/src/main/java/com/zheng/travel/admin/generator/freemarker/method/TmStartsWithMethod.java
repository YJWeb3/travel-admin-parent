package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("startsWith")
public class TmStartsWithMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 2)
            throw new TemplateModelException("Wrong arguments!");
        String source = String.valueOf(args.get(0));
        String search = String.valueOf(args.get(1));
        return source.startsWith(search);
    }

}
