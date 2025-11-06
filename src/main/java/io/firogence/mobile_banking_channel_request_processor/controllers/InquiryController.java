package io.firogence.mobile_banking_channel_request_processor.controllers;

import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.BalanceRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.FullStatementRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.inquiry.MiniStatementRequest;
import io.firogence.mobile_banking_channel_request_processor.services.InquiryService;
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
@RequestMapping("inquiries")
@RequiredArgsConstructor
public class InquiryController {
    private final InquiryService inquiryService;

    @PostMapping("/balance")
    public ResponseEntity<GenericResponse> balance(@Valid @RequestBody BalanceRequest request){
        return ResponseEntity.ok(inquiryService.balance(request));
    }

    @PostMapping("/mini-statement")
    public ResponseEntity<GenericResponse> miniStatement(@Valid @RequestBody MiniStatementRequest request){
        return ResponseEntity.ok(inquiryService.miniStatement(request));
    }

    @PostMapping("/full-statement")
    public ResponseEntity<GenericResponse> fullStatement(@Valid @RequestBody FullStatementRequest request){
        return ResponseEntity.ok(inquiryService.fullStatement(request));
    }

}
