package com.dlrtn.clothing_bin_finder.config.datasource.exception;

public class SshConnectionException extends RuntimeException {
    public SshConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static SshConnectionException of(Throwable cause) {
        return new SshConnectionException("SSH 세션 설정 실패", cause);
    }
}
