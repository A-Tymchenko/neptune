package com.ra.shop.config;

import com.ra.shop.repository.implementation.OrderRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@PropertySource(value = "classpath:db.properties")
@ComponentScan("com.ra.shop.repository.implementation")
@Configuration
public class AppConfiguration {

    @Autowired
    private transient Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        final Logger LOGGER = LogManager.getLogger("JdbcTemplate");
        LOGGER.info("JdbcTemplate autowired shop application");
        return new JdbcTemplate(dataSource);
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
