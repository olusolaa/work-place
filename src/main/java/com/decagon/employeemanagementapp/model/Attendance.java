package com.decagon.employeemanagementapp.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.*;

@Entity
@Getter
@Setter
public class Attendance extends BaseModel{


    @CreationTimestamp
    private LocalDateTime attendance;

    private MonthDay monthDay;
    private LocalTime localTime;
    private Boolean isLate = true;

    @ManyToOne
 //   @JoinColumn(name = ("employee_id"))
    private Employee employee;
}
