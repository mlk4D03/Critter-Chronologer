package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        return this.employeeRepository.save(new Employee(employeeDTO.getName(),employeeDTO.getSkills(),employeeDTO.getDaysAvailable()));
    }

    public void addAvailableDays(Set<DayOfWeek> availableDays, Long id) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = this.employeeRepository.findById(id);
        if(employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if(employee.getDaysAvailable() == null) {
                Set<DayOfWeek> daysAvailable = availableDays;
                employee.setDaysAvailable(daysAvailable);
            } else {
                employee.getDaysAvailable().addAll(availableDays);
            }
            this.employeeRepository.save(employee);
        } else {
            throw new EmployeeNotFoundException("An Employee with ID = " + id + " is not available");
        }
    }

    public Employee findEmployeeById(Long id) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
        if(optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        }
        throw new EmployeeNotFoundException("An Employee with ID = \" + id + \" is not available");
    }

    public List<Employee> getEmployeesForService(DayOfWeek day, Set<EmployeeSkill> employeeSkills) {
        List<Employee> employees = this.employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(day,employeeSkills);
        return employees.stream().filter(employee -> employee.getSkills().containsAll(employeeSkills)).collect(Collectors.toList());
    }

}
