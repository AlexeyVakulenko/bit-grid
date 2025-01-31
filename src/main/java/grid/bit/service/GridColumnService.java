package grid.bit.service;

import grid.bit.dto.ShortColumnDto;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;
import grid.bit.repository.GridColumnRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GridColumnService {
    private final GridColumnRepository gridColumnRepository;

    @Transactional
    public ShortColumnDto insertColumn(Long afterColumnId) {
        //получить колонку по afterColumnId
        GridColumn gridColumn = gridColumnRepository.getReferenceById(afterColumnId);
        Long gridId = gridColumn.getGrid().getId();
        int afterNumber = gridColumn.getNumber();

        //инкрементировать номера всех колонок больше afterNumber
        List<GridColumn> columnsForUpdate = gridColumnRepository.findByGridIdAndAfterNumberOrderByDesc(gridId, afterNumber);
        columnsForUpdate = columnsForUpdate.stream()
                .peek(column -> column.setNumber(column.getNumber() + 1)).toList();
        gridColumnRepository.saveAllAndFlush(columnsForUpdate);

        //добавить новую запись
        GridColumn newGridColumn = new GridColumn();
        newGridColumn.setGrid(gridColumn.getGrid());
        newGridColumn.setCells(Collections.emptyList());
        newGridColumn.setNumber(afterNumber + 1);
        newGridColumn = gridColumnRepository.save(newGridColumn);

        return new ShortColumnDto(newGridColumn.getId());
    }

    @Transactional
    public void deleteColumn(Long columnId) {
        //получить колонку по afterColumnId
        GridColumn gridColumn = gridColumnRepository.getReferenceById(columnId);
        Long gridId = gridColumn.getGrid().getId();
        int afterNumber = gridColumn.getNumber();

        //удалить запись
        gridColumnRepository.delete(gridColumn);

        //декрементировать номера всех колонок больше afterNumber
        List<GridColumn> columnsForUpdate = gridColumnRepository.findByGridIdAndAfterNumberOrderByAsc(gridId, afterNumber);
        columnsForUpdate = columnsForUpdate.stream()
                .peek(column -> column.setNumber(column.getNumber() - 1)).toList();
        gridColumnRepository.saveAllAndFlush(columnsForUpdate);
    }
}