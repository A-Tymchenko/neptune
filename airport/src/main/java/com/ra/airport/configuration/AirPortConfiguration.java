package com.ra.airport.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@PropertySource("classpath:config.properties")
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
        HikariConfig config = new HikariConfig(environment.getProperty());

        config.setJdbcUrl(environment.getProperty("jdbc.url"));
        config.setUsername(environment.getProperty("jdbc.user"));
        config.setPassword(environment.getProperty("jdbc.password"));
        return config;
    }
}
