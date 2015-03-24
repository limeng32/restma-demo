package limeng32.testSpring.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import limeng32.testSpring.annotation.Status;

public class StatusValidator implements ConstraintValidator<Status, String> {

	private final String[] ALL_STATUS = { "created", "paid", "shipped",
			"closed" };

	public void initialize(Status status) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (Arrays.asList(ALL_STATUS).contains(value))
			return true;
		return false;
	}
}
