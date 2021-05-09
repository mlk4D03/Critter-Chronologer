package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> findAllByEmployeesId(Long employeesId);

    List<Schedule> findAllByPetsId(Long petsId);
}
