package io.firogence.mobile_banking_channel_request_processor.exceptions;

/**
 * @author Alex Kiburu
 */
public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String msg) {
        super(msg);
    }
}
