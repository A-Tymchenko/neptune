package com.ra.advertisement.config;

import javax.sql.DataSource;

import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.ra.advertisement")
@PropertySource("classpath:config.properties")
public class DataBaseConfiguration implements WebMvcConfigurer {

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
     * bean for DataSource.
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

    /**
     * bean for ResourceDatabasePopulator for H2 dataBase to runScript.
     *
     * @return dataSource
     */
    @Bean
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("./advertisement_db.sql"));
        return populator;
    }

    /**
     * bean for DataSourceInitializer.
     *
     * @param dataSource Datasource
     * @param populator  ResourceDatabasePopulator
     * @return DataSourceInitializer
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource, final ResourceDatabasePopulator populator) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDatabasePopulator(populator);
        initializer.setDataSource(dataSource);
        return initializer;
    }
}

