package com.decagon.employeemanagementapp.repository;

import com.decagon.employeemanagementapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findAllByFirstNameIgnoreCaseContainsOrLastNameIgnoreCaseContainsOrderByFirstName(String firstName, String LastName, Pageable pageable);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByEmailAndPassword(String email, String password);
    Page<Employee> findAll(Pageable pageable);
}
