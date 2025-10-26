package io.firogence.mobile_banking_channel_request_processor.services;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.transfer.InternalTransferRequest;

/**
 * @author Alex Kiburu
 */
public interface TransferService {
    GenericResponse internalTransfer(InternalTransferRequest request);
}
