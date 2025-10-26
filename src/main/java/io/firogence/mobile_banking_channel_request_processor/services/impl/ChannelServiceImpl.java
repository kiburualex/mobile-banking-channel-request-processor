package io.firogence.mobile_banking_channel_request_processor.services.impl;

import io.firogence.mobile_banking_channel_request_processor.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Alex Kiburu
 */
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    @Override
    public boolean isChannelActive(String channel) {
        return false;
    }

}
