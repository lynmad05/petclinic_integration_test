package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {
        Optional<Type> optionalType = typeRepository.findById(id);

        if (optionalType.isEmpty()) {
            throw new TypeNotFoundException("Type not found with ID: " + id);
        }

        return typeMapper.mapToDto(optionalType.get());
    }

    @Override
    public List<TypeDTO> findByName(String name) {
        return typeMapper.mapToDtoList(typeRepository.findByName(name));
    }

    @Override
    public List<TypeDTO> findAll() {
        return typeMapper.mapToDtoList(typeRepository.findAll());
    }

    @Override
    public TypeDTO create(TypeDTO dto) {
        Type entity = typeMapper.mapToEntity(dto);
        Type saved = typeRepository.save(entity);
        return typeMapper.mapToDto(saved);
    }

    @Override
    public TypeDTO update(TypeDTO dto) {
        Type entity = typeMapper.mapToEntity(dto);
        Type updated = typeRepository.save(entity);
        return typeMapper.mapToDto(updated);
    }

    @Override
    public void delete(Integer id) throws TypeNotFoundException {

        Optional<Type> optionalType = typeRepository.findById(id);

        if (optionalType.isEmpty()) {
            throw new TypeNotFoundException("Type not found with ID: " + id);
        }

        typeRepository.delete(optionalType.get());
    }
}
