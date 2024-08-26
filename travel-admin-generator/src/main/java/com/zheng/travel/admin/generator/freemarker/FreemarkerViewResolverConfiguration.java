package com.zheng.travel.admin.generator.freemarker;

import freemarker.template.TemplateModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class FreemarkerViewResolverConfiguration  implements  ApplicationContextAware, CommandLineRunner {
	
	private ApplicationContext context;

	@Autowired
	private FreeMarkerViewResolver resolver;

	@PostConstruct
	public void init() {
		System.err.println("注册freemaker了...............");
		// 注册路由视图
		resolver.setViewClass(FreemarkerView.class);
		Map<String, TemplateModel> beans = (Map<String, TemplateModel>) context.getBeansOfType(TemplateModel.class);
		for (Map.Entry<String, TemplateModel> entry : beans.entrySet()) {
			resolver.getAttributesMap().put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	@Override
	public void run(String... args) throws Exception {
		init();
	}
}