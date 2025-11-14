package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.TypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TypesControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testFindAllTypes() throws Exception {

        mockMvc.perform(get("/api/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$").isArray());
    }


    @Test
    public void testFindTypeByIdOK() throws Exception {

        mockMvc.perform(get("/api/types/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(1)));
    }


    @Test
    public void testFindTypeByIdKO() throws Exception {
        mockMvc.perform(get("/api/types/99999"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testFindTypeByName() throws Exception {

        String FIND_NAME = "dog";

        mockMvc.perform(get("/api/types/name/" + FIND_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(FIND_NAME)));
    }


    @Test
    public void testCreateType() throws Exception {

        TypeDTO newType = TypeDTO.builder()
                .name("hamster")
                .description("small pet")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(3)
                .careLevel("medium")
                .build();

        mockMvc.perform(post("/api/types")
                        .content(om.writeValueAsString(newType))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is("hamster")));
    }

    @Test
    public void testUpdateType() throws Exception {

        // Crear un type para actualizarlo
        TypeDTO dto = TypeDTO.builder()
                .name("rabbit")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(5)
                .careLevel("low")
                .build();

        ResultActions mvcActions = mockMvc.perform(post("/api/types")
                        .content(om.writeValueAsString(dto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // Update
        TypeDTO updateDTO = TypeDTO.builder()
                .id(id)
                .name("bunny")
                .active(true)
                .sizeCategory("medium")
                .averageLifespan(6)
                .careLevel("medium")
                .build();

        mockMvc.perform(put("/api/types/" + id)
                        .content(om.writeValueAsString(updateDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("bunny")));
    }


    @Test
    public void testDeleteType() throws Exception {

        // Crear uno primero
        TypeDTO dto = TypeDTO.builder()
                .name("toDelete")
                .active(true)
                .build();

        ResultActions action = mockMvc.perform(post("/api/types")
                        .content(om.writeValueAsString(dto))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        String response = action.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // Eliminar
        mockMvc.perform(delete("/api/types/" + id))
                .andExpect(status().isOk());

        // Validar not found
        mockMvc.perform(get("/api/types/" + id))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteTypeKO() throws Exception {
        mockMvc.perform(delete("/api/types/99999"))
                .andExpect(status().isNotFound());
    }
}
