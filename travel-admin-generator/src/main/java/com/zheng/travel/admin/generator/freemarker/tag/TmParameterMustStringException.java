package com.zheng.travel.admin.generator.freemarker.tag;

import freemarker.template.TemplateModelException;

public class TmParameterMustStringException extends TemplateModelException {

    private static final long serialVersionUID = 1L;

    public TmParameterMustStringException(String paramName) {
        super("[Exmay Freemarker Parameter Exception]��The \"" + paramName + "\" parameter must be a string.");
    }
}