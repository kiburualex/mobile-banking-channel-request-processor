package io.firogence.mobile_banking_channel_request_processor.services.impl;

import com.google.gson.Gson;
import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.transfer.InternalTransferRequest;
import io.firogence.mobile_banking_channel_request_processor.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final Gson gson = new Gson();

    @Override
    public GenericResponse internalTransfer(InternalTransferRequest request) {
        log.info("Internal Transfer Request: {}", gson.toJson(request));
        return null;
    }
}
