package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TypeServiceTest {

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private TypeMapper typeMapper;

    @InjectMocks
    private TypeServiceImpl typeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -----------------------
    // FIND BY ID OK
    // -----------------------
    @Test
    void testFindById() throws Exception {

        Type entity = Type.builder()
                .id(1)
                .name("dog")
                .description("friendly")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("easy")
                .build();

        TypeDTO dto = TypeDTO.builder()
                .id(1)
                .name("dog")
                .description("friendly")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(12)
                .careLevel("easy")
                .build();

        when(typeRepository.findById(1)).thenReturn(Optional.of(entity));
        when(typeMapper.mapToDto(entity)).thenReturn(dto);

        TypeDTO result = typeService.findById(1);

        assertNotNull(result);
        assertEquals("dog", result.getName());
    }

    // -----------------------
    // FIND BY ID NOT FOUND
    // -----------------------
    @Test
    void testFindByIdNotFound() {

        when(typeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TypeNotFoundException.class,
                () -> typeService.findById(99));
    }

    // -----------------------
    // FIND ALL
    // -----------------------
    @Test
    void testFindAll() {

        Type t1 = Type.builder().id(1).name("dog").build();
        Type t2 = Type.builder().id(2).name("cat").build();

        TypeDTO d1 = TypeDTO.builder().id(1).name("dog").build();
        TypeDTO d2 = TypeDTO.builder().id(2).name("cat").build();

        when(typeRepository.findAll()).thenReturn(List.of(t1, t2));

        when(typeMapper.mapToDto(t1)).thenReturn(d1);
        when(typeMapper.mapToDto(t2)).thenReturn(d2);

        List<TypeDTO> result = typeService.findAll();

        assertEquals(2, result.size());
    }

    // -----------------------
    // CREATE
    // -----------------------
    @Test
    void testCreate() {

        TypeDTO input = TypeDTO.builder()
                .name("hamster")
                .build();

        Type entity = Type.builder()
                .name("hamster")
                .build();

        Type saved = Type.builder()
                .id(10)
                .name("hamster")
                .build();

        TypeDTO output = TypeDTO.builder()
                .id(10)
                .name("hamster")
                .build();

        when(typeMapper.mapToEntity(input)).thenReturn(entity);
        when(typeRepository.save(entity)).thenReturn(saved);
        when(typeMapper.mapToDto(saved)).thenReturn(output);

        TypeDTO result = typeService.create(input);

        assertNotNull(result.getId());
        assertEquals("hamster", result.getName());
    }

    // -----------------------
    // UPDATE
    // -----------------------
    @Test
    void testUpdate() {

        TypeDTO input = TypeDTO.builder()
                .id(5)
                .name("parrot")
                .build();

        Type entity = Type.builder()
                .id(5)
                .name("parrot")
                .build();

        when(typeMapper.mapToEntity(input)).thenReturn(entity);
        when(typeRepository.save(entity)).thenReturn(entity);
        when(typeMapper.mapToDto(entity)).thenReturn(input);

        TypeDTO result = typeService.update(input);

        assertEquals("parrot", result.getName());
    }

    // -----------------------
    // DELETE
    // -----------------------
    @Test
    void testDelete() throws Exception {

        Type entity = Type.builder()
                .id(3)
                .name("snake")
                .build();

        TypeDTO dto = TypeDTO.builder()
                .id(3)
                .name("snake")
                .build();

        when(typeRepository.findById(3)).thenReturn(Optional.of(entity));
        when(typeMapper.mapToDto(entity)).thenReturn(dto);
        when(typeMapper.mapToEntity(dto)).thenReturn(entity);

        typeService.delete(3);

        verify(typeRepository, times(1)).delete(entity);
    }
}
