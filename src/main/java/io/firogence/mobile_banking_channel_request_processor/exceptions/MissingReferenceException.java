package io.firogence.mobile_banking_channel_request_processor.exceptions;

/**
 * @author Alex Kiburu
 */
public class MissingReferenceException extends RuntimeException {
    public MissingReferenceException(String message) {
        super(message);
    }
}
