package io.firogence.mobile_banking_channel_request_processor.services;

/**
 * @author Alex Kiburu
 */
public interface TransactionService {
    boolean isValidReference(String reference);
    boolean isValidServiceByCode(String serviceCode);
    boolean isValidServiceByCodeAndChannel(String serviceCode, String channel);
}
