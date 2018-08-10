package com.ra.advertisement.config;

import javax.sql.DataSource;

import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.net.URL;

@Configuration
@ComponentScan("com.ra.advertisement")
@PropertySource("classpath:config.properties")
public class AdvertisementConfiguration {

    @Autowired
    private transient Environment env;

    /**
     * bean for creation of JdbcTemplate.
     *
     * @param dataSource DataSource
     * @return JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * method creates dataSource for H2 dataBase.
     *
     * @return dataSource
     */
    @Bean
    public DataSource simpleDataSource() {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }
}
