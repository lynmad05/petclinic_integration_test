package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.mapper.TypeMapper;
import com.tecsup.petclinic.repositories.TypeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    /**
     * CREATE
     */
    @Override
    public TypeDTO create(TypeDTO dto) {

        Type newType = typeRepository.save(typeMapper.mapToEntity(dto));

        return typeMapper.mapToDto(newType);
    }

    /**
     * UPDATE
     */
    @Override
    public TypeDTO update(TypeDTO dto) {

        Type updatedType = typeRepository.save(typeMapper.mapToEntity(dto));

        return typeMapper.mapToDto(updatedType);
    }

    /**
     * DELETE
     */
    @Override
    public void delete(Integer id) throws TypeNotFoundException {

        TypeDTO typeDTO = findById(id);

        typeRepository.delete(typeMapper.mapToEntity(typeDTO));
    }

    /**
     * FIND BY ID
     */
    @Override
    public TypeDTO findById(Integer id) throws TypeNotFoundException {

        Optional<Type> opt = typeRepository.findById(id);

        if (!opt.isPresent()) {
            throw new TypeNotFoundException("Record not found...!");
        }

        return typeMapper.mapToDto(opt.get());
    }

    /**
     * FIND BY NAME
     */
    @Override
    public List<TypeDTO> findByName(String name) {

        List<Type> types = typeRepository.findByName(name);

        types.forEach(t -> log.info("" + t));

        return types.stream()
                .map(typeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * FIND ALL
     */
    @Override
    public List<TypeDTO> findAll() {

        List<Type> types = typeRepository.findAll();

        return types.stream()
                .map(typeMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
