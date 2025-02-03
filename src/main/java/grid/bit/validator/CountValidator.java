package grid.bit.validator;

import grid.bit.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class CountValidator {

    public void validate(int count, int maxCount) {
        if (count >= maxCount) {
            throw new ValidationException(
                    String.format("Unable to add entity, maximum number exceeded %d", maxCount));
        }
    }
}
