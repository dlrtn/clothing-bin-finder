package com.dlrtn.clothing_bin_finder.config.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ssh")
public class SshSessionProperties {
    private String host;
    private String username;
    private int port;
    private String privateKey;
    private String passphrase;
}
