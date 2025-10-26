package io.firogence.mobile_banking_channel_request_processor.configurations.validators.account;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex Kiburu
 */
@Constraint(validatedBy = AccountValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccount {
    String message() default "Account is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
