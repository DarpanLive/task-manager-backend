package com.TaskManager.TaskManager.Config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${spring.datasource.url}") String datasourceUrl,
                                 @Value("${spring.datasource.username:}") String datasourceUsername,
                                 @Value("${spring.datasource.password:}") String datasourcePassword) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");

        if (datasourceUrl.startsWith("postgres://") || datasourceUrl.startsWith("postgresql://")) {
            URI uri = URI.create(datasourceUrl);
            dataSource.setJdbcUrl("jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath());
            if (uri.getUserInfo() != null) {
                String[] credentials = uri.getUserInfo().split(":", 2);
                dataSource.setUsername(decode(credentials[0]));
                if (credentials.length > 1) {
                    dataSource.setPassword(decode(credentials[1]));
                }
            }
        } else {
            dataSource.setJdbcUrl(datasourceUrl);
            dataSource.setUsername(datasourceUsername);
            dataSource.setPassword(datasourcePassword);
        }
        return dataSource;
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}

