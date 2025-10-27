package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.repositories.TransactionServiceRepository;
import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionServiceRepository transactionServiceRepository;

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
        // todo check if reference already exists
        return false;
    }

    @Override
    public boolean isValidServiceByCodeAndChannel(String serviceCode, String channel) {
        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findByCodeAndChannels_Name(serviceCode, channel);
//        Optional<TransactionServiceEntity> transactionServiceOptional = transactionServiceRepository.findProjectionByCodeAndChannelName(serviceCode, channel);
        if(transactionServiceOptional.isPresent()){
            TransactionServiceEntity transactionServiceEntity = transactionServiceOptional.get();
            return transactionServiceEntity.isActive();
        }
        // todo check if reference already exists
        return false;
    }
}
