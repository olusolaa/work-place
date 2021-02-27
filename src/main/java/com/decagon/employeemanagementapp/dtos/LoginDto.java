package com.decagon.employeemanagementapp.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
