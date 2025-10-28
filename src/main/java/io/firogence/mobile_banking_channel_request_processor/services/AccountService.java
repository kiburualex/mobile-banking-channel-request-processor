package io.firogence.mobile_banking_channel_request_processor.services;

/**
 * @author Alex Kiburu
 */
public interface AccountService {

    boolean isAccountActive(String accountNumber);

    boolean isValidAccountForCustomer(String accountNumber, String nationalId);
}
