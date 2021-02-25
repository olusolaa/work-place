package com.decagon.employeemanagementapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LatestEmployeeAttendanceDto {
    private String firstName;
    private String lastName;
    private String email;
    private boolean isLate;
    private String message;
}
