package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component("NumToChina")
public class TmNumberToChinaMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 2)
            throw new TemplateModelException("Wrong arguments!");
        Integer num = Integer.valueOf(String.valueOf(args.get(0)));
        return intToChnNumConverter(num);
    }

    public static String intToChnNumConverter(int num) {
        String resultNumber = null;
        if (num > 10000 || num < 0) {
            return "";
        }
        HashMap<Integer, String> chnNumbers = new HashMap<Integer, String>();
        chnNumbers.put(0, "零");
        chnNumbers.put(1, "一");
        chnNumbers.put(2, "二");
        chnNumbers.put(3, "三");
        chnNumbers.put(4, "四");
        chnNumbers.put(5, "五");
        chnNumbers.put(6, "六");
        chnNumbers.put(7, "七");
        chnNumbers.put(8, "八");
        chnNumbers.put(9, "九");

        HashMap<Integer, String> unitMap = new HashMap<Integer, String>();
        unitMap.put(1, "");
        unitMap.put(10, "十");
        unitMap.put(100, "百");
        unitMap.put(1000, "千");
        int[] unitArray = {1000, 100, 10, 1};

        StringBuilder result = new StringBuilder();
        int i = 0;
        while (num > 0) {
            int n1 = num / unitArray[i];
            if (n1 > 0) {
                result.append(chnNumbers.get(n1)).append(unitMap.get(unitArray[i]));
            }
            if (n1 == 0) {
                if (result.lastIndexOf("零") != result.length() - 1) {
                    result.append("零");
                }
            }
            num = num % unitArray[i++];
            if (num == 0) {
                break;
            }
        }
        resultNumber = result.toString();
        if (resultNumber.startsWith("零")) {
            resultNumber = resultNumber.substring(1);
        }
        if (resultNumber.startsWith("一十")) {
            resultNumber = resultNumber.substring(1);
        }
        return resultNumber;
    }
}
