package com.decagon.employeemanagementapp.controllers;

import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SalaryController {

    EmployeeRepository employeeRepository;
    SalaryRepository salaryRepository;

    @Autowired
    public SalaryController(EmployeeRepository employeeRepository, SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }



}
