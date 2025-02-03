package grid.bit.validator;

import grid.bit.dto.UpsertCellDto;
import grid.bit.exception.ValidationException;
import grid.bit.model.Grid;
import grid.bit.model.GridColumn;
import grid.bit.repository.GridColumnRepository;
import grid.bit.repository.GridRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GridCellValidator {
    private final GridColumnRepository gridColumnRepository;
    private final GridRepository gridRepository;

    public void validate(Long columnId, UpsertCellDto upsertCellDto) {
        GridColumn gridColumn = gridColumnRepository.getReferenceById(columnId);
        Grid grid = gridRepository.getReferenceById(gridColumn.getGrid().getId());
        int cellSize = grid.getCellSize();

        String value = upsertCellDto.getValue();
        if (value == null || (!value.isEmpty() && value.length() != cellSize)) {
            throw new ValidationException(String.format("Unable to add cell, the value %s is incorrect ", value));
        }
    }
}
