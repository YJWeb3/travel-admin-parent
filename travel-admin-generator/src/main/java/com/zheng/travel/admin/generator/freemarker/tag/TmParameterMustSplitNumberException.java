package com.zheng.travel.admin.generator.freemarker.tag;

import freemarker.template.TemplateModelException;

public class TmParameterMustSplitNumberException extends TemplateModelException {

    private static final long serialVersionUID = 1L;

    public TmParameterMustSplitNumberException(String paramName) {
        super("[Exmay Freemarker Parameter Exception]��The \"" + paramName
                + "\" parameter must be a number split by ','");
    }

    public TmParameterMustSplitNumberException(String paramName, Exception cause) {
        super("[Exmay Freemarker Parameter Exception]��The \"" + paramName
                + "\" parameter must be a number split by ','", cause);
    }
}