package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.Channel;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceEntity;
import io.firogence.mobile_banking_channel_request_processor.entities.TransactionServiceChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Kiburu
 */

public interface TransactionServiceChannelRepository extends JpaRepository<TransactionServiceChannel, Long> {
    Set<TransactionServiceChannel> findByTransactionService(TransactionServiceEntity service);
    List<TransactionServiceChannel> findAllByChannel(Channel channel);
}
