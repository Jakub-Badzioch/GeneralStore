package com.general.store.validator;

import com.general.store.validator.impl.IsItJpgIn180x80pxImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsItJpgIn180x80pxImpl.class)
public @interface IsItJpgIn180x80px {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}