package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import org.junit.jupiter.api.Test;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    /**
     *
     */
    @Test
    public void testFindAllTypes() {

        List<Type> types = this.typeService.findAll();

        log.info("TOTAL TYPES FOUND: " + types.size());

        assertTrue(types.size() > 0);
    }

    /**
     *
     */
    @Test
    public void testCreateType() {

        String TYPE_NAME = "hamster";

        Type type = Type.builder()
                .name(TYPE_NAME)
                .build();

        Type newType = this.typeService.create(type);

        log.info("TYPE CREATED: " + newType);

        assertNotNull(newType.getId());
        assertEquals(TYPE_NAME, newType.getName());
    }

    /**
     *
     */
    @Test
    public void testUpdateType() {

        String TYPE_NAME = "rabbit";
        String UP_TYPE_NAME = "bunny";

        Type type = Type.builder()
                .name(TYPE_NAME)
                .build();

        // ------------ Create ---------------
        log.info(">" + type);
        Type typeCreated = this.typeService.create(type);
        log.info(">>" + typeCreated);

        // ------------ Update ---------------
        typeCreated.setName(UP_TYPE_NAME);

        Type typeUpdated = this.typeService.update(typeCreated);
        log.info(">>>>" + typeUpdated);

        // VALIDACIÓN
        assertEquals(UP_TYPE_NAME, typeUpdated.getName());
    }

    /**
     *
     */
    @Test
    public void testDeleteType() {

        String TYPE_NAME = "parrot";

        // ------------ Create ---------------
        Type type = Type.builder()
                .name(TYPE_NAME)
                .build();

        Type newType = this.typeService.create(type);
        log.info("" + newType);

        // ------------ Delete ---------------
        try {
            this.typeService.delete(newType.getId());
        } catch (TypeNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------
        try {
            this.typeService.findById(newType.getId());
            assertTrue(false);
        } catch (TypeNotFoundException e) {
            assertTrue(true);
        }
    }

}