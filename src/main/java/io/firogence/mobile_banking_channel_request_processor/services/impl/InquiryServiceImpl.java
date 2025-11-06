package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.BalanceRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.FullStatementRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.MiniStatementRequest;
import io.firogence.mobile_banking_channel_request_processor.services.InquiryService;
import io.firogence.mobile_banking_channel_request_processor.utils.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryServiceImpl implements InquiryService {
    private final RestService restService;


    @Override
    public GenericResponse balance(BalanceRequest request) {
        //
        return null;
    }

    @Override
    public GenericResponse miniStatement(MiniStatementRequest request) {
        return null;
    }

    @Override
    public GenericResponse fullStatement(FullStatementRequest request) {
        return null;
    }
}
