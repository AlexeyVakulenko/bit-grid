package grid.bit.controller;

import grid.bit.annotation.ExceptionHandling;
import grid.bit.dto.CellDto;
import grid.bit.dto.UpsertCellDto;
import grid.bit.service.GridCellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid/cell")
@RestController
@RequiredArgsConstructor
public class GridCellController {
    private final GridCellService gridCellService;

    @ExceptionHandling
    @PostMapping("/{columnId}/{rowId}")
    public ResponseEntity<?> upsertCell(@PathVariable Long columnId, @PathVariable Long rowId,
                                       @RequestBody UpsertCellDto upsertCellDto) {
            CellDto cellDto = gridCellService.upsertCell(columnId, rowId, upsertCellDto);
            return new ResponseEntity<>(cellDto, HttpStatus.NO_CONTENT);
    }
}