package io.firogence.mobile_banking_channel_request_processor.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.charge.ChargeRequest;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.exceptions.OperationNotPermittedException;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionServiceRepository;
import io.firogence.mobile_banking_channel_request_processor.services.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class ChargeImpl implements ChargeService {
    private final TransactionServiceRepository transactionServiceRepository;
    private final Gson gson = new Gson();


    @Override
    public GenericResponse fetchCharges(ChargeRequest request) {
        // fetch charge by service
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(request.serviceCode(), request.channel());
        if(transactionServiceOptional.isEmpty())
            throw new NoSuchElementException("Service not found");

        TransactionServiceEntity transactionService = transactionServiceOptional.get();
        if(transactionService.getChargesData() == null || transactionService.getChargesData().isEmpty())
            throw new NoSuchElementException("No charge set for service : " + transactionService.getName());

        var rangeData = transactionService.getChargesData();
        JsonArray rangeDataArray = gson.fromJson(rangeData, JsonArray.class);

        // todo:: apply script engine or another formula
        return GenericResponse.builder()
                .status("00")
                .message("success")
                .data(rangeDataArray)
                .build();
    }
}
