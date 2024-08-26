package com.zheng.travel.admin.generator.freemarker;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class FreemarkerView extends FreeMarkerView {

	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		String base = RequestUtil.getBasePath(request);
		model.put("basePath", base);
		model.put("adminPath", base+"/admin");
		super.exposeHelpers(model, request);
	}

}
