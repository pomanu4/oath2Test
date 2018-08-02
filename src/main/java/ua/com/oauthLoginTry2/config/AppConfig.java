package ua.com.oauthLoginTry2.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@PropertySource({"classpath:applicationProp/application.properties"})
@ComponentScans({
	@ComponentScan("ua.com.oauthLoginTry2.controller"),
	@ComponentScan("ua.com.oauthLoginTry2.serviceImpl"),
	@ComponentScan("ua.com.oauthLoginTry2.component")
})
@Import({SrcurityConfig.class, PersistenceConfig.class})
public class AppConfig  implements ApplicationContextAware, WebMvcConfigurer {
	
	@Autowired
	private Environment env;
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}
	
	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix(env.getProperty("pages.prefix"));
		resolver.setSuffix(env.getProperty("pages.suffix"));
		resolver.setTemplateMode(env.getProperty("template.version"));

		return resolver;
	}
	
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setEnableSpringELCompiler(true);
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setCharacterEncoding(env.getProperty("charse.encoding"));
		thymeleafViewResolver.setContentType(env.getProperty("content.type"));
		thymeleafViewResolver.setTemplateEngine(templateEngine());

		return thymeleafViewResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(env.getProperty("css.resource.marker"))
			.addResourceLocations(env.getProperty("css.resource.path"));
	}
	
	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasenames(env.getProperty("massage.source"));	
		source.setDefaultEncoding(env.getProperty("charse.encoding"));
		source.setUseCodeAsDefaultMessage(true);
		
		return source;
	}

}
