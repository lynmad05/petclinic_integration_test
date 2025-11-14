package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class TObjectCreator {

	public static Pet getPet() {
		return new Pet(1,"Leo",1,1, null);
	}

	public static Pet newPet() {
		return new Pet(0,"Punky",1,1, null);
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static Pet newPetForUpdate() {
		return new Pet(0,"Bear",1,1,null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0,"Bird",1,1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}
	public static List<PetDTO> getAllPetTOs() {
		List<PetDTO> petTOs  = new ArrayList<PetDTO>();
		petTOs.add(new PetDTO(1,"Leo",1,1, "2000-09-07"));
		petTOs.add(new PetDTO(2,"Basil",6,2, "2002-08-06"));
		petTOs.add(new PetDTO(3,"Rosy",2,3, "2001-04-17"));
		petTOs.add(new PetDTO(4,"Jewel",2,3, "2000-03-07"));
		petTOs.add(new PetDTO(5,"Iggy",3,4, "2000-11-30"));
		return petTOs;
	}


	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(1,"Leo",1,1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(9,"Lucky",5,7, null));
		pets.add(new Pet(11,"Freddy",5,9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(12,"Lucky",2,10, null));
		pets.add(new Pet(13,"Sly",1,10, null));
		return pets;
	}


	public static PetDTO getPetTO() {
		return new PetDTO(1,"Leo",1,1, "2000-09-07");
	}

	public static PetDTO newPetTO() {
		return new PetDTO(-1,"Beethoven",1,1, "2020-05-20");
	}

	public static PetDTO newPetTOForDelete() {
		return new PetDTO(10000,"Beethoven3",1,1, "2020-05-20");
	}
}
