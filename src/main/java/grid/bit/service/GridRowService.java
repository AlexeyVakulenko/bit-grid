package grid.bit.service;

import grid.bit.dto.ShortRowDto;
import grid.bit.model.GridRow;
import grid.bit.repository.GridRowRepository;
import grid.bit.validator.GridRowValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GridRowService {
    private final GridRowRepository gridRowRepository;
    private final GridRowValidator gridRowValidator;

    @Transactional
    public ShortRowDto insertRow(Long afterRowId) {
        //получить колонку по afterColumnId
        GridRow gridRow = gridRowRepository.getReferenceById(afterRowId);
        Long gridId = gridRow.getGrid().getId();
        int afterNumber = gridRow.getNumber();

        gridRowValidator.validate(afterNumber);

        //инкрементировать номера всех колонок больше afterNumber
        List<GridRow> rowsForUpdate = gridRowRepository.findByGridIdAndAfterNumberOrderByDesc(gridId, afterNumber);
        rowsForUpdate = rowsForUpdate.stream()
                .peek(row -> row.setNumber(row.getNumber() + 1)).toList();
        gridRowRepository.saveAllAndFlush(rowsForUpdate);

        //добавить новую запись
        GridRow newGridRow = new GridRow();
        newGridRow.setGrid(gridRow.getGrid());
        newGridRow.setCells(Collections.emptyList());
        newGridRow.setNumber(++afterNumber);
        newGridRow = gridRowRepository.save(newGridRow);

        return new ShortRowDto(newGridRow.getId());
    }

    @Transactional
    public void deleteRow(Long rowId) {
        //получить колонку по afterColumnId
        GridRow gridRow = gridRowRepository.getReferenceById(rowId);
        Long gridId = gridRow.getGrid().getId();
        int afterNumber = gridRow.getNumber();

        //удалить запись
        gridRowRepository.delete(gridRow);

        //декрементировать номера всех колонок больше afterNumber
        List<GridRow> rowsForUpdate = gridRowRepository.findByGridIdAndAfterNumberOrderByAsc(gridId, afterNumber);
        rowsForUpdate = rowsForUpdate.stream()
                .peek(row -> row.setNumber(row.getNumber() - 1)).toList();
        gridRowRepository.saveAllAndFlush(rowsForUpdate);
    }

    @Deprecated
    @Transactional
    public ShortRowDto insertRow0(Long afterRowId) {
        //получить колонку по afterColumnId
        GridRow gridRow = gridRowRepository.getReferenceById(afterRowId);
        Long gridId = gridRow.getGrid().getId();
        int afterNumber = gridRow.getNumber();

        //инкрементировать номера всех колонок больше afterNumber
        int count = gridRowRepository.incNumberAfter(gridId, afterNumber);

        //добавить новую запись
        GridRow newGridRow = new GridRow();
        newGridRow.setGrid(gridRow.getGrid());
        newGridRow.setCells(Collections.emptyList());
        newGridRow.setNumber(++afterNumber);
        newGridRow = gridRowRepository.save(newGridRow);

        return new ShortRowDto(newGridRow.getId());
    }
}
