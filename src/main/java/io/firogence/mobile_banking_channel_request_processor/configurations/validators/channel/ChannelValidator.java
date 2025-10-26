package io.firogence.mobile_banking_channel_request_processor.configurations.validators.channel;

import io.firogence.mobile_banking_channel_request_processor.services.ChannelService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author Alex Kiburu
 */
@RequiredArgsConstructor
public class ChannelValidator implements ConstraintValidator<ValidChannel, String>{
    private final ChannelService channelService;

    @Override
    public boolean isValid(String channel, ConstraintValidatorContext context) {
        if (channel == null || channel.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Account is mandatory").addConstraintViolation();
            return false;
        }

        // Check if the account is valid and active
        boolean isAccountActive = channelService.isChannelActive(channel);
        if (!isAccountActive) {
            context.buildConstraintViolationWithTemplate("Account is not active").addConstraintViolation();
            return false;
        }

        return true;
    }

}
