package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pets;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getName(),customerDTO.getPhoneNumber(),customerDTO.getNotes());
        if(customerDTO.getPetIds() != null) {
            customerDTO.getPetIds().stream().map(petId -> new Pets(petId)).forEach(pet -> customer.getPets().add(pet));
        }
        Customer savedCustomer = this.customerRepository.save(customer);
        CustomerDTO customerDTOResponse = getCustomerDTO(savedCustomer);
        customerDTOResponse.setPetIds(savedCustomer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));

        return customerDTOResponse;
    }

    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = this.customerRepository.findAll();
        return customers.stream().map(c -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(c.getId());
            customerDTO.setName(c.getName());
            customerDTO.setNotes(c.getNotes());
            customerDTO.setPhoneNumber(c.getPhoneNumber());
            List<Long> petIDs = c.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(petIDs);
            return customerDTO;
        }).collect(Collectors.toList());

    }

    public CustomerDTO getOwnerByPet(Long petId) {
        Customer customer = this.customerRepository.findByPetsId(petId);
        CustomerDTO customerDTO = getCustomerDTO(customer);
        ArrayList<Long> petIds = new ArrayList<>();
        petIds.add(petId);
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private static CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }


}
