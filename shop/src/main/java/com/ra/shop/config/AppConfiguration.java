package com.ra.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:cfg.properties")
@ComponentScan("com.ra.shop.repository.impl")
public class AppConfiguration {

    @Autowired
    private transient Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(buildDataSource());
    }

    @Bean
    public DataSource buildDataSource() {
        return new DriverManagerDataSource(
                environment.getProperty("URL"),
                environment.getProperty("USERNAME"),
                environment.getProperty("PASSWORD"));
    }

}
