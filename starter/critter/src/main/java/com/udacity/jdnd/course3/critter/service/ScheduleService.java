package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.SheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private SheduleRepository sheduleRepository;

    public Schedule saveShedule(ScheduleDTO scheduleDTO) {
        List<Pet> pets = scheduleDTO.getPetIds().stream().map(id -> new Pet(id)).collect(Collectors.toList());
        List<Employee> employees = scheduleDTO.getEmployeeIds().stream().map(id -> new Employee(id)).collect(Collectors.toList());
        Schedule schedule = new Schedule(employees, scheduleDTO.getActivities(), pets, scheduleDTO.getDate());
        return this.sheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedulesByEmployee(Long employeeId) {
        return this.sheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getAllSchedulesByPets(Long petId) {
        return this.sheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getAllSchedules() {
        return this.sheduleRepository.findAll();
    }

}
