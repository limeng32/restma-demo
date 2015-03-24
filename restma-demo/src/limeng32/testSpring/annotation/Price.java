package limeng32.testSpring.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Max(10000)
@Min(8000)
@Constraint(validatedBy = {})
@Documented
@Target( { ANNOTATION_TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface Price {
	String message() default "´íÎóµÄ¼Û¸ñ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
