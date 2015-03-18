package cn.limeng32.testSpring.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.limeng32.testSpring.validator.StatusValidator;

@Constraint(validatedBy = { StatusValidator.class })
@Documented
@Target( { ANNOTATION_TYPE, METHOD, FIELD })
@Retention(RUNTIME)
public @interface Status {
	String message() default "����ȷ��״̬ , Ӧ���� 'created', 'paid', shipped', closed'����֮һ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
