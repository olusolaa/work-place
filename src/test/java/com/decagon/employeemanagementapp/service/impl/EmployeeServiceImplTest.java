package com.decagon.employeemanagementapp.service.impl;

import com.decagon.employeemanagementapp.controllers.EmployeeController;
import com.decagon.employeemanagementapp.dtos.EmployeeDto;
import com.decagon.employeemanagementapp.dtos.ResponseDto;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    Employee employee;


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        employee = new Employee();
        employee.setFirstName("David");
        employee.setLastName("Monday");
        employee.setEmail("monday@");
        employee.setId(2l);
        employee.setPassword("monday");
    }
    @Test
    void logIn() {

    }

    @Test
    void activateAccount() {
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void getEmployeeById() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void addEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("David");
        employeeDto.setLastName("Monday");
        employeeDto.setEmail("monday1@gmail.com");

        Employee employeeFromDto = new Employee();
        employeeFromDto.setId(2L);
        employeeFromDto.setFirstName(employeeDto.getFirstName());
        employeeFromDto.setLastName(employeeDto.getLastName());
        employeeFromDto.setEmail(employeeDto.getEmail());


        when(employeeRepository.save(ArgumentMatchers.<Employee>any())).thenReturn(employeeFromDto);

        ResponseDto responseDto =  employeeService.addEmployee(employeeDto);
        Employee savedEmployee = (Employee) responseDto.getData();
        assert savedEmployee.getId() != null;
        assertTrue(responseDto.isSuccessful());
    }

    @Test
    void deleteEmployee() {
        String adminReturnValue = "Access denied! Admin cannot be deleted";
        String userReturnValue = "deleted";
        when(employeeRepository.findById(2L)).thenReturn(Optional.of(employee));

        assertEquals(userReturnValue, employeeService.deleteEmployee(2L));

        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertEquals(adminReturnValue, employeeService.deleteEmployee(1L));
    }

    @Test
    void search() {
    }
}