package com.novacroft.nemo.common.exception;

/**
 * Batch job errors.
 */
public class BatchJobException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     */
    public BatchJobException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     */
    public BatchJobException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     */
    public BatchJobException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of <code>(cause==null ? null : cause.toString())</code> (which typically
     * contains the class and detail message of <code>cause</code>).
     */
    public BatchJobException(Throwable cause) {
        super(cause);
    }
}
