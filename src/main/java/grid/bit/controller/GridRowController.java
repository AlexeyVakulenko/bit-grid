package grid.bit.controller;

import grid.bit.dto.ErrorResponse;
import grid.bit.dto.ShortColumnDto;
import grid.bit.dto.ShortRowDto;
import grid.bit.service.GridColumnService;
import grid.bit.service.GridRowService;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid/row")
@RestController
public class GridRowController {
    private static final Logger log = LoggerFactory.getLogger(GridRowController.class);
    private final GridRowService gridRowService;

    public GridRowController(GridRowService gridRowService) {
        this.gridRowService = gridRowService;
    }

    // ToDo: implement insert, delete and getCommonPrefix

    @PostMapping
    public ResponseEntity insertRow(@RequestParam("afterRowId") Long afterRowId) {
        try {
            ShortRowDto shortRowDto = gridRowService.insertRow(afterRowId);
            return new ResponseEntity<>(shortRowDto, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRow(@PathVariable("id") Long id) {
        try {
            gridRowService.deleteRow(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }
}
