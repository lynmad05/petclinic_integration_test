package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
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

    public TypeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() throws Exception {

        Type entity = new Type(1, "dog");
        TypeDTO dto = new TypeDTO(1, "dog");

        when(typeRepository.findById(1)).thenReturn(Optional.of(entity));
        when(typeMapper.mapToDto(entity)).thenReturn(dto);

        TypeDTO result = typeService.findById(1);

        assertNotNull(result);
        assertEquals("dog", result.getName());
    }


    @Test
    void testFindByIdNotFound() {

        when(typeRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TypeNotFoundException.class, () -> typeService.findById(99));
    }


    @Test
    void testFindAll() {

        Type t1 = new Type(1, "dog");
        Type t2 = new Type(2, "cat");

        List<Type> entities = List.of(t1, t2);

        TypeDTO d1 = new TypeDTO(1, "dog");
        TypeDTO d2 = new TypeDTO(2, "cat");

        when(typeRepository.findAll()).thenReturn(entities);
        when(typeMapper.mapToDtoList(entities)).thenReturn(List.of(d1, d2));

        List<TypeDTO> result = typeService.findAll();

        assertEquals(2, result.size());
    }


    @Test
    void testCreate() {

        TypeDTO input = new TypeDTO(null, "hamster");
        Type entity = new Type(null, "hamster");
        Type saved = new Type(10, "hamster");
        TypeDTO output = new TypeDTO(10, "hamster");

        when(typeMapper.mapToEntity(input)).thenReturn(entity);
        when(typeRepository.save(entity)).thenReturn(saved);
        when(typeMapper.mapToDto(saved)).thenReturn(output);

        TypeDTO result = typeService.create(input);

        assertNotNull(result.getId());
        assertEquals("hamster", result.getName());
    }


    @Test
    void testUpdate() {

        TypeDTO input = new TypeDTO(5, "parrot");
        Type entity = new Type(5, "parrot");

        when(typeMapper.mapToEntity(input)).thenReturn(entity);
        when(typeRepository.save(entity)).thenReturn(entity);
        when(typeMapper.mapToDto(entity)).thenReturn(input);

        TypeDTO result = typeService.update(input);

        assertEquals("parrot", result.getName());
    }


    @Test
    void testDelete() throws Exception {

        Type entity = new Type(3, "snake");

        when(typeRepository.findById(3)).thenReturn(Optional.of(entity));

        typeService.delete(3);

        verify(typeRepository, times(1)).delete(entity);
    }
}
