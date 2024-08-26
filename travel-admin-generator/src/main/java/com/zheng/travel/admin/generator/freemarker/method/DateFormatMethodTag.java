package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.ext.beans.DateModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by shiqm on 2017-05-03.
 */
@Component("dateFormat")
public class DateFormatMethodTag implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (null != arguments && 2 == arguments.size()) {
            DateModel date = (DateModel) arguments.get(0);
            String pattern = String.valueOf(arguments.get(1));
            String dateStr = new SimpleDateFormat(pattern).format(date.getAsDate());
            return dateStr;
        } else {
            return arguments.get(0);
        }
    }
}