package com.decagon.employeemanagementapp.repository;


import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>  {

    List<Salary>findSalariesByCreatedAtBetween(LocalDateTime localDate, LocalDateTime localDate2);
    List<Salary>findByEmployee(Employee employee);
    Salary findSalariesByEmployeeAndCreatedAt(Employee employee, LocalDateTime createdAt);


}