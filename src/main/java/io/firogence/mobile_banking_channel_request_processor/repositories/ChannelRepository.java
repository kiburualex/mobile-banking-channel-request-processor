package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.Set;

/**
 * @author Alex Kiburu
 */
public interface ChannelRepository extends JpaRepository<Channel, Long>, JpaSpecificationExecutor<Channel> {
    Optional<Channel> findFirstByNameIgnoreCase(String name);
    Set<Channel> findAllByIdIn(Set<Long> id);
}
