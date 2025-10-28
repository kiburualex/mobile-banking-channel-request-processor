package io.firogence.mobile_banking_channel_request_processor.configurations.validators.service;

import io.firogence.mobile_banking_channel_request_processor.services.ChannelService;
import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Alex Kiburu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceAndChannelValidator implements ConstraintValidator<ValidServiceAndChannel, Object> {

    private final TransactionService transactionService;
    private final ChannelService channelService;

    private String serviceCodeFieldName;
    private String channelFieldName;
    private String validationMessage;

    @Override
    public void initialize(ValidServiceAndChannel constraintAnnotation) {
        this.serviceCodeFieldName = constraintAnnotation.serviceCodeField();
        this.channelFieldName = constraintAnnotation.channelField();
        this.validationMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String serviceCode;
        String channel;

        // Use reflection to extract the field values from the validated object (InternalTransferRequest)
        try {
            Method serviceIdMethod = value.getClass().getMethod(serviceCodeFieldName);
            Method channelMethod = value.getClass().getMethod(channelFieldName);

            serviceCode = (String) serviceIdMethod.invoke(value);
            channel = (String) channelMethod.invoke(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // In a real app, log this error
            System.err.println("Error accessing fields using reflection: " + e.getMessage());
            // Fail validation securely if fields can't be accessed
            return false;
        }

        // check if channel is active
        boolean isValid = channelService.isChannelActive(channel);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Channel [" + channel + "] is not active")
                    .addPropertyNode(channelFieldName)
                    .addConstraintViolation();
            return false;
        }

        // check if service with channel is active
        isValid = transactionService.isValidServiceByCodeAndChannel(serviceCode, channel);

        // customize error message if validation fails
        if (!isValid) {
            var message = "The selected service [ "+ serviceCode +" ] is inactive or unavailable for the current channel [ "+ channel + " ]";
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(serviceCodeFieldName)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}