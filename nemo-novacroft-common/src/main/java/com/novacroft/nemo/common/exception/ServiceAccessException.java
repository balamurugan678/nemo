package com.novacroft.nemo.common.exception;

/**
 * Service access layer errors.
 */
public class ServiceAccessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     */
    public ServiceAccessException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     */
    public ServiceAccessException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     */
    public ServiceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of <code>(cause==null ? null : cause.toString())</code> (which typically
     * contains the class and detail message of <code>cause</code>).
     */
    public ServiceAccessException(Throwable cause) {
        super(cause);
    }
}
