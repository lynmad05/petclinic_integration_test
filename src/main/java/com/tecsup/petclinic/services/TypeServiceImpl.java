package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Type;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
import com.tecsup.petclinic.repositories.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public Type findById(Integer id) throws TypeNotFoundException {
        Optional<Type> type = typeRepository.findById(id);
        if (type.isEmpty()) {
            throw new TypeNotFoundException("Record not found...!");
        }
        return type.get();
    }

    @Override
    public List<Type> findByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public Type create(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type update(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public void delete(Integer id) throws TypeNotFoundException {
        Type type = findById(id);
        typeRepository.delete(type);
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }
}
