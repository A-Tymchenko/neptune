package com.ra.airport.configuration;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport.dao.impl")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private transient Environment environment;

    /**
     * Method register dataSource Bean.
     *
     * @return HikariDataSource.
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(dataSourceConfig());
    }

    /**
     * Method register dataSourceConfig Bean.
     *
     * @return HikariConfig.
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
     * Method register jdbcTemplate Bean.
     *
     * @return JdbcTemplate.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * Method register NamedParameterJdbcTemplate Bean.
     *
     * @return NamedParameterJdbcTemplate.
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }
}
