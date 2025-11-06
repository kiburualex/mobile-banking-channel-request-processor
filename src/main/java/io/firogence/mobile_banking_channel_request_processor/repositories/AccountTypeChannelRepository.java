package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.AccountType;
import io.firogence.mobile_banking_channel_request_processor.entities.AccountTypeChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * @author Alex Kiburu
 */

public interface AccountTypeChannelRepository extends JpaRepository<AccountTypeChannel, Long> {
    Set<AccountTypeChannel> findByAccountType(AccountType accountType);
}
