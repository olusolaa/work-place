package com.decagon.employeemanagementapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity(name = "employee_leaves")
@Setter
@Getter
public class Leave extends BaseModel{


    private Date date;
    private String type;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = ("employee_id"))
    private Employee employee;
}
