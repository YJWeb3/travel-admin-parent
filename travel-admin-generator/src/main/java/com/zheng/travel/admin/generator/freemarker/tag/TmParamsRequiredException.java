package com.zheng.travel.admin.generator.freemarker.tag;

import freemarker.template.TemplateModelException;

public class TmParamsRequiredException extends TemplateModelException {

    private static final long serialVersionUID = 1L;

    public TmParamsRequiredException(String paramName) {
        super("[Exmay Freemarker Parameter Exception]��The required \"" + paramName + "\" paramter is missing.");
    }
}