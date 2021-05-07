package com.udacity.jdnd.course3.critter.data;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employeeSkills", joinColumns = @JoinColumn(name = "employeeId"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "days", joinColumns = @JoinColumn(name = "employeeId"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<Shedules> shedules = new ArrayList<>();

    public Employee(String name,Set<EmployeeSkill> employeeSkills,Set<DayOfWeek> daysAvailable) {
        super(name);
        this.daysAvailable = daysAvailable;
        this.skills = employeeSkills;
    }

    public Employee() {
        super();
    }

    public Employee(Long id){
        super(id);
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Shedules> getShedules() {
        return shedules;
    }

    public void setShedules(List<Shedules> shedules) {
        this.shedules = shedules;
    }
}
