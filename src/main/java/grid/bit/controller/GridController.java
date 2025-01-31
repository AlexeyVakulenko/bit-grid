package grid.bit.controller;

import grid.bit.dto.CreateGridDto;
import grid.bit.dto.ErrorResponse;
import grid.bit.dto.UpdateGridDto;
import grid.bit.dto.ShortGridDto;
import grid.bit.service.GridService;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("grid")
@RestController
public class GridController {
    private static final Logger log = LoggerFactory.getLogger(GridController.class);
    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    // ToDo: implement getAll, create, update and delete

    @GetMapping
    public ResponseEntity<Set<ShortGridDto>> getAllGridDto() {
        return ResponseEntity.ok(gridService.findAll());
    }

    @PostMapping
    public ResponseEntity createGrid(@RequestBody CreateGridDto body) {
        try {
            ShortGridDto createGridDto = gridService.createGrid(body);
            return new ResponseEntity<>(createGridDto, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity putGridDto(@PathVariable("id") Long id, @RequestBody UpdateGridDto body) {
        try {
            gridService.updateGrid(id, body);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGrid(@PathVariable("id") Long id) {
        try {
            gridService.deleteGrid(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ConstraintViolationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }
}