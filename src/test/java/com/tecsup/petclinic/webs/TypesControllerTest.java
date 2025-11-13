package com.tecsup.petclinic.webs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TypesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 1️⃣ Listar todos los types
     */
    @Test
    public void testFindAllTypes() throws Exception {

        mockMvc.perform(get("/api/types"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * 2️⃣ Crear un nuevo type
     */
    @Test
    public void testCreateType() throws Exception {

        String json = """
            {
                "name": "hamster"
            }
            """;

        mockMvc.perform(post("/api/types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("hamster"));
    }

    /**
     * 3️⃣ Buscar type por ID
     */
    @Test
    public void testFindTypeById() throws Exception {

        int id = 1; // Asegúrate que exista en la BD

        mockMvc.perform(get("/api/types/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    /**
     * 4️⃣ Buscar types por nombre
     */
    @Test
    public void testFindTypeByName() throws Exception {

        String name = "dog"; // Asegúrate que exista

        mockMvc.perform(get("/api/types/name/{name}", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name));
    }

    /**
     * 5️⃣ Actualizar un type existente
     */
    @Test
    public void testUpdateType() throws Exception {

        int id = 1; // ID existente
        String json = """
            {
                "id": 1,
                "name": "bunny"
            }
            """;

        mockMvc.perform(put("/api/types/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bunny"));
    }


}
