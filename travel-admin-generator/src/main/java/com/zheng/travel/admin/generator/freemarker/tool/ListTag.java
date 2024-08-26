package com.zheng.travel.admin.generator.freemarker.tool;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class ListTag implements TemplateMethodModelEx {
    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 2) {
            throw new TemplateModelException("Wrong arguments!");
        }

        String model = String.valueOf(args.get(0));
        String template = String.valueOf(args.get(1));
        return "<#list  pages.list as " + model + ">\n" +
                "<#if " + model + "??>\n"
                + template +
                "\n</#if>\n" +
                "</#list>";
    }

}
