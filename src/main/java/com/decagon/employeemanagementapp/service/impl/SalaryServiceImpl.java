package com.decagon.employeemanagementapp.service.impl;


import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.model.Salary;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.repository.SalaryRepository;
import com.decagon.employeemanagementapp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class SalaryServiceImpl implements SalaryService {


    private EmployeeRepository employeeRepository;
    private SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(EmployeeRepository employeeRepository, SalaryRepository salaryRepository) {
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
    }

    @Override
    public String addSalary(Long id, Salary salary) {
        Optional<Employee> employeeDb = employeeRepository.findById(id);
        Salary salary1 = new Salary();
        salary1.setEmployee(employeeDb.get());
        salary1.setAmount(salary.getAmount());
        salary1.setCreatedAt(LocalDateTime.now());
        salaryRepository.save(salary1);
        return salary1.getAmount() +" paid to "+salary1.getEmployee().getFirstName()+" on " + salary1.getCreatedAt();
    }

    @Override
    public List<Salary> getAllSalary() {
        return salaryRepository.findAll();
    }

    @Override
    public List<Salary> getAllSalaryById(Employee employee) {
        return salaryRepository.findByEmployee(employee);
    }

//    @Override
//    public Salary getCurrentSalaryById(Employee employee, LocalDateTime localDateTime) {
//        return salaryRepository.findSalariesByEmployeeAndCreatedAt(employee, localDateTime)
//    }
}