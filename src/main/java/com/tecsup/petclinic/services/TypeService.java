package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;

import java.util.List;

public interface TypeService {

    TypeDTO findById(Integer id) throws TypeNotFoundException;

    List<TypeDTO> findByName(String name);

    List<TypeDTO> findAll();

    TypeDTO create(TypeDTO dto);

    TypeDTO update(TypeDTO dto);

    void delete(Integer id) throws TypeNotFoundException;
}
