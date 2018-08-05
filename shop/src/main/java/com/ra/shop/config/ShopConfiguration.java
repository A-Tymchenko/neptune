package com.ra.shop.config;

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

/**
 * Configuration class contains jdbc template and data source config.
 */
@Configuration
@PropertySource(value = "classpath:cfg.properties")
@ComponentScan("com.ra.shop.repository.implementation")
public class ShopConfiguration {

    /**
     * Spring environment that stores database properties.
     */
    @Autowired
    private transient Environment environment;

    /**
     * Jdbc template for operations with database.
     *
     * @return JdbcTemplate instance of jdbcTemplate.
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(buildDataSource());
    }

    /**
     * Hikari DataSource with database properties.
     *
     * @return DataSource instance with configured database properties.
     */
    @Bean
    public DataSource buildDataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("URL"));
        config.setUsername(environment.getProperty("USERNAME"));
        config.setPassword(environment.getProperty("PASSWORD"));
        return new HikariDataSource(config);
    }

}
