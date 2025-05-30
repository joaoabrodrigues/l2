package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.BoxDTO;
import br.com.store.packing.domain.entity.Box;
import br.com.store.packing.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boxes")
public class BoxController {

    @Autowired
    private BoxService boxService;

    @PostMapping
    public ResponseEntity<BoxDTO> createBox(@RequestBody BoxDTO boxDTO) {
        BoxDTO savedBox = boxService.saveBox(boxDTO);
        return ResponseEntity.status(201).body(savedBox);
    }

    @GetMapping
    public ResponseEntity<Box> getBox(@RequestParam Long id) {
        Box box = boxService.getBox(id);
        return ResponseEntity.ok(box);
    }
}
