package grid.bit.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GridColumnValidator {
    private final CountValidator countValidator;
    private final int maxColumnCount;

    public GridColumnValidator(@Value("${main.maxColumnCount:10}") int maxColumnCount, CountValidator countValidator) {
        this.maxColumnCount = maxColumnCount;
        this.countValidator = countValidator;
    }

    public void validate(int afterNumber) {
        countValidator.validate(afterNumber, maxColumnCount);
    }
}
