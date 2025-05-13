package com.dlrtn.clothing_bin_finder.config.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "remote-database")
public class DatabaseHostProperties {
    private String host;
    private int port;
}
