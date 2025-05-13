package com.dlrtn.clothing_bin_finder.config.datasource;

import com.dlrtn.clothing_bin_finder.config.datasource.exception.SshConnectionException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
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

    private final SessionProperties properties;
    private JSch jSch;
    private Session session;
    private boolean identityAdded = false;

    public Session buildSession() {
        try {
            session = createSession();

            session.setConfig(properties.getProperties());
            session.connect();

            log.info("SSH 세션이 연결되었습니다. Host: {}, Port: {}", properties.getHost(), properties.getPort());

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
        if (!identityAdded) {
            jSch.addIdentity(properties.getPrivateKey(), properties.getPassphrase());
            identityAdded = true;
        }
        return jSch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
    }
}
