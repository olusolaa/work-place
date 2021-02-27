package com.decagon.employeemanagementapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Setter
@Getter
public class Employee extends BaseModel{

    @Column
    private String firstName;

    @Column(name = ("last_name"))
    private String lastName;

    @Column(name = ("email"))
    private String email;

    @Column(name = ("password"))
    private String password;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Leave> leaves;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Salary> salaries;

    @OneToMany
    private List<Attendance> attendances;


}
