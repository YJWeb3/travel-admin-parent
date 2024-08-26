package com.zheng.travel.admin.generator.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class TmTemplateDirectiveModelUtil {

    public static final String OUT_BEAN = "tag_bean";
    public static final String OUT_LIST = "tag_list";
    public static final String OUT_PAGINATION = "tag_pagination";
    public static final String PARAM_TPL = "tpl";
    public static final String PARAM_TPL_SUB = "tplSub";

    // 添加变量
    public static Map<String, TemplateModel> addParamsToVariable(Environment env, Map<String, TemplateModel> params)
            throws TemplateException {

        Map origMap = new HashMap();
        if (params.size() <= 0) {
            return origMap;
        }

        for (Map.Entry entry : params.entrySet()) {
            String key = (String) entry.getKey();
            TemplateModel value = env.getVariable(key);
            if (value != null) {
                origMap.put(key, value);
            }
            env.setVariable(key, (TemplateModel) entry.getValue());
        }
        return origMap;
    }

    // 删除变量
    public static void removeParamsFromVariable(Environment env, Map<String, TemplateModel> params,
                                                Map<String, TemplateModel> origMap) throws TemplateException {
        if (params.size() <= 0) {
            return;
        }
        for (String key : params.keySet())
            env.setVariable(key, (TemplateModel) origMap.get(key));
    }

//	public static RequestContext getContext(Environment env)
//			throws TemplateException {
//		TemplateModel ctx = env.getGlobalVariable("springMacroRequestContext");
//
//		if (ctx instanceof AdapterTemplateModel) {
//			return ((RequestContext) ((AdapterTemplateModel) ctx)
//					.getAdaptedObject(RequestContext.class));
//		}
//
//		throw new TemplateModelException(
//				"RequestContext 'springMacroRequestContext' not found in DataModel.");
//	}

    // 获取String类型变量
    public static String getString(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel)
            return ((TemplateScalarModel) model).getAsString();
        if (model instanceof TemplateNumberModel) {
            return ((TemplateNumberModel) model).getAsNumber().toString();
        }
        throw new TmParameterMustStringException(name);
    }

    // 获取Integer类型变量
    public static Integer getInteger(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s)) {
                return null;
            }
            try {
                return Integer.valueOf(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                if (!StringUtils.isNumeric(String.valueOf(s))) {
                    return null;
                }
                throw new TmParameterMustNumberException(name);
            }
        }
        if (model instanceof TemplateNumberModel) {
            return Integer.valueOf(((TemplateNumberModel) model).getAsNumber().intValue());
        }
        throw new TmParameterMustNumberException(name);
    }

    // 获取Integer类型数组
    public static Integer[] getIntegerArray(String name, Map<String, TemplateModel> params) throws TemplateException {
        String str = getString(name, params);
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] arr = str.split(",");
        Integer[] ids = new Integer[arr.length];
        int i = 0;
        try {
            for (String s : arr) {
                ids[(i++)] = Integer.valueOf(s);
            }
            return ids;
        } catch (NumberFormatException e) {
            throw new TmParameterMustSplitNumberException(name, e);
        }
    }

    // 获取Long类型变量
    public static Long getLong(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();
            if (StringUtils.isBlank(s) || !StringUtils.isNumeric(String.valueOf(s))) {
                return null;
            }
            try {
                return Long.valueOf(Long.parseLong(s));
            } catch (NumberFormatException e) {
                if (!StringUtils.isNumeric(String.valueOf(s))) {
                    return null;
                }
                throw new TmParameterMustNumberException(name);
            }
        }
        if (model instanceof TemplateNumberModel) {
            return Long.valueOf(((TemplateNumberModel) model).getAsNumber().longValue());
        }
        throw new TmParameterMustNumberException(name);
    }

    // 获取Boolean型变量
    public static Boolean getBoolean(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateBooleanModel)
            return Boolean.valueOf(((TemplateBooleanModel) model).getAsBoolean());
        if (model instanceof TemplateNumberModel)
            return Boolean.valueOf(((TemplateNumberModel) model).getAsNumber().intValue() != 0);
        if (model instanceof TemplateScalarModel) {
            String s = ((TemplateScalarModel) model).getAsString();

            if (!(StringUtils.isBlank(s))) {
                return Boolean.valueOf(
                        (!(s.equals("0"))) && (!(s.equalsIgnoreCase("false"))) && (!(s.equalsIgnoreCase("f"))));
            }

            return null;
        }

        throw new TmParameterMustBooleanException(name);
    }

    // 获取Date型变量
    public static Date getDate(String name, Map<String, TemplateModel> params) throws TemplateException {
        TemplateModel model = (TemplateModel) params.get(name);
        if (model == null) {
            return null;
        }
        if (model instanceof TemplateDateModel)
            return ((TemplateDateModel) model).getAsDate();
        if (model instanceof TemplateScalarModel) {
            // DateTypeEditor editor = new DateTypeEditor();
            // editor.setAsText(((TemplateScalarModel)model).getAsString());
            // return ((Date)editor.getValue());
            return null;
        }
        throw new TmParameterMustDateException(name);
    }

//	public static InvokeType getInvokeType(Map<String, TemplateModel> params)
//			throws TemplateException {
//		String tpl = getString("tpl", params);
//		if ("3".equals(tpl))
//			return InvokeType.userDefined;
//		if ("2".equals(tpl))
//			return InvokeType.sysDefined;
//		if ("1".equals(tpl)) {
//			return InvokeType.custom;
//		}
//		return InvokeType.body;
//	}
//
//	public static enum InvokeType {
//		body, custom, sysDefined, userDefined;
//
//		// public static final InvokeType[] values()
//		// {
//		// return ((InvokeType[])$VALUES.clone());
//		// }
//	}
}