package com.decagon.employeemanagementapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class Salary extends BaseModel{


    private double amount;

    @ManyToOne
    @JoinColumn(name = ("employee_id"))
    private Employee employee;
}
