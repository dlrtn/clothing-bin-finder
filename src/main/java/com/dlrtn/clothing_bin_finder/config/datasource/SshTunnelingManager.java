package com.dlrtn.clothing_bin_finder.config.datasource;

import com.dlrtn.clothing_bin_finder.config.datasource.exception.SshTunnelingException;
import com.dlrtn.clothing_bin_finder.config.datasource.properties.DatabaseHostProperties;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(DatabaseHostProperties.class)
public class SshTunnelingManager {

    private final SessionManager sessionManager;
    private final DatabaseHostProperties databaseHostProperties;

    public Integer setupPortForwarding(int localPort) {
        try {
            Session session = sessionManager.buildSession();

            int forwardedPort = session.setPortForwardingL(
                    localPort,
                    databaseHostProperties.getHost(),
                    databaseHostProperties.getPort());

            log.info("SSH 터널이 로컬 포트 {}에 설정되었습니다", forwardedPort);
            return forwardedPort;
        } catch (JSchException e) {
            log.error("SSH 터널링 실패", e);
            sessionManager.close();
            throw SshTunnelingException.of(e);
        }
    }

    public Integer setupPortForwarding() {
        return setupPortForwarding(0); // 기본값으로 0 전달
    }
}
