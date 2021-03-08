package com.decagon.employeemanagementapp.dtos;

import com.decagon.employeemanagementapp.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class SearchDto {
    Page<Employee> page;
    String message;
}
