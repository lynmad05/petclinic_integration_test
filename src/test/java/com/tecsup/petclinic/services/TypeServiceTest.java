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

}