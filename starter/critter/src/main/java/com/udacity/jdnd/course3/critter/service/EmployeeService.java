package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee savedEmployee = this.employeeRepository.save(new Employee(employeeDTO.getName(),employeeDTO.getSkills(),employeeDTO.getDaysAvailable()));
        return getEmployeeDTO(savedEmployee);
    }

    public void addAvailableDays(Set<DayOfWeek> availableDays, Long id) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
        if(employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.getDaysAvailable().addAll(availableDays);
            this.employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("An Employee with ID = " + id + " is not available");
        }
    }
    private static EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

}
