package io.firogence.mobile_banking_channel_request_processor.services;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.BalanceRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.FullStatementRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.MiniStatementRequest;

/**
 * @author Alex Kiburu
 */
public interface InquiryService {

    GenericResponse balance(BalanceRequest request);

    GenericResponse miniStatement(MiniStatementRequest request);

    GenericResponse fullStatement(FullStatementRequest request);
}
