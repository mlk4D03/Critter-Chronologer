package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(PetDTO petDTO) {
        Pet pet = new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes());
        Optional<Customer> customerOptional = this.customerRepository.findById(petDTO.getOwnerId());
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            pet.setOwner(customer);
        }
        Pet savedPet = this.petsRepository.save(pet);
        Customer customer = savedPet.getOwner();
        if (customer != null) {
            List<Pet> customerPets = customer.getPets();
            if (customerPets == null) {
                customerPets = new ArrayList<>();
            }
            customerPets.add(savedPet);
            customer.setPets(customerPets);
            customerRepository.save(customer);
        }
        return savedPet;
    }

    public List<Pet> getAllPets() {
        return this.petsRepository.findAll();
    }

    public List<Pet> getAllPetsByOwner(Long id) {
        return this.petsRepository.findByOwnerId(id);
    }

    public Pet getPetById(Long petId) throws PetNotFoundException {
        Optional<Pet> petById = this.petsRepository.findById(petId);
        if (petById.isPresent()) {
            Pet pet = petById.get();
            return pet;
        }
        throw new PetNotFoundException("The requested Pet with Id: " + petId + " was not found!");
    }

}
