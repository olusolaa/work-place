package com.decagon.employeemanagementapp.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmployeeDto {

    private String firstName;
    private String lastName;
    private String email;
    private Long id;

}
