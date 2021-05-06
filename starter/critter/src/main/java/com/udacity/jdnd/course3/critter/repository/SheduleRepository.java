package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.data.Shedules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheduleRepository extends JpaRepository<Shedules,Long> {
}
