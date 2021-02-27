package com.decagon.employeemanagementapp.dtos;

import com.decagon.employeemanagementapp.model.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    String role;
    boolean isSuccessful;
    int status;
    String error;
    Object data;
    Employee employee;
}
