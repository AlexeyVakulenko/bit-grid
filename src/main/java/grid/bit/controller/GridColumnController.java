package grid.bit.controller;

import grid.bit.dto.ErrorResponse;
import grid.bit.dto.ShortColumnDto;
import grid.bit.service.GridColumnService;
import jakarta.validation.ConstraintViolationException;
import jdk.jfr.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("grid/column")
@RestController
public class GridColumnController {
    private static final Logger log = LoggerFactory.getLogger(GridColumnController.class);
    private final GridColumnService gridColumnService;

    public GridColumnController(GridColumnService gridColumnService) {
        this.gridColumnService = gridColumnService;
    }

    // ToDo: implement insert, delete and getCommonPrefix

    @PostMapping
    public ResponseEntity insertColumn(@RequestParam("afterColumnId") Long afterColumnId) {
        try {
            ShortColumnDto shortColumnDto = gridColumnService.insertColumn(afterColumnId);
            return new ResponseEntity<>(shortColumnDto, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteColumn(@PathVariable("id") Long id) {
        try {
            gridColumnService.deleteColumn(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }
}