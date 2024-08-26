package com.zheng.travel.admin.generator.freemarker.tool;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

public class AddKuohu2 implements TemplateMethodModelEx {
	
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() > 1) {
			throw new TemplateModelException("Wrong arguments!");
		}

		String numstr = String.valueOf(args.get(0));
		return  "${"+numstr+"}";
	}
}
