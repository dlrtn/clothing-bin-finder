package com.dlrtn.clothing_bin_finder.config.datasource;

import com.dlrtn.clothing_bin_finder.config.datasource.exception.SshConnectionException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@RequiredArgsConstructor
@Component
public class SessionManager {

    private final SshConfig config;
    private JSch jSch;
    private Session session;

    @PostConstruct
    public Session buildSession() {
        try {
            session = createSession();

            session.setConfig(config.getSessionProperties());
            session.connect();

            log.info("SSH 세션이 연결되었습니다. Host: {}, Port: {}", config.getHost(), config.getPort());

            return session;
        } catch (JSchException e) {
            log.error("SSH 세션 설정 실패", e);
            this.close();
            throw SshConnectionException.of(e);
        }
    }

    @PreDestroy
    public void close() {
        if (this.isSessionConnected()) {
            session.disconnect();
            log.info("SSH 세션이 종료되었습니다.");
        } else {
            log.warn("SSH 세션이 이미 종료되었습니다.");
        }
    }

    private boolean isSessionConnected() {
        return session != null && session.isConnected();
    }

    private Session createSession() throws JSchException {
        if (jSch == null) {
            jSch = new JSch();
        }
        jSch.addIdentity(config.getPrivateKey(), config.getPassphrase());
        return jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
    }
}
