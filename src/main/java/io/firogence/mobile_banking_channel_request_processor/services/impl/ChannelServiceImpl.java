package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.entities.Channel;
import io.firogence.mobile_banking_channel_request_processor.repositories.ChannelRepository;
import io.firogence.mobile_banking_channel_request_processor.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;

    @Override
    public boolean isChannelActive(String channel) {
        Optional<Channel> channelOptional = channelRepository.findFirstByNameIgnoreCase(channel);
        if (channelOptional.isPresent()) {
            Channel currentChannel = channelOptional.get();
            return currentChannel.isActive();
        }

        return false;
    }

}
