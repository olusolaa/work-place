package com.decagon.employeemanagementapp.repository;

import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>  {

}
