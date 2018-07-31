package com.ra.shop.config;

import com.ra.shop.repository.impl.OrderRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@PropertySource(value = "classpath:cfg.properties")
@ComponentScan("com.ra.shop.repository.impl")
@Configuration
public class AppConfiguration {

    @Autowired
    private transient Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(buildDataSource());
    }

    @Bean
    public DataSource buildDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("URL"));
        dataSource.setUsername(environment.getProperty("USERNAME"));
        dataSource.setPassword(environment.getProperty("PASSWORD"));
        return dataSource;
    }

}
