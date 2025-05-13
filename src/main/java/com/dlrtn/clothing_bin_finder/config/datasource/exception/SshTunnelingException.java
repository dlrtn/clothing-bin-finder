package com.dlrtn.clothing_bin_finder.config.datasource.exception;

public class SshTunnelingException extends RuntimeException {
    public SshTunnelingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static SshTunnelingException of(Throwable cause) {
        return new SshTunnelingException("SSH 터널링 실패", cause);
    }
}
