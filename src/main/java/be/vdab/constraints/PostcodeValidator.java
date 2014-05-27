package be.vdab.constraints;

import javax.validation.*;

public class PostcodeValidator implements ConstraintValidator<Postcode, Integer> {
	private final static int MIN_POSTCODE = 1000;
	private final static int MAX_POSTCODE = 9999;

	@Override
	public void initialize(Postcode postcode) {}

	@Override
	public boolean isValid(Integer postcode, ConstraintValidatorContext context) {
		if (postcode == null) {
			return true;
		}
		return postcode >= MIN_POSTCODE && postcode <= MAX_POSTCODE;
	}
}
