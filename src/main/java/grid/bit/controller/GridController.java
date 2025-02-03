package grid.bit.controller;

import grid.bit.annotation.ExceptionHandling;
import grid.bit.dto.CreateGridDto;
import grid.bit.dto.ShortGridDto;
import grid.bit.dto.UpdateGridDto;
import grid.bit.service.GridService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GridController {
    private final GridService gridService;

    @GetMapping
    public ResponseEntity<Set<ShortGridDto>> getAllGridDto() {
        return ResponseEntity.ok(gridService.findAll());
    }

    @ExceptionHandling
    @PostMapping
    public ResponseEntity<?> createGrid(@RequestBody CreateGridDto body) {
            ShortGridDto createGridDto = gridService.createGrid(body);
            return new ResponseEntity<>(createGridDto, HttpStatus.CREATED);
    }

    @ExceptionHandling
    @PutMapping("/{id}")
    public ResponseEntity<?> putGridDto(@PathVariable("id") Long id, @RequestBody UpdateGridDto body) {
            gridService.updateGrid(id, body);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandling
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrid(@PathVariable("id") Long id) {
            gridService.deleteGrid(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}