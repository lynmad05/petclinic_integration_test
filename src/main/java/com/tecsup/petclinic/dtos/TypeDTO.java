package com.tecsup.petclinic.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDTO {

    private Integer id;
    private String name;
    private String description;
    private Boolean active;
    private String sizeCategory;
    private Integer averageLifespan;
    private String careLevel;
}
