package grid.bit.service;

import grid.bit.dto.CellDto;
import grid.bit.dto.UpsertCellDto;
import grid.bit.model.CompositeKey;
import grid.bit.model.GridCell;
import grid.bit.repository.GridCellRepository;
import grid.bit.validator.GridCellValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GridCellService {
    private final GridCellRepository gridCellRepository;
    private final GridCellValidator gridCellValidator;

    @Transactional
    public CellDto upsertCell(Long columnId, Long rowId, UpsertCellDto upsertCellDto) {
        gridCellValidator.validate(columnId, upsertCellDto);

        GridCell gridCell = gridCellRepository.findById(new CompositeKey(columnId, rowId))
                .map(cell -> {
                    cell.setValue(upsertCellDto.getValue());
                    return cell;
                })
                .orElse(new GridCell(columnId, rowId, upsertCellDto.getValue()));
        gridCellRepository.saveAndFlush(gridCell);

        return new CellDto(columnId, rowId, upsertCellDto.getValue());
    }
}
