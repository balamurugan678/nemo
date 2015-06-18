package com.novacroft.nemo.common.exception;

/**
 * Runtime exception to indicate that the application service layer attempted to update a domain object that was previously updated by a different
 * request.
 */
public class ApplicationServiceStaleDataException extends RuntimeException {

    private static final long serialVersionUID = 3823176937973673915L;

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     */
    public ApplicationServiceStaleDataException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     */
    public ApplicationServiceStaleDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of <code>(cause==null ? null : cause.toString())</code> (which
     * typically contains the class and detail message of <code>cause</code>).
     */
    public ApplicationServiceStaleDataException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     */
    public ApplicationServiceStaleDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
