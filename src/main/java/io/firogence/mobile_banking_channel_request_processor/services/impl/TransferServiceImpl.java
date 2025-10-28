package io.firogence.mobile_banking_channel_request_processor.services.impl;

import com.google.gson.Gson;
import io.firogence.mobile_banking_channel_request_processor.dtos.GenericResponse;
import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimit;
import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimitRequest;
import io.firogence.mobile_banking_channel_request_processor.dtos.transfer.InternalTransferRequest;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.exceptions.OperationNotPermittedException;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionServiceRepository;
import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import io.firogence.mobile_banking_channel_request_processor.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransactionService transactionService;
    private final TransactionServiceRepository transactionServiceRepository;
    private final Gson gson = new Gson();

    @Override
    public GenericResponse internalTransfer(InternalTransferRequest request) {
        log.info("Internal Transfer Request: {}", gson.toJson(request));
        // check transaction limit
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(request.serviceCode(), request.channel());
        if(transactionServiceOptional.isEmpty())
            throw new NoSuchElementException("No service for code " + request.serviceCode());

        TransactionServiceEntity transactionServiceEntity = transactionServiceOptional.get();
        String limitsData = transactionServiceEntity.getLimitsData();
        log.info("service code: {}, channel: {}, limits data: {}", request.serviceCode(), request.channel(), limitsData);

        ChannelLimitRequest channelLimitRequest = ChannelLimitRequest.builder()
                .serviceCode(request.serviceCode())
                .channel(request.channel())
                .amount(request.amount())
                .customerNationalId(request.nationalId())
                .limitsJson(limitsData)
                .build();
        ChannelLimit chargeLimit = transactionService.isAmountWithinLimit(channelLimitRequest);
        if(chargeLimit.isHasLimit()) {
            if (!chargeLimit.isValidPerTransactionLimit())
                throw new OperationNotPermittedException("Amount [" + request.amount().toString() + "] exceeds set limit [" + chargeLimit.getLimitPerTransaction().toString() + "]");

            if (!chargeLimit.isValidDailyTransactionLimit())
                throw new OperationNotPermittedException("Your total transaction amount exceeds daily limit [" + chargeLimit.getDailyTransactionLimit().toString() + "]");
        }

        return GenericResponse.builder()
                .status("00")
                .message("Successful internal request")
                .transactionId("120312012")
                .build();
    }
}
