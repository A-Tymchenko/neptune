package com.ra.airport.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import com.ra.airport.config.AirPortConfiguration;
import com.ra.airport.config.AirPortWebConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringContextInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(final ServletContext servletContext) {
        final AnnotationConfigWebApplicationContext ctx =
                new AnnotationConfigWebApplicationContext();

        ctx.register(AirPortConfiguration.class);
        ctx.register(AirPortWebConfig.class);
        ctx.setServletContext(servletContext);

        final ServletRegistration.Dynamic servlet =
                servletContext.addServlet("springDispatcherServlet",
                        new DispatcherServlet(ctx));

        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
