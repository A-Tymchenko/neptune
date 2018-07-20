package com.ra.airport.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport.dao.impl")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource () {
        return new HikariDataSource(dataSourceConfig());
    }

    @Bean
    public HikariConfig dataSourceConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbc.url"));
        config.setUsername(environment.getProperty("jdbc.user"));
        config.setPassword(environment.getProperty("jdbc.password"));
        return config;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
