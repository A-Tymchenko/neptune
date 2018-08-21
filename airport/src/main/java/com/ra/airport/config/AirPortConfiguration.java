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
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Spring configuration class for DAO layer.
 */
@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private transient Environment environment;


    /**
     * Register {@link DataSource} bean.
     *
     * @return data source bean
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(dataSourceConfig());
    }

    /**
     * Register {@link HikariConfig} bean. Set main properties to it.
     *
     * @return return config for {@link DataSource} bean
     */
    @Bean
    public HikariConfig dataSourceConfig() {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("jdbc.url"));
        config.setUsername(environment.getProperty("jdbc.user"));
        config.setPassword(environment.getProperty("jdbc.password"));
        config.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        return config;
    }

    /**
     * Register {@link JdbcTemplate} bean.
     *
     * @return template
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * Register NamedParameterJdbcTemplate bean.
     *
     * @return NamedParameterJdbcTemplate.
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    /**
     * Register ResourceDatabasePopulator bean for H2 dataBase to runScript.
     *
     * @return dataSource
     */
    @Bean
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("sql/create_table_skripts.sql"));
        databasePopulator.addScript(new ClassPathResource("sql/tables_backup(data).sql"));
        return databasePopulator;
    }

    /**
     * Register bean for DataSourceInitializer.
     *
     * @param dataSource Datasource
     * @param databasePopulator ResourceDatabasePopulator
     * @return DataSourceInitializer
     */
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource, final ResourceDatabasePopulator
            databasePopulator) {
        final DataSourceInitializer sourceInitializer = new DataSourceInitializer();
        sourceInitializer.setDatabasePopulator(databasePopulator);
        sourceInitializer.setDataSource(dataSource);
        return sourceInitializer;
    }
}
