package grid.bit.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GridRowValidator {
    private final int maxRowCount;
    private final CountValidator countValidator;

    public GridRowValidator(@Value("${main.maxRowCount:100000}") int maxRowCount, CountValidator countValidator) {
        this.maxRowCount = maxRowCount;
        this.countValidator = countValidator;
    }

    public void validate(int afterNumber) {
        countValidator.validate(afterNumber, maxRowCount);
    }
}
