package com.dlrtn.clothing_bin_finder.config.datasource;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import java.util.Properties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
@ConfigurationProperties(prefix = "ssh")
@Setter
public class SshTunnelingInitializer {

    private String sshHost;
    private int sshPort;
    private String sshUsername;
    private String privateKey;
    private String passPhrase;

    private int remoteDatabasePort;
    private String remoteDatabaseHost;

    private Session session;

    @PreDestroy
    public void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            log.info("SSH 세션이 종료되었습니다");
        }
        log.info("SSH 세션이 연결되지 않았습니다");
    }

    public Integer buildTunneling() throws JSchException {
        try {
            int forwardedPort = session.setPortForwardingL(0, remoteDatabaseHost,
                    remoteDatabasePort);

            log.info("SSH 터널이 로컬 포트 {}에 설정되었습니다", forwardedPort);
            return forwardedPort;
        } catch (JSchException e) {
            log.error("터널링 실패", e);
            this.close();
            throw e;
        }
    }

    public void buildSession() throws JSchException {
        try {
            JSch jSch = new JSch();
            jSch.addIdentity(privateKey, passPhrase);
            session = jSch.getSession(sshUsername, sshHost, sshPort);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            log.info("SSH 세션이 연결되었습니다");
        } catch (JSchException e) {
            log.error("SSH 세션 설정 실패", e);
            this.close();
            throw e;
        }
    }
}
