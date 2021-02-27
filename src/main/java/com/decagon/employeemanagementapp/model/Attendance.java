package com.decagon.employeemanagementapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Attendance extends BaseModel{

    private boolean isPresent = false;
    @ManyToOne
 //   @JoinColumn(name = ("employee_id"))
    private Employee employee;
}
