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

@Configuration
@ComponentScan("com.ra.shop.repository.implementation")
@PropertySource("classpath:db.properties")
public class ShopConfiguration {

    @Autowired
    private transient Environment environment;

    /**
     * bean for creation JdbcTemplate.
     *
     * @return JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * bean for creation HikariDataSource.
     *
     * @return HikariDataSource
     */
    @Bean
    public HikariDataSource dataSource() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("URL"));
        config.setUsername(environment.getProperty("USERNAME"));
        config.setPassword(environment.getProperty("PASSWORD"));
        return new HikariDataSource(config);
    }
}
