package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule =  this.scheduleService.saveShedule(scheduleDTO);
        List<Long> petIds = savedSchedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
        List<Long> employeesId = savedSchedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList());
        Set<EmployeeSkill> activities = savedSchedule.getActivities();
        ScheduleDTO scheduleDTOResponse = new ScheduleDTO();
        scheduleDTOResponse.setId(savedSchedule.getId());
        scheduleDTOResponse.setDate(savedSchedule.getDate());
        scheduleDTOResponse.setActivities(activities);
        scheduleDTOResponse.setPetIds(petIds);
        scheduleDTOResponse.setEmployeeIds(employeesId);
        return scheduleDTOResponse;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return getSchedulesDTOList(this.scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return getSchedulesDTOList(this.scheduleService.getAllSchedulesByPets(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedulesByEmployees =  this.scheduleService.getAllSchedulesByEmployee(employeeId);
        return getSchedulesDTOList(schedulesByEmployees);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Pet> petByCustomer = this.petService.getAllPetsByOwner(customerId);
        List<Schedule> schedules = this.scheduleService.getAllSchedules();
        schedules =  schedules.stream().filter(schedule -> {
            Set<Long> petIdsSchedule = new HashSet<>(schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toSet()));
            for(Pet pet : petByCustomer) {
                if(petIdsSchedule.contains(pet.getId())) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        return getSchedulesDTOList(schedules);
    }

    private List<ScheduleDTO> getSchedulesDTOList(List<Schedule> schedulesList) {
        return schedulesList.stream().map(schedule -> {
            List<Long> petIds = schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            List<Long> employeesId = schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList());
            Set<EmployeeSkill> activities = schedule.getActivities();
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setActivities(activities);
            scheduleDTO.setEmployeeIds(employeesId);
            scheduleDTO.setPetIds(petIds);
            scheduleDTO.setDate(schedule.getDate());
            scheduleDTO.setId(schedule.getId());
            return scheduleDTO;
        }).collect(Collectors.toList());
    }

}
