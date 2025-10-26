package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Override
    public boolean isValidReference(String reference) {
        // todo check if reference already exists
        return false;
    }
}
