package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;

import java.util.List;

public interface TypeService {

    Type findById(Integer id) throws TypeNotFoundException;

    List<Type> findByName(String name);

    Type create(Type type);

    Type update(Type type);

    void delete(Integer id) throws TypeNotFoundException;

    List<Type> findAll();
}
