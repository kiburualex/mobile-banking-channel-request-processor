package io.firogence.mobile_banking_channel_request_processor.services;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.charge.ChargeRequest;

/**
 * @author Alex Kiburu
 */
public interface ChargeService {
    GenericResponse fetchCharges(ChargeRequest request);
}
