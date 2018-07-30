package com.ra.airport.config;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * * Spring configuration class for working with DB through {@link com.ra.airport.dao.impl.FlightDao} class.
 */
@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport.dao.impl")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private transient Environment environment;

    /**
     * Return {@link DataSource} bean.
     * @return data source bean
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(dataSourceConfig());
    }

    /**
     * Return {@link HikariConfig} bean. Set main properties to it.
     * @return return config for {@link DataSource} bean
     */
    @Bean
    public HikariConfig dataSourceConfig() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbc.url"));
        config.setUsername(environment.getProperty("jdbc.user"));
        config.setPassword(environment.getProperty("jdbc.password"));
        return config;
    }

    /**
     * Return {@link JdbcTemplate} bean.
     * @return template
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());

    }
}

