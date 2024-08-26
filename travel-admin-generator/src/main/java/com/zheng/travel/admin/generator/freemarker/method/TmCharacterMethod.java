package com.zheng.travel.admin.generator.freemarker.method;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("character")
public class TmCharacterMethod implements TemplateMethodModelEx {
    // 这里的长度计算，以汉字为标准，两个字母作为一个字符
    @SuppressWarnings("rawtypes")
    public Object exec(List args) throws TemplateModelException {
        if (args.size() > 1)
            throw new TemplateModelException("Wrong arguments!");
        int number = Integer.valueOf(String.valueOf(args.get(0)));
        return getCharacter(number);
    }

    public static String getCharacter(int num) {
        String cweek = "";
        if (num == 1)
            cweek = "A";
        if (num == 2)
            cweek = "B";
        if (num == 3)
            cweek = "C";
        if (num == 4)
            cweek = "D";
        if (num == 5)
            cweek = "E";
        if (num == 6)
            cweek = "F";
        if (num == 7)
            cweek = "G";
        if (num == 8)
            cweek = "H";
        if (num == 9)
            cweek = "I";
        if (num == 10)
            cweek = "J";
        if (num == 11)
            cweek = "K";
        if (num == 12)
            cweek = "M";
        if (num == 13)
            cweek = "L";
        if (num == 14)
            cweek = "N";
        if (num == 15)
            cweek = "O";
        if (num == 16)
            cweek = "P";
        if (num == 17)
            cweek = "Q";
        if (num == 18)
            cweek = "R";
        if (num == 19)
            cweek = "S";
        if (num == 20)
            cweek = "T";
        if (num == 21)
            cweek = "U";
        if (num == 22)
            cweek = "V";
        if (num == 23)
            cweek = "W";
        if (num == 24)
            cweek = "X";
        if (num == 25)
            cweek = "Y";
        if (num == 26)
            cweek = "Z";
        return cweek;
    }
}
