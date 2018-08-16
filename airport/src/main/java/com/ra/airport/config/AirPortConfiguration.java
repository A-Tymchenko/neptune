package com.ra.airport.config;

import java.util.HashMap;
import java.util.Map;
import com.ra.airport.servlet.handler.*;
import javax.sql.DataSource;

import com.ra.airport.servlet.handler.factory.HandlerFactory;
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

/**
 * Spring configuration class for DAO layer.
 */
@PropertySource("classpath:config.properties")
@ComponentScan("com.ra.airport")
@Configuration
public class AirPortConfiguration {

    @Autowired
    private transient Environment environment;

    @Autowired
    private transient GetFlightsHandler getFlightsHand;

    @Autowired
    private transient CreateFlightHandler createFlightHand;

    @Autowired
    private transient DeleteFlightHandler deleteFlightHand;

    @Autowired
    private transient UpdateFlightHandler updateFlightHand;

    @Autowired
    private transient GetAirportsHandler airportsHandler;

    @Autowired
    private transient CreateAirportHandler createAirportHand;

    @Autowired
    private transient UpdateAirportHandler updateAirportHand;

    @Autowired
    private transient DeleteAirportHandler deleteAirportHand;

    @Autowired
    private transient CreatePlaneHandler createPlaneHand;

    /**
     * Register {@link DataSource} bean.
     * @return data source bean
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(dataSourceConfig());
    }

    /**
     * Register {@link HikariConfig} bean. Set main properties to it.
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
     * Register handlers map.
     * Using by {@link HandlerFactory}.
     *
     * @return Map.
     */
    @Bean
    public Map<String, ServletHandler> handlers() {
        final Map<String, ServletHandler> handlers = new HashMap<>();
        handlers.put("/flights", getFlightsHand);
        handlers.put("/flight/create", createFlightHand);
        handlers.put("/flight/delete", deleteFlightHand);
        handlers.put("/flight/update", updateFlightHand);
        handlers.put("/airports", airportsHandler);
        handlers.put("/airport/create", createAirportHand);
        handlers.put("/airport/update", updateAirportHand);
        handlers.put("/airport/delete", deleteAirportHand);
        handlers.put("/plane/create", createAirportHand);
        return handlers;
    }

    /**
     * Register {@link HandlerFactory} bean.
     *
     * @return handlerFactory.
     */
    @Bean
    public HandlerFactory handlerFactory() {
        return new HandlerFactory(handlers());
    }
}
