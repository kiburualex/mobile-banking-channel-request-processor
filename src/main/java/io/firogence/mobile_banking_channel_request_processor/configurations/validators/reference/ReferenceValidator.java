package io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference;

import io.firogence.mobile_banking_channel_request_processor.services.TransactionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author Alex Kiburu
 */
@RequiredArgsConstructor
public class ReferenceValidator implements ConstraintValidator<ValidReference, String>{
    private final TransactionService transactionService;

    @Override
    public boolean isValid(String reference, ConstraintValidatorContext context) {
        if (reference == null || reference.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Reference is mandatory").addConstraintViolation();
            return false;
        }

        // Check if the account is valid and active
        boolean isAccountActive = transactionService.isValidReference(reference);
        if (!isAccountActive) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Reference exists for another transaction").addConstraintViolation();
            return false;
        }

        return true;
    }

}
