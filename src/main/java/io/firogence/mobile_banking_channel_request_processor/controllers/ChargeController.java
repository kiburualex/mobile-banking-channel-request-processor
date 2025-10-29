package io.firogence.mobile_banking_channel_request_processor.controllers;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.charge.ChargeRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.transfer.InternalTransferRequest;
import io.firogence.mobile_banking_channel_request_processor.services.ChargeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alex Kiburu
 */
@Slf4j
@RestController
@RequestMapping("charges")
@RequiredArgsConstructor
public class ChargeController {
    private final ChargeService chargeService;

    @PostMapping
    public ResponseEntity<GenericResponse> create(@Valid @RequestBody ChargeRequest request){
        return ResponseEntity.ok(chargeService.fetchCharges(request));
    }
}
