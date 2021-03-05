package com.decagon.employeemanagementapp.dtos;

import com.decagon.employeemanagementapp.model.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchDto {
    List<Employee> employees;
    String message;
}
