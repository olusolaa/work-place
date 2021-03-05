package com.decagon.employeemanagementapp.service;


import com.decagon.employeemanagementapp.dtos.*;
import com.decagon.employeemanagementapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {


    ResponseDto logIn(LoginDto loginDto);
    ResponseDto activateAccount(ActivationDto activationDto);
    Page<Employee> getAllEmployees(int pageNum);
    Employee getEmployeeById(Long id);
    String deleteEmployee(Long Id);
    ResponseDto updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long id);
    ResponseDto addEmployee(EmployeeDto employeeDto);
    SearchDto search(String firstname, String lastname);


}
