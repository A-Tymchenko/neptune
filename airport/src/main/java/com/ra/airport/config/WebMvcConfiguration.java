package com.ra.airport.config;

import javax.validation.Validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Author: anbo.
 * Date: 19.08.2018
 */
@WebAppConfiguration
@EnableWebMvc
@Configuration
public class WebMvcConfiguration {

    /**
     * Register ViewResolver bean.
     *
     * @return ViewResolver
     */
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Register Validator.
     *
     * @return validator
     */
    @Bean
    public Validator validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Register {@link ObjectMapper} bean.
     *
     * @return return {@link ObjectMapper} bean
     */
    @Bean
    public ObjectMapper objectMapper() {
        final var objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
