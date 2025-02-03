package grid.bit.controller;

import grid.bit.annotation.ExceptionHandling;
import grid.bit.dto.ShortRowDto;
import grid.bit.service.GridRowService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GridRowController {
    private final GridRowService gridRowService;

    @ExceptionHandling
    @PostMapping
    public ResponseEntity<?> insertRow(@RequestParam("afterRowId") Long afterRowId) {
            ShortRowDto shortRowDto = gridRowService.insertRow(afterRowId);
            return new ResponseEntity<>(shortRowDto, HttpStatus.CREATED);
    }

    @ExceptionHandling
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRow(@PathVariable("id") Long id) {
            gridRowService.deleteRow(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
