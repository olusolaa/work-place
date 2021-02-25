package com.decagon.employeemanagementapp.service;

import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.model.Salary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface SalaryService {

    String addSalary(Long id, Salary salary);
    List<Salary> getAllSalary();
    List<Salary> getAllSalaryById(Employee employee);
//    Salary getCurrentSalaryById(Employee employee, LocalDateTime localDateTime);
}