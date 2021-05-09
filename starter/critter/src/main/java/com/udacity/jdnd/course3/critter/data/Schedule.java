package com.udacity.jdnd.course3.critter.data;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    Long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Pet> pets = new ArrayList<>();

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "activities", joinColumns = @JoinColumn(name = "sheduleId"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    private LocalDate date;

    public Schedule(List<Employee> employees, Set<EmployeeSkill> activities, List<Pet> pets, LocalDate date) {
        this.employees = employees;
        this.activities = activities;
        this.date = date;
        this.pets = pets;
    }

    public Schedule(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
