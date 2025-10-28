package io.firogence.mobile_banking_channel_request_processor.configurations.validators.service.service_channel;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Alex Kiburu
 */
@Constraint(validatedBy = ServiceAndChannelValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidServiceAndChannel {
    String serviceCodeField();
    String channelField();
    String message() default "Service is not active or is not configured for the specified channel.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
