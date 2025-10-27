package io.firogence.mobile_banking_channel_request_processor.configurations.validators.service;

import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author Alex Kiburu
 */
@RequiredArgsConstructor
public class ServiceValidator implements ConstraintValidator<ValidService, String>{
    private final TransactionService transactionService;

    @Override
    public boolean isValid(String serviceCode, ConstraintValidatorContext context) {
        if (serviceCode == null || serviceCode.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Service Code is mandatory").addConstraintViolation();
            return false;
        }

        // Check if the account is valid and active
        boolean isAccountActive = transactionService.isValidServiceByCode(serviceCode);
        if (!isAccountActive) {
            context.buildConstraintViolationWithTemplate("Service Code is not active").addConstraintViolation();
            return false;
        }

        return true;
    }

}
