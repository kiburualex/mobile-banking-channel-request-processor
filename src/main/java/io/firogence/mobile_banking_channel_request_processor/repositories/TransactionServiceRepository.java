package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Alex Kiburu
 */
public interface TransactionServiceRepository extends JpaRepository<TransactionServiceEntity, Long>, JpaSpecificationExecutor<TransactionServiceEntity> {

    @Transactional
    Optional<TransactionServiceEntity> findByCodeAndChannels_Name(String code, String channelName);

    @Transactional
    Optional<TransactionServiceEntity> findFirstByCodeIgnoreCase(String code);
}
