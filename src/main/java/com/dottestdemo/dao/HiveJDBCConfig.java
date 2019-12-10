package com.dottestdemo.dao;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class HiveJDBCConfig {


    @Autowired
    private Environment env;


    @Bean("hiveJdbcDataSource")
    @Qualifier("hiveJdbcDataSource")
    public DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(env.getProperty("hive.url"));
        dataSource.setDriverClassName(env.getProperty("hive.driver-class-name"));
        dataSource.setUsername(env.getProperty("hive.user"));
        dataSource.setPassword(env.getProperty("hive.password"));
        System.out.println("Hive DataSource Inject Successfully...");
        return dataSource;
    }

    @Bean("hiveJdbcTemplate")
    public JdbcTemplate hiveJdbJdbcTemplatecTemplate(@Qualifier("hiveJdbcDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
