package grid.bit.controller;

import grid.bit.annotation.ExceptionHandling;
import grid.bit.dto.CommonPrefixDto;
import grid.bit.dto.ShortColumnDto;
import grid.bit.service.GridColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("grid/column")
@RestController
@RequiredArgsConstructor
public class GridColumnController {
    private final GridColumnService gridColumnService;

    @ExceptionHandling
    @PostMapping
    public ResponseEntity<?> insertColumn(@RequestParam("afterColumnId") Long afterColumnId) {
            ShortColumnDto shortColumnDto = gridColumnService.insertColumn(afterColumnId);
            return new ResponseEntity<>(shortColumnDto, HttpStatus.CREATED);
    }

    @ExceptionHandling
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteColumn(@PathVariable("id") Long id) {
            gridColumnService.deleteColumn(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandling
    @GetMapping("/{id}/common-prefix")
    public ResponseEntity<?> getCommonPrefix(@PathVariable("id") Long id) {
            CommonPrefixDto commonPrefixDto = gridColumnService.getCommonPrefix(id);
            return new ResponseEntity<>(commonPrefixDto, HttpStatus.OK);
    }
}