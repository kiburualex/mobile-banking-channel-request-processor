package io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.account_customer;

import io.firogence.mobile_banking_channel_request_processor.services.AccountService;
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
public class AccountCustomerValidator implements ConstraintValidator<ValidAccountCustomer, Object> {
    private final AccountService accountService;

    private String accountFieldName;
    private String nationalIdName;
    private String validationMessage;

    @Override
    public void initialize(ValidAccountCustomer constraintAnnotation) {
        this.accountFieldName = constraintAnnotation.accountField();
        this.nationalIdName = constraintAnnotation.nationalIdField();
        this.validationMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String account;
        String nationalId;

        // Use reflection to extract the field values from the validated object (InternalTransferRequest)
        try {
            Method accountMethod = value.getClass().getMethod(accountFieldName);
            Method nationalIdMethod = value.getClass().getMethod(nationalIdName);

            account = (String) accountMethod.invoke(value);
            nationalId = (String) nationalIdMethod.invoke(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // In a real app, log this error
            System.err.println("Error accessing fields using reflection: " + e.getMessage());
            // Fail validation securely if fields can't be accessed
            return false;
        }

        // check if account is valid for the customer
        boolean isValidAccount = accountService.isValidAccountForCustomer(account, nationalId);
        if (!isValidAccount) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid account details")
                    .addPropertyNode(accountFieldName)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}