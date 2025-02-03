package grid.bit.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CellValueConstraintValidator implements ConstraintValidator<CellValueConstraint, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        if (value.isEmpty()) {
            return true;
        }
        if (value.length() > 100_000) {
            return false;
        }
        for (char c : value.toCharArray()) {
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }
}
