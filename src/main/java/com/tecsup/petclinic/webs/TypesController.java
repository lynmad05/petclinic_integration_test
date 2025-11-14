package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.services.TypeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/types")
public class TypesController {

    private final TypeService typeService;
    private final TypeMapper mapper;

    public TypesController(TypeService typeService, TypeMapper mapper) {
        this.typeService = typeService;
        this.mapper = mapper;
    }

    /**
     * Get all types
     */
    @GetMapping
    public ResponseEntity<List<TypeDTO>> findAll() {

        List<TypeDTO> types = typeService.findAll();

        log.info("TypesDTO: {}", types);

        return ResponseEntity.ok(types);
    }

    /**
     * Create type
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TypeDTO> create(@RequestBody TypeDTO dto) {

        TypeDTO newType = typeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newType);
    }

    /**
     * Find type by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> findById(@PathVariable Integer id) {

        try {

            TypeDTO typeDTO = typeService.findById(id);
            return ResponseEntity.ok(typeDTO);

        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find types by name
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<TypeDTO>> findByName(@PathVariable String name) {

        List<TypeDTO> types = typeService.findByName(name);
        return ResponseEntity.ok(types);
    }

    /**
     * Update type
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> update(@PathVariable Integer id, @RequestBody TypeDTO dto) {

        try {
            // Verificar que existe
            TypeDTO typeFound = typeService.findById(id);

            // Actualizar campos
            typeFound.setName(dto.getName());
            typeFound.setDescription(dto.getDescription());
            typeFound.setActive(dto.getActive());
            typeFound.setSizeCategory(dto.getSizeCategory());
            typeFound.setAverageLifespan(dto.getAverageLifespan());
            typeFound.setCareLevel(dto.getCareLevel());

            TypeDTO updated = typeService.update(typeFound);

            return ResponseEntity.ok(updated);

        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete type
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            typeService.delete(id);
            return ResponseEntity.ok("Deleted ID: " + id);

        } catch (TypeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
