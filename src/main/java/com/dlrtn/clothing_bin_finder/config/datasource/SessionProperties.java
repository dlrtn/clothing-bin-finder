package com.dlrtn.clothing_bin_finder.config.datasource;

import com.dlrtn.clothing_bin_finder.config.datasource.properties.SshSessionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SshSessionProperties.class)
public class SessionProperties {
    private final SshSessionProperties sshSessionProperties;
    private Properties properties;

    public Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
        }
        return properties;
    }

    public String getHost() {
        return sshSessionProperties.getHost();
    }

    public String getUsername() {
        return sshSessionProperties.getUsername();
    }

    public int getPort() {
        return sshSessionProperties.getPort();
    }

    public String getPrivateKey() {
        return sshSessionProperties.getPrivateKey();
    }

    public String getPassphrase() {
        return sshSessionProperties.getPassphrase();
    }
}
