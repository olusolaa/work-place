package com.decagon.employeemanagementapp.service.impl;


import com.decagon.employeemanagementapp.dtos.*;
import com.decagon.employeemanagementapp.exception.ResourceNotFoundException;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //I dont really understand this part
    @Override
    public ResponseDto logIn(LoginDto loginDto) {


        ResponseDto response = new ResponseDto();
        try{
            Optional<Employee> employeeDb1 = employeeRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());

            if(employeeDb1.isPresent()){
                response.setStatus(201);
                response.setSuccessful(true);
                response.setData(employeeDb1.get());
                response.setEmployee(employeeDb1.get());
                if (employeeDb1.get().getId() == 1){
                    response.setRole("Admin");
                }
                else {
                    response.setRole("Employee");
                }
                return  response;
            }
            throw new ResourceNotFoundException("Incorrect email or password");
        }catch (Exception e){
            response.setStatus(500);
            response.setSuccessful(false);
            response.setData(null);
            response.setError(e.getMessage());
            return response;
        }
    }


    @Override
    public ResponseDto activateAccount(ActivationDto activationDto) {

        var response = new ResponseDto();

//        if (employeeRepository.findAll() == null) {
//            System.out.println("im here not admin");
            try {
                Optional<Employee> employeeDb = employeeRepository.findByEmail(activationDto.getEmail());
                Optional<Employee> employeeDb2 = employeeRepository.findById(activationDto.getId());

                if (!activationDto.getPassword().equals(activationDto.getConfirmPassword())) {
                    throw new ResourceNotFoundException("Password does not match");
                }

                if (!employeeDb.isPresent() || !employeeDb.get().equals(employeeDb2.get())) {
                    throw new ResourceNotFoundException("Access denied! Not a valid employee");
                }

                if (employeeDb.isPresent() && employeeDb.get().getPassword() != null) {
                    throw new ResourceNotFoundException("Unsuccessful! This user has ALREADY been activated");
                }
                employeeDb2.get().setPassword(activationDto.getPassword());
                employeeRepository.save(employeeDb2.get());
                System.out.println(employeeDb2.get().getPassword());


//                I don't really need this condition because of @valid
                if (employeeDb.get().getPassword() != null) {
                    response.setStatus(201);
                    response.setSuccessful(true);
                    response.setData(employeeDb.get().getPassword());
                    return response;
                } else {
                    throw new Exception("Something went wrong");
                }
            } catch (Exception e) {
                response.setStatus(500);
                response.setSuccessful(false);
                response.setData(null);
                response.setError(e.getMessage());
                return response;
            }
        }
//        else {
//            System.out.println("Admin login");
//            try {
//                Employee admin = new Employee();
//                if (!activationDto.getPassword().equals(activationDto.getConfirmPassword())) {
//                    throw new ResourceNotFoundException("Password does not match");
//                }
//                admin.setPassword(activationDto.getPassword());
//                admin.setEmail(activationDto.getEmail());
//                employeeRepository.save(admin);
//            } catch (ResourceNotFoundException e) {
//                response.setStatus(500);
//                response.setSuccessful(false);
//                response.setData(null);
//                response.setError(e.getMessage());
//            }
//
//        }
//        return response;
//    }



    @Override
    public Page<Employee> getAllEmployees(int pageNum) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            Page<Employee> result = employeeRepository.findAll(pageable);
            return result;
//            result.remove(0);
//            if(result.size() > 0) {
//                return result;
//            } else {
//                return new ArrayList<>();
//            }
        //you can use pagination here
    }

    public Employee getEmployeeById(Long id) throws ResourceNotFoundException
    {
        Optional<Employee> employeeDb = this.employeeRepository.findById(id);
        if (employeeDb.isEmpty()) {
            throw new ResourceNotFoundException("No employee record exist for id "+ id);
        }
        return employeeDb.get();
    }

    @Override
    public ResponseDto updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long id) {
        ResponseDto response = new ResponseDto();
        try {
            Optional<Employee> employeeDb = this.employeeRepository.findById(id);
            if (employeeDb.isPresent()) {
                employeeDb.get().setFirstName(updateEmployeeDto.getFirstName());
                employeeDb.get().setLastName(updateEmployeeDto.getLastName());
                employeeDb.get().setEmail(updateEmployeeDto.getEmail());
                employeeRepository.save(employeeDb.get());
                response.setStatus(201);
                response.setSuccessful(true);
                return response;
            }
            throw new ResourceNotFoundException("No record found for Employee with Id " + id);
        } catch (Exception e) {
            response.setStatus(500);
            response.setSuccessful(false);
            response.setData(null);
            response.setError(e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDto addEmployee(EmployeeDto employeeDto) {
        var response = new ResponseDto();
        try{
            Optional<Employee> employeeDb = employeeRepository.findByEmail(employeeDto.getEmail());
            if (employeeDb.isPresent()) {
                throw new ResourceNotFoundException("Email has already been given to another employee");
            }
            Employee n = new Employee();
            n.setFirstName(employeeDto.getFirstName());
            n.setLastName(employeeDto.getLastName());
            n.setEmail(employeeDto.getEmail());
            var addedEmployee = employeeRepository.save(n);
            if(addedEmployee != null){
                response.setStatus(201);
                response.setSuccessful(true);
                response.setData(addedEmployee);
                return  response;
            }else{
                throw new Exception("Something went wrong");
            }
        }catch (Exception e) {
            response.setStatus(500);
            response.setSuccessful(false);
            response.setData(null);
            response.setError(e.getMessage());
            return response;
        }
    }

    public String deleteEmployee(Long id) throws ResourceNotFoundException {
        Optional<Employee> employeeDb = this.employeeRepository.findById(id);
        if (employeeDb.isEmpty()) {
            throw new ResourceNotFoundException("No employee record exist for id "+ id);
        }
        employeeRepository.deleteById(id);
        return "deleted";
    }

    @Override
    public SearchDto search(String firstname, String lastname){
        log.info(firstname+", "+lastname);
        var searchDto = new SearchDto();
        if (!firstname.isBlank() || firstname != null){
            if (!lastname.isBlank() || lastname != null){
                List<Employee> employees = employeeRepository.findAllByFirstNameContainsAndLastNameContains(firstname, lastname);
                searchDto.setEmployees(employees);
                if (employees.isEmpty()) searchDto.setMessage("No result found");
                else searchDto.setMessage(employees.size() + "employees found");
            }else{
                List<Employee> employees = employeeRepository.findAllByFirstNameContains(firstname);
                searchDto.setEmployees(employees);
                if (employees.isEmpty()) searchDto.setMessage("No result found");
                else searchDto.setMessage(employees.size() + "employees found");
            }
        }else{
            if (!lastname.isBlank() || lastname != null){
                List<Employee> employees = employeeRepository.findAllByLastNameContains(lastname);
                searchDto.setEmployees(employees);
                if (employees.isEmpty()) searchDto.setMessage("No result found");
                else searchDto.setMessage(employees.size() + "employees found");
            }else{
                List<Employee> employees = employeeRepository.findAll();
                searchDto.setEmployees(employees);
                if (employees.isEmpty()) searchDto.setMessage("No result found");
                else searchDto.setMessage(employees.size() + "employees found");
            }
        }
        log.info(String.valueOf(searchDto.getEmployees().size()));
        return searchDto;
    }

}
