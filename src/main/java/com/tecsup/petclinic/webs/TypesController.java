package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.services.TypeService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypesController {

    private final TypeService typeService;

    public TypesController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public List<TypeDTO> getAll() {
        return typeService.findAll();
    }

    @GetMapping("/{id}")
    public TypeDTO getById(@PathVariable Integer id) throws TypeNotFoundException {
        return typeService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<TypeDTO> getByName(@PathVariable String name) {
        return typeService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<TypeDTO> create(@RequestBody TypeDTO dto) {
        TypeDTO created = typeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public TypeDTO update(@PathVariable Integer id, @RequestBody TypeDTO dto) {
        dto.setId(id);
        return typeService.update(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws TypeNotFoundException {
        typeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
