package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pets;
import com.udacity.jdnd.course3.critter.data.Shedules;
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

    public Shedules saveShedule(ScheduleDTO scheduleDTO) {
        List<Pets> pets = scheduleDTO.getPetIds().stream().map(id -> new Pets(id)).collect(Collectors.toList());
        List<Employee> employees = scheduleDTO.getEmployeeIds().stream().map(id -> new Employee(id)).collect(Collectors.toList());
        Shedules schedule = new Shedules(employees,scheduleDTO.getActivities(),pets,scheduleDTO.getDate());
        return this.sheduleRepository.save(schedule);
    }
}
