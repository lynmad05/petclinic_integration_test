package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TypeMapper {

    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    // Entity -> DTO
    TypeDTO mapToDto(Type type);

    // DTO -> Entity
    Type mapToEntity(TypeDTO typeDTO);

    // List<Entity> -> List<DTO>
    List<TypeDTO> mapToDtoList(List<Type> types);

    // List<DTO> -> List<Entity>
    List<Type> mapToEntityList(List<TypeDTO> typeDTOs);
}
