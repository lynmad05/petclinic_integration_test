package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.services.TypeService;
import com.tecsup.petclinic.util.TObjectCreatorType;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class TypesControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TypeService typeService;

    // ------------------------------------------------
    //   FIND ALL
    // ------------------------------------------------
    @Test
    public void testFindAllTypes() throws Exception {

        List<TypeDTO> typeDTOs = TObjectCreatorType.getAllTypeDTOs();

        when(typeService.findAll()).thenReturn(typeDTOs);

        mockMvc.perform(get("/api/types"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", is(typeDTOs.size())))
                .andExpect(jsonPath("$[0].id", is(1)));
    }


    // ------------------------------------------------
    //   FIND BY ID OK
    // ------------------------------------------------
    @Test
    public void testFindTypeOK() throws Exception {

        TypeDTO dto = TObjectCreatorType.getTypeDTO();

        when(typeService.findById(dto.getId()))
                .thenReturn(dto);

        mockMvc.perform(get("/api/types/" + dto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId())))
                .andExpect(jsonPath("$.name", is(dto.getName())));
    }


    // ------------------------------------------------
    //   FIND BY ID NOT FOUND
    // ------------------------------------------------
    @Test
    public void testFindTypeKO() throws Exception {

        int ID_NOT_EXIST = 999;

        when(typeService.findById(ID_NOT_EXIST))
                .thenThrow(new TypeNotFoundException("Not found"));

        mockMvc.perform(get("/api/types/" + ID_NOT_EXIST))
                .andExpect(status().isNotFound());
    }


    // ------------------------------------------------
    //   FIND BY NAME
    // ------------------------------------------------
    @Test
    public void testFindTypeByName() throws Exception {

        String NAME = "cat";

        List<TypeDTO> list = List.of(
                new TypeDTO(1, "cat", "Cute", true, "small", 15, "low")
        );

        when(typeService.findByName(NAME)).thenReturn(list);

        mockMvc.perform(get("/api/types/name/" + NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(NAME)));
    }


    // ------------------------------------------------
    //   CREATE
    // ------------------------------------------------
    @Test
    public void testCreateType() throws Exception {

        TypeDTO newType = TObjectCreatorType.newTypeDTO();

        when(typeService.create(newType)).thenReturn(newType);

        mockMvc.perform(post("/api/types")
                        .content(om.writeValueAsString(newType))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(newType.getName())));
    }


    // ------------------------------------------------
    //   UPDATE
    // ------------------------------------------------
    @Test
    void testUpdateType() throws Exception {

        Integer ID = 2000;

        // ----- DTO existente -----
        TypeDTO existingDTO = TypeDTO.builder()
                .id(ID)
                .name("Bird")
                .description("Pet bird")
                .active(true)
                .sizeCategory("Small")
                .averageLifespan(5)
                .careLevel("Low")
                .build();

        // ----- DTO actualizado -----
        TypeDTO updatedDTO = TypeDTO.builder()
                .id(ID)
                .name("Parrot")
                .description("Colorful parrot")
                .active(true)
                .sizeCategory("Medium")
                .averageLifespan(20)
                .careLevel("Medium")
                .build();

        // ---- MOCKS ----

        when(typeService.findById(ID)).thenReturn(existingDTO);

        when(typeService.update(any(TypeDTO.class))).thenReturn(updatedDTO);

        // ---- EXECUTE ----
        mockMvc.perform(put("/api/types/" + ID)
                        .content(new ObjectMapper().writeValueAsString(updatedDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID)))
                .andExpect(jsonPath("$.name", is("Parrot")))
                .andExpect(jsonPath("$.description", is("Colorful parrot")))
                .andExpect(jsonPath("$.sizeCategory", is("Medium")))
                .andExpect(jsonPath("$.averageLifespan", is(20)))
                .andExpect(jsonPath("$.careLevel", is("Medium")));
    }



    // ------------------------------------------------
    //   DELETE OK
    // ------------------------------------------------
    @Test
    public void testDeleteTypeOK() throws Exception {

        TypeDTO dto = TObjectCreatorType.newTypeDTOForDelete();

        doNothing().when(typeService).delete(dto.getId());

        mockMvc.perform(delete("/api/types/" + dto.getId()))
                .andExpect(status().isOk());
    }


    // ------------------------------------------------
    //   DELETE NOT FOUND
    // ------------------------------------------------
    @Test
    public void testDeleteTypeKO() throws Exception {

        int ID = 6000;

        doThrow(new TypeNotFoundException("Not found"))
                .when(typeService).delete(ID);

        mockMvc.perform(delete("/api/types/" + ID))
                .andExpect(status().isNotFound());
    }
}
