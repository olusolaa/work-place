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
    Optional<List<Employee>> findAllByFirstNameContainsOrLastNameContainsOrderByFirstName(String lastName, String firstName);
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByEmailAndPassword(String email, String password);
    Page<Employee> findAll(Pageable pageable);
    List<Employee> findAllByFirstNameContains(String firstName);
    List<Employee> findAllByLastNameContains(String LastName);
    List<Employee> findAllByFirstNameContainsAndLastNameContains(String firstName, String LastName);



    //service
//    if(!firstname.isBlank()){
//        if(!lastname.isBlank()){
//            findAllByFirstNameContainsAndLastNameContains(firstname, lastname);
//        }else{
//            findAllByFirstNameContains(firstname);
//        }
//    }else{
//        if(!lastname.isBlank()){
//            findAllByLastNameContains(lastname);
//        }else{
//            findAll();
//        }
//    }
}
