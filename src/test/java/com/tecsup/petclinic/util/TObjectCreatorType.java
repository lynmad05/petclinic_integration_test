package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Type;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreatorType {

    public static Type getType() {
        return new Type(1, "Dog", "Domestic dog", true, "Medium", 12, "Moderate");
    }

    public static Type newType() {
        return new Type(0, "Cat", "Domestic cat", true, "Small", 15, "Low");
    }

    public static Type newTypeCreated() {
        Type type = newType();
        type.setId(1000);
        return type;
    }

    public static Type newTypeForUpdate() {
        return new Type(0, "Bird", "Pet bird", true, "Small", 5, "Low");
    }

    public static Type newTypeCreatedForUpdate() {
        Type type = newTypeForUpdate();
        type.setId(2000);
        return type;
    }

    public static Type newTypeForDelete() {
        return new Type(0, "Rabbit", "Domestic rabbit", true, "Small", 8, "Low");
    }

    public static Type newTypeCreatedForDelete() {
        Type type = newTypeForDelete();
        type.setId(3000);
        return type;
    }

    public static TypeDTO getTypeDTO() {
        return new TypeDTO(1, "Dog", "Domestic dog", true, "Medium", 12, "Moderate");
    }

    public static TypeDTO newTypeDTO() {
        return new TypeDTO(-1, "Cat", "Domestic cat", true, "Small", 15, "Low");
    }

    public static TypeDTO newTypeDTOForDelete() {
        return new TypeDTO(3000, "Rabbit", "Domestic rabbit", true, "Small", 8, "Low");
    }
    public static List<Type> getTypesForFindByName() {
        List<Type> types = new ArrayList<>();
        types.add(new Type(1, "cat", "Domestic feline", true, "small", 15, "medium"));
        // Puedes agregar más si quieres simular más resultados
        return types;
    }


    public static List<TypeDTO> getAllTypeDTOs() {
        List<TypeDTO> types = new ArrayList<>();
        types.add(new TypeDTO(1, "Dog", "Domestic dog", true, "Medium", 12, "Moderate"));
        types.add(new TypeDTO(2, "Cat", "Domestic cat", true, "Small", 15, "Low"));
        types.add(new TypeDTO(3, "Bird", "Pet bird", true, "Small", 5, "Low"));
        types.add(new TypeDTO(4, "Rabbit", "Domestic rabbit", true, "Small", 8, "Low"));
        return types;
    }
}
