package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * @author Alex Kiburu
 */
public interface TariffRepository extends JpaRepository<Tariff, Long>, JpaSpecificationExecutor<Tariff> {

    Optional<Tariff> findFirstByNameIgnoreCase(String name);

}
