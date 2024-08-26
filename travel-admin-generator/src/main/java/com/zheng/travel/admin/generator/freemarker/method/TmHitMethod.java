package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

@Component("hit")
public class TmHitMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 2)
            throw new TemplateModelException("Wrong arguments!");

        String numstr = String.valueOf(args.get(0));
        Float num = Float.valueOf(numstr);
        if (num > 1000) {
            num = num / 100f;
            return new DecimalFormat("#.#").format(num) + "K";
        } else if (num > 10000) {
            num = num / 1000f;
            return new DecimalFormat("#.#").format(num) + "W";
        } else {
            return StringUtils.isEmpty(numstr) ? 0 : numstr;
        }

    }

}
