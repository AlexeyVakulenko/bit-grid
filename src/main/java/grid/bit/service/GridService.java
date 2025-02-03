package grid.bit.service;

import grid.bit.dto.CreateGridDto;
import grid.bit.dto.ShortGridDto;
import grid.bit.dto.UpdateGridDto;
import grid.bit.model.Grid;
import grid.bit.model.GridCell;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridColumnRepository;
import grid.bit.repository.GridRepository;
import grid.bit.repository.GridRowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GridService {
    private final GridRepository gridRepository;
    private final GridColumnRepository gridColumnRepository;
    private final GridRowRepository gridRowRepository;
    private final GridCellRepository gridCellRepository;

    @Transactional
    public ShortGridDto createGrid(CreateGridDto createGridDto) {
        Grid grid = new Grid();
        grid.setName(createGridDto.getName());
        grid.setCellSize(createGridDto.getCellSize());
        grid = gridRepository.save(grid);

        GridColumn gridColumn = new GridColumn(1, grid);
        gridColumn = gridColumnRepository.save(gridColumn);

        GridRow gridRow = new GridRow(1, grid);
        gridRow = gridRowRepository.save(gridRow);

        GridCell gridCell = new GridCell(gridColumn.getId(), gridRow.getId(), "");
        gridCellRepository.save(gridCell);

        return new ShortGridDto(grid.getId(), grid.getName(), grid.getCellSize());
    }

    @Transactional
    public void updateGrid(Long id, UpdateGridDto updateGridDto) {
        Grid grid = gridRepository.getReferenceById(id);
        if (!ObjectUtils.nullSafeEquals(grid.getName(), updateGridDto.getName())) {
            grid.setName(updateGridDto.getName());
            gridRepository.save(grid);
        }
    }

    @Transactional
    public void deleteGrid(Long id) {
        gridRepository.deleteById(id);
    }

    public Set<ShortGridDto> findAll() {
        List<Grid> grids = gridRepository.findAll();
        return grids.stream()
                .map(grid -> new ShortGridDto(grid.getId(), grid.getName(), grid.getCellSize()))
                .collect(Collectors.toSet());
    }
}