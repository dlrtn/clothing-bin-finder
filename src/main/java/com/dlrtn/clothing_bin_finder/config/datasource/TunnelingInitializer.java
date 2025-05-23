package com.dlrtn.clothing_bin_finder.config.datasource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class TunnelingInitializer {

    private final SshTunnelingManager sshTunnelingManager;

    public Integer buildTunneling(int localPort) {
        return sshTunnelingManager.setupPortForwarding(localPort);
    }

    public Integer buildTunneling() {
        return sshTunnelingManager.setupPortForwarding();
    }
}
