package com.novacroft.nemo.common.exception;

/**
 * Application service layer errors.
 */
public class ApplicationServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;

    /**
     * Constructs a new exception with <code>null</code> as its detail message.
     */
    public ApplicationServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     */
    public ApplicationServiceException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new exception with the specified detail message.
     */
    public ApplicationServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     */
    public ApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message
     * of <code>(cause==null ? null : cause.toString())</code> (which typically
     * contains the class and detail message of <code>cause</code>).
     */
    public ApplicationServiceException(Throwable cause) {
        super(cause);
    }
    
    public String getErrorCode(){
        return this.errorCode;
    }
}
