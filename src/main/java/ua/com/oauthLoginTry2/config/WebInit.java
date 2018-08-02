package ua.com.oauthLoginTry2.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInit implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfig.class);

		servletContext.addListener(new ContextLoaderListener(context));

		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet", servlet);
		dynamic.addMapping("/");
		dynamic.setLoadOnStartup(1);
		dynamic.setMultipartConfig(new MultipartConfigElement("", 10000000, 10000000, 10000000));
	}
	
	

}
