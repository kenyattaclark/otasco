package com.box20six.otasco;

public class OtascoException extends RuntimeException {

    private static final long serialVersionUID = 799292300311172540L;

    public OtascoException(String message) {
        super(message);
    }

    public OtascoException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtascoException(Throwable cause) {
        super(cause);
    }
}
