package com.example.universityapp.conf;

import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.universityapp.dao")
public class DBConf {

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        String URL = "jdbc:h2:file:/data/sample";
        driverManagerDataSource.setUrl(URL);
        String USER = "user";
        driverManagerDataSource.setUsername(USER);
        String PASSWORD = "pass";
        driverManagerDataSource.setPassword(PASSWORD);
        String DRIVER = "org.h2.Driver";
        driverManagerDataSource.setDriverClassName(DRIVER);


        return driverManagerDataSource;
    }
}
