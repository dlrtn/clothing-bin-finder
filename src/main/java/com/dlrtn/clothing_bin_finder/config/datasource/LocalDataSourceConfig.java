package com.dlrtn.clothing_bin_finder.config.datasource;

import com.jcraft.jsch.JSchException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("local")
@Configuration
@RequiredArgsConstructor
public class LocalDataSourceConfig {

    private final SshTunnelingInitializer initializer;

    @Bean("dataSource")
    @Primary
    public DataSource dataSource(DataSourceProperties properties) throws JSchException {
        initializer.buildSession();
        Integer forwardedPort = initializer.buildTunneling();

        String url = properties.getUrl()
                .replace("[forwardedPort]", Integer.toString(forwardedPort));
        log.info("포워딩된 포트가 포함된 데이터베이스 URL: {}", url);

        return DataSourceBuilder.create()
                .url(url)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .driverClassName(properties.getDriverClassName())
                .build();
    }
}
