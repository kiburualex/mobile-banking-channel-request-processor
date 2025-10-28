package io.firogence.mobile_banking_channel_request_processor.services;

import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimit;
import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimitRequest;

/**
 * @author Alex Kiburu
 */
public interface TransactionService {
    boolean isValidReference(String reference);
    boolean isValidServiceByCode(String serviceCode);
    boolean isValidServiceByCodeAndChannel(String serviceCode, String channel);
    ChannelLimit isAmountWithinLimit(ChannelLimitRequest request);
}
