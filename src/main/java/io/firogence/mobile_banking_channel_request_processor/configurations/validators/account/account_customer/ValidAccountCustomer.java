package io.firogence.mobile_banking_channel_request_processor.configurations.validators.account.account_customer;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author Alex Kiburu
 */
@Constraint(validatedBy = AccountCustomerValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidAccountCustomer {
    String accountField();
    String nationalIdField();
    String message() default "Invalid account details";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
