package grid.bit.controller;

import grid.bit.dto.ErrorResponse;
import grid.bit.dto.CellDto;
import grid.bit.dto.UpsertCellDto;
import grid.bit.service.GridCellService;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid/cell")
@RestController
public class GridCellController {
    private static final Logger log = LoggerFactory.getLogger(GridCellController.class);
    private final GridCellService gridCellService;

    public GridCellController(GridCellService gridCellService) {
        this.gridCellService = gridCellService;
    }

    @PostMapping("/{columnId}/{rowId}")
    public ResponseEntity upsertCell(@PathVariable Long columnId, @PathVariable Long rowId,
                                       @RequestBody UpsertCellDto upsertCellDto) {
        try {
            CellDto cellDto = gridCellService.upsertCell(columnId, rowId, upsertCellDto);
            return new ResponseEntity<>(cellDto, HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }
}