package com.sallyvnge.optionpricingapi.exception;

/**
 * Exception thrown when an unsupported or missing option type is encountered.
 */
public class UnsupportedOrMissingOptionTypeException extends RuntimeException {

    public UnsupportedOrMissingOptionTypeException(String message) {
        super(message);
    }

    public UnsupportedOrMissingOptionTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
