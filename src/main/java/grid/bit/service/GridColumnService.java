package grid.bit.service;

import grid.bit.dto.CommonPrefixDto;
import grid.bit.dto.ShortColumnDto;
import grid.bit.model.GridCell;
import grid.bit.model.GridColumn;
import grid.bit.repository.GridColumnRepository;
import grid.bit.validator.GridColumnValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GridColumnService {
    private final GridColumnRepository gridColumnRepository;
    private final GridColumnValidator gridColumnValidator;

    @Transactional
    public ShortColumnDto insertColumn(Long afterColumnId) {
        //получить колонку по afterColumnId
        GridColumn gridColumn = gridColumnRepository.getReferenceById(afterColumnId);
        Long gridId = gridColumn.getGrid().getId();
        int afterNumber = gridColumn.getNumber();

        gridColumnValidator.validate(afterNumber);

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

    public CommonPrefixDto getCommonPrefix(Long columnId) {
        GridColumn gridColumn = gridColumnRepository.getReferenceById(columnId);
        List<GridCell> cells = gridColumn.getCells();

        List<String> bits = cells.stream().map(GridCell::getValue).toList();
        String maxPrefix = getMaxPrefix(bits);
        return new CommonPrefixDto(maxPrefix);
    }

    private String getMaxPrefix(List<String> bits) {
        if (bits.isEmpty()) {
            return "";
        }
        String prefix = bits.getFirst();
        for (int i = 1; i < bits.size(); i++)
            while (!isPrefix(bits.get(i), prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        return prefix;
    }

    private boolean isPrefix(String bit, String prefix) {
        return bit.indexOf(prefix) == 0;
    }
}