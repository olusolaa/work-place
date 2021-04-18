package com.decagon.employeemanagementapp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
public class EmployeeDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;

    private Long id;

    public EmployeeDto(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public EmployeeDto() {
    }
}
