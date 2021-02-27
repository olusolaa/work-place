package com.decagon.employeemanagementapp.service;

import com.decagon.employeemanagementapp.model.Salary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalaryService {

    String addSalary(Salary salary);
    Salary fetchSalary(Long salaryId);
    List<Salary> fetchAllSalary();
}
