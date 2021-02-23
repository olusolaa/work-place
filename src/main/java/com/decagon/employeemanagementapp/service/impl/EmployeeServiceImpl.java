package com.decagon.employeemanagementapp.service.impl;


import com.decagon.employeemanagementapp.dtos.*;
import com.decagon.employeemanagementapp.exception.ResourceNotFoundException;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public List<Employee> getAllEmployees() {

            List<Employee> result = employeeRepository.findAll();
            result.remove(0);
            if(result.size() > 0) {
                return result;
            } else {
                return new ArrayList<>();
            }
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
                System.out.println("I was here");
                employeeDb.get().setFirstName(updateEmployeeDto.getFirstName());
                System.out.println(updateEmployeeDto.getFirstName());
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

//    I have issues displaying errors

}
