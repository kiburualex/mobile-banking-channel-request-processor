package io.firogence.mobile_banking_channel_request_processor.exceptions;

/**
 * @author Alex Kiburu
 */
public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException(String message) {
        super(message);
    }
}