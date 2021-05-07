package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pets;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetsRepository petsRepository;

    public PetDTO savePet(PetDTO petDTO) {
        Pets pet = new Pets(petDTO.getType(),petDTO.getName(),petDTO.getBirthDate(),petDTO.getNotes());
        Customer owner = new Customer(petDTO.getOwnerId());
        pet.setOwner(owner);
        Pets savedPet = this.petsRepository.save(pet);
        PetDTO petDTOResponse = getPetDTO(this.petsRepository.save(pet));
        petDTOResponse.setOwnerId(savedPet.getOwner().getId());
        return petDTOResponse;
    }

    public List<PetDTO> getAllPets() {
        List<Pets> pets = this.petsRepository.findAll();
        return pets.stream().map(pet -> {
            PetDTO petDTO = getPetDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            return petDTO;
        }).collect(Collectors.toList());
    }

    public List<PetDTO> getAllPetsByOwner(Long id) {
        List<Pets> petByOwner = this.petsRepository.findByOwnerId(id);
        return petByOwner.stream().map(pet -> {
            PetDTO petDTO = getPetDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            return petDTO;
        }).collect(Collectors.toList());
    }

    private static PetDTO getPetDTO(Pets pets) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pets,petDTO);
        return petDTO;
    }
}
