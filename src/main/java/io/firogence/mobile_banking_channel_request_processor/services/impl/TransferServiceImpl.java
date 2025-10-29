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
import io.firogence.mobile_banking_channel_request_processor.utils.UtilService;
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
    private final UtilService utilService;
    private final Gson gson = new Gson();

    @Override
    public GenericResponse internalTransfer(InternalTransferRequest request) {
        log.info("Internal Transfer Request: {}", gson.toJson(request));
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(request.serviceCode(), request.channel());
        if(transactionServiceOptional.isEmpty())
            throw new NoSuchElementException("No service for code " + request.serviceCode());

        TransactionServiceEntity transactionServiceEntity = transactionServiceOptional.get();
        // check limits
        if(transactionServiceEntity.isApplyLimit()) {
            String limitsData = transactionServiceEntity.getLimitsData();
            log.info("service code: {}, channel: {}, limits data: {}", request.serviceCode(), request.channel(), limitsData);
            ChannelLimitRequest channelLimitRequest = ChannelLimitRequest.builder()
                    .serviceCode(request.serviceCode())
                    .channel(request.channel())
                    .amount(request.amount())
                    .customerNationalId(request.nationalId())
                    .limitsJson(limitsData == null ? "" : limitsData)
                    .build();
            ChannelLimit chargeLimit = transactionService.isAmountWithinLimit(channelLimitRequest);
            if(!chargeLimit.isHasLimit())
                throw new OperationNotPermittedException("Limits not set for service code " + request.serviceCode() + ", channel " + request.channel());

            if (!chargeLimit.isValidPerTransactionLimit())
                throw new OperationNotPermittedException("Amount [" + utilService.formatWithCommas(request.amount())+ "] exceeds set limit [" + utilService.formatWithCommas(chargeLimit.getLimitPerTransaction()) + "]");

            if (!chargeLimit.isValidDailyTransactionLimit())
                throw new OperationNotPermittedException("Your total transaction amount exceeds daily limit [" + utilService.formatWithCommas(chargeLimit.getDailyTransactionLimit()) + "]");
        }

        // perform transfer

        return GenericResponse.builder()
                .status("00")
                .message("Successful internal request")
                .transactionId("120312012")
                .build();
    }
}
