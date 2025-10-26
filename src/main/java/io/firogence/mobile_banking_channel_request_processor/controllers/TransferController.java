package io.firogence.mobile_banking_channel_request_processor.controllers;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.transfer.InternalTransferRequest;
import io.firogence.mobile_banking_channel_request_processor.services.TransferService;
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
@RequestMapping("transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/internal")
    public ResponseEntity<GenericResponse> create(@Valid @RequestBody InternalTransferRequest request){
        return ResponseEntity.ok(transferService.internalTransfer(request));
    }
}
