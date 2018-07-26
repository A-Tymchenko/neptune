package com.ra.advertisement.config;

import javax.sql.DataSource;

import com.ra.advertisement.dao.AdvertisementAdvertisementDaoImpl;
import com.ra.advertisement.dao.AdvertisementDao;
import com.ra.advertisement.dao.DeviceAdvertisementDaoImpl;
import com.ra.advertisement.dao.ProviderAdvertisementDaoImpl;
import com.ra.advertisement.dao.PublisherAdvertisementDaoImpl;
import com.ra.advertisement.entity.Advertisement;
import com.ra.advertisement.entity.Device;
import com.ra.advertisement.entity.Provider;
import com.ra.advertisement.entity.Publisher;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
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

    /**
     * bean for creation of AdvertisementAdvertisementDaoImpl.class.
     *
     * @param jdbcTemplate jdbcTemplate
     * @return AdvertisementAdvertisementDaoImpl
     */
    @Bean
    public AdvertisementDao advertDao(final JdbcTemplate jdbcTemplate) {
        return new AdvertisementAdvertisementDaoImpl(jdbcTemplate);
    }

    /**
     * bean for creation of DeviceAdvertisementDaoImpl.class.
     *
     * @param jdbcTemplate jdbcTemplate
     * @return DeviceAdvertisementDaoImpl
     */
    @Bean
    public AdvertisementDao deviceDao(final JdbcTemplate jdbcTemplate) {
        return new DeviceAdvertisementDaoImpl(jdbcTemplate);
    }

    /**
     * bean for creation of ProviderAdvertisementDaoImpl.class.
     *
     * @param jdbcTemplate jdbcTemplate
     * @return ProviderAdvertisementDaoImpl
     */
    @Bean
    public AdvertisementDao providerDao(final JdbcTemplate jdbcTemplate) {
        return new ProviderAdvertisementDaoImpl(jdbcTemplate);
    }

    /**
     * bean for creation of PublisherAdvertisementDaoImpl.class.
     *
     * @param jdbcTemplate jdbcTemplate
     * @return PublisherAdvertisementDaoImpl
     */
    @Bean
    public AdvertisementDao publisherDao(final JdbcTemplate jdbcTemplate) {
        return new PublisherAdvertisementDaoImpl(jdbcTemplate);
    }

    /**
     * bean creates Advertisement.
     *
     * @return new Advertisement
     */
    @Bean
    public Advertisement createAdvertisement() {
        return new Advertisement();
    }

    /**
     * bean creates Device.
     *
     * @return new Device
     */
    @Bean
    public Device createDevice() {
        return new Device();
    }

    /**
     * bean creates Provider.
     *
     * @return new Provider
     */
    @Bean
    public Provider createProvider() {
        return new Provider();
    }

    /**
     * bean creates Publisher.
     *
     * @return new Publisher
     */
    @Bean
    public Publisher createPublisher() {
        return new Publisher();
    }

}
