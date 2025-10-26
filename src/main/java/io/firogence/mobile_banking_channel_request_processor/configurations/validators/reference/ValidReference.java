package io.firogence.mobile_banking_channel_request_processor.configurations.validators.reference;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex Kiburu
 */
@Constraint(validatedBy = ReferenceValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReference {
    String message() default "Reference is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
