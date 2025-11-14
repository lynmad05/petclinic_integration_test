package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;
import com.tecsup.petclinic.util.TObjectCreatorType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class TypeServiceMockitoTest {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TypeMapper typeMapper;

    @MockitoBean
    private TypeRepository repository;

    @BeforeEach
    void setUp() {
    }

    /**
     * Test find by ID
     */
    @Test
    public void testFindTypeById() {

        Type typeExpected = TObjectCreatorType.getType();

        Mockito.when(this.repository.findById(1))
                .thenReturn(Optional.of(typeExpected));

        TypeDTO type = null;
        try {
            type = this.typeService.findById(1);
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("Expected: {}", typeExpected);
        log.info("Actual: {}", type);

        assertEquals(typeExpected.getName(), type.getName());
    }

    /**
     * Test find by name
     */
    @Test
    public void testFindTypeByName() {

        String FIND_NAME = "cat";

        List<Type> typesExpected = TObjectCreatorType.getTypesForFindByName();

        Mockito.when(this.repository.findByName(FIND_NAME))
                .thenReturn(typesExpected);

        List<TypeDTO> types = this.typeService.findByName(FIND_NAME);

        assertEquals(typesExpected.size(), types.size());
    }

    /**
     * Test create type
     */
    @Test
    public void testCreateType() {

        Type newType = TObjectCreatorType.newType();
        Type newTypeCreated = TObjectCreatorType.newTypeCreated();

        TypeDTO newTypeDTO = this.typeMapper.mapToDto(newType);
        TypeDTO expectedTypeDTO = this.typeMapper.mapToDto(newTypeCreated);

        Mockito.when(this.repository.save(newType))
                .thenReturn(newTypeCreated);

        TypeDTO createdTypeDTO = this.typeService.create(newTypeDTO);

        log.info("Type created: {}", createdTypeDTO);

        assertNotNull(createdTypeDTO.getId());
        assertEquals(expectedTypeDTO.getName(), createdTypeDTO.getName());
        assertEquals(expectedTypeDTO.getDescription(), createdTypeDTO.getDescription());
    }

    /**
     * Test update type
     */
    @Test
    public void testUpdateType() {

        Type newType = TObjectCreatorType.newTypeForUpdate();
        Type newTypeCreated = TObjectCreatorType.newTypeCreatedForUpdate();

        TypeDTO newTypeDTO = this.typeMapper.mapToDto(newType);
        TypeDTO expectedTypeDTO = this.typeMapper.mapToDto(newTypeCreated);

        // Create
        Mockito.when(this.repository.save(newType))
                .thenReturn(newTypeCreated);

        TypeDTO createdTypeDTO = this.typeService.create(newTypeDTO);

        // Prepare update
        createdTypeDTO.setName("dog2");
        createdTypeDTO.setDescription("Updated dog");

        Type updatedTypeEntity = this.typeMapper.mapToEntity(createdTypeDTO);

        Mockito.when(this.repository.save(updatedTypeEntity))
                .thenReturn(updatedTypeEntity);

        TypeDTO updatedTypeDTO = this.typeService.update(createdTypeDTO);

        log.info("Type updated: {}", updatedTypeDTO);

        assertEquals("dog2", updatedTypeDTO.getName());
        assertEquals("Updated dog", updatedTypeDTO.getDescription());
    }

    /**
     * Test delete type
     */
    @Test
    public void testDeleteType() {

        Type typeToDelete = TObjectCreatorType.newTypeForDelete();
        Type typeCreated = TObjectCreatorType.newTypeCreatedForDelete();

        TypeDTO typeDTO = this.typeMapper.mapToDto(typeToDelete);

        // Create
        Mockito.when(this.repository.save(typeToDelete))
                .thenReturn(typeCreated);

        TypeDTO createdTypeDTO = this.typeService.create(typeDTO);

        // Delete
        Mockito.doNothing().when(this.repository).delete(typeCreated);
        Mockito.when(this.repository.findById(typeCreated.getId()))
                .thenReturn(Optional.of(typeCreated));

        try {
            this.typeService.delete(createdTypeDTO.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        // Validate deletion
        Mockito.when(this.repository.findById(typeCreated.getId()))
                .thenReturn(Optional.ofNullable(null));

        try {
            this.typeService.findById(createdTypeDTO.getId());
            assertTrue(false);
        } catch (TypeNotFoundException e) {
            assertTrue(true);
        }
    }

}
