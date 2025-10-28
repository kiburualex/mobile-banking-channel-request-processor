package io.firogence.mobile_banking_channel_request_processor.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimit;
import io.firogence.mobile_banking_channel_request_processor.dtos.transaction.ChannelLimitRequest;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionRepository;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionServiceRepository;
import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TransactionServiceRepository transactionServiceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public boolean isValidReference(String reference) {
        // todo check if reference already exists
        return true;
    }

    @Override
    public boolean isValidServiceByCode(String serviceCode) {
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findFirstByCodeIgnoreCase(serviceCode);
        if(transactionServiceOptional.isPresent()){
            TransactionServiceEntity transactionServiceEntity = transactionServiceOptional.get();
            return transactionServiceEntity.isActive();
        }

        return false;
    }

    @Override
    public boolean isValidServiceByCodeAndChannel(String serviceCode, String channel) {
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(serviceCode, channel);
        if(transactionServiceOptional.isPresent()){
            TransactionServiceEntity transactionServiceEntity = transactionServiceOptional.get();
            return transactionServiceEntity.isActive();
        }

        return false;
    }

    @Override
    public ChannelLimit isAmountWithinLimit(ChannelLimitRequest request) {
        ChannelLimit limitData = new ChannelLimit();
        limitData.setChannel(request.getChannel());
        limitData.setHasLimit(request.getLimitsJson() != null);

        try {
            // 1. Parse the JSON String into a List of ChannelLimit objects
            List<ChannelLimit> channelLimits = objectMapper.readValue(request.getLimitsJson(), new TypeReference<>() {});

            // 2. Find the limits for the specified channel
            Optional<ChannelLimit> limitDataOptional = channelLimits.stream()
                    .filter(limit -> request.getChannel().equalsIgnoreCase(limit.getChannel()))
                    .findFirst();

            // 3. Compare the amount against the limitPerTransaction
            if (limitDataOptional.isPresent()) {
                limitData = limitDataOptional.get();
                BigDecimal limitPerTransaction = limitData.getLimitPerTransaction();
                BigDecimal dailyTransactionLimit = limitData.getDailyTransactionLimit();
                limitData.setValidPerTransactionLimit(request.getAmount().compareTo(limitPerTransaction) <= 0);
                Optional<BigDecimal> sumOfTodaysTransactions = transactionRepository.findTotalAmountByServiceCodeAndNationalIdToday(
                        request.getServiceCode(),
                        request.getCustomerNationalId(),
                        LocalDateTime.now());
                BigDecimal sumAmount = new BigDecimal(0);
                if(sumOfTodaysTransactions.isPresent())
                    sumAmount = sumOfTodaysTransactions.get();
                limitData.setValidDailyTransactionLimit(sumAmount.compareTo(dailyTransactionLimit) <= 0);
            } else {
                // Handle case where the channel is not found in the limits list
                log.info("Channel {} not found in limits configuration.", request.getChannel());
            }

        } catch (Exception e) {
            log.error(String.format("Error checking limits for channel %s - service: %s, ", request.getChannel(), request.getServiceCode()), e);
        }

        return limitData;
    }


}
