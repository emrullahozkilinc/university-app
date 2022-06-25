package com.example.universityapp.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.example.universityapp.dao")
public class DBConf {

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String URL = "jdbc:h2:file:/data/sample";
        dataSource.setUrl(URL);
        String USER = "user";
        dataSource.setUsername(USER);
        String PASSWORD = "pass";
        dataSource.setPassword(PASSWORD);
        String DRIVER = "org.h2.Driver";
        dataSource.setDriverClassName(DRIVER);


        return dataSource;
    }
}
