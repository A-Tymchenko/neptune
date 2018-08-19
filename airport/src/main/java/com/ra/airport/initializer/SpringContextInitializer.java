package com.ra.airport.initializer;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;
import javax.servlet.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class SpringContextInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx =
                new AnnotationConfigWebApplicationContext();

        ctx.register(AirPortConfiguration.class);
        ctx.register(AirPortWebConfig.class);
        ctx.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("springDispatcherServlet",
                        new DispatcherServlet(ctx));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
