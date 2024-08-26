package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("week")
public class TmWeekDateMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 1)
            throw new TemplateModelException("Wrong arguments!");
        try {
            return getWeekOfDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(args.get(0))));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
}
