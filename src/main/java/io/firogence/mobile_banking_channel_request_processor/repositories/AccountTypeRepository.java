package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author Alex Kiburu
 */
public interface AccountTypeRepository extends JpaRepository<AccountType, Long>, JpaSpecificationExecutor<AccountType> {
    Optional<AccountType> findFirstByNameIgnoreCase(String name);

    Optional<AccountType> findFirstByNameIgnoreCaseOrAccountCodeIgnoreCase(String name, String accountCode);
}
