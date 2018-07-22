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

@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private transient Environment environment;

    /**
     * Method rigister dataSource Bean.
     *
     * @return HikariDataSource.
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(dataSourceConfig());
    }

    /**
     * Method rigister dataSourceConfig Bean.
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
     * Method rigister jdbcTemplate Bean.
     *
     * @return JdbcTemplate.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
