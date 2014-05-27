package be.vdab.constraints;

import java.lang.annotation.*;
import javax.validation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostcodeValidator.class)
public @interface Postcode {
	String message() default "{be.vdab.constraints.Postcode}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
}
