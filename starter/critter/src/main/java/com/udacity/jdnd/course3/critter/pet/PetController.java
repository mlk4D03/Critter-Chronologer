package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = this.petService.savePet(petDTO);
        PetDTO petDTOResponse = getPetDTO(pet);
        petDTOResponse.setOwnerId(pet.getOwner().getId());
        return petDTOResponse;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) throws PetNotFoundException {
        Pet pet =  this.petService.getPetById(petId);
        PetDTO petDTO = getPetDTO(pet);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getAllPets().stream().map(pet -> {
            PetDTO petDTO = getPetDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            return petDTO;
        }).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getAllPetsByOwner(ownerId).stream().map(pet -> {
            PetDTO petDTO = getPetDTO(pet);
            petDTO.setOwnerId(pet.getOwner().getId());
            return petDTO;
        }).collect(Collectors.toList());
    }

    private static PetDTO getPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,petDTO);
        return petDTO;
    }
}
