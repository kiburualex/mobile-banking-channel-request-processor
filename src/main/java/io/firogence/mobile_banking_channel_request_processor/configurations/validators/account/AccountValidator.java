package io.firogence.mobile_banking_channel_request_processor.configurations.validators.account;

import io.firogence.mobile_banking_channel_request_processor.services.AccountService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author Alex Kiburu
 */
@RequiredArgsConstructor
public class AccountValidator implements ConstraintValidator<ValidAccount, String>{
    private final AccountService accountService;

    @Override
    public boolean isValid(String accountNumber, ConstraintValidatorContext context) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Account is mandatory").addConstraintViolation();
            return false;
        }

        // Check if the account is valid and active
        boolean isAccountActive = accountService.isAccountActive(accountNumber);
        if (!isAccountActive) {
            context.buildConstraintViolationWithTemplate("Account is not active").addConstraintViolation();
            return false;
        }

        // todo:: check if account is blocked or not allowed to transact

        return true;
    }

}
