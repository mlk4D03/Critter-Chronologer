package com.udacity.jdnd.course3.critter.data;


import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Shedules {

    @Id
    @GeneratedValue
    Long id;

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<Pets> pets = new ArrayList<>();

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "activities", joinColumns = @JoinColumn(name = "sheduleId"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    private LocalDate date;

    public Shedules(List<Employee> employees, Set<EmployeeSkill> activities,List<Pets> pets, LocalDate date) {
        this.employees = employees;
        this.activities = activities;
        this.date = date;
        this.pets = pets;
    }

    public Shedules(){

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

    public List<Pets> getPets() {
        return pets;
    }

    public void setPets(List<Pets> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
