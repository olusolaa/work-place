package com.decagon.employeemanagementapp.controllers;

import com.decagon.employeemanagementapp.dtos.EmployeeDto;
import com.decagon.employeemanagementapp.dtos.ResponseDto;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeController employeeController;

    MockMvc mockMvc;

    @BeforeEach
    void init(){

        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        Employee employee = new Employee();
        employee.setFirstName("David");
        employee.setLastName("Monday");
        employee.setEmail("monday@");
        employee.setId(2l);
        employee.setPassword("monday");
    }

    @Test
    void shouldShowAddEmployeePage() throws Exception {
        Employee admin = new Employee();
        admin.setId(1L);
        admin.setFirstName("Joans");
        admin.setEmail("admin@xmail.com");

        Map<String, Object> sessionAttribute = new HashMap<>();
        sessionAttribute.put("admin", admin);

        mockMvc.perform(get("/admin/employee/add")
                .sessionAttrs(sessionAttribute).param("email", admin.getEmail()))
                .andDo(print())
                .andExpect(model().attributeExists("addEmployee"))
                .andExpect(view().name("add-employee"))
                .andExpect(status().isOk());
    }

    @Test
    void addEmployeeController() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("David");
        employeeDto.setLastName("Monday");
        employeeDto.setEmail("monday1@gmail.com");

//        ResponseDto responseDto = employeeRepository.save(employee)


        //verify(employeeRepository, times(2)).save(employeeFromDto);

//        when(employeeService.addEmployee(ArgumentMatchers.<EmployeeDto>any())).thenReturn(responseDto);

        mockMvc.perform(post("/add-employee")
                .param("firstName", employeeDto.getFirstName())
                .param("lastName", employeeDto.getLastName())
                .param("email", employeeDto.getEmail()))
                .andExpect(status().is3xxRedirection());



        //ResponseDto responseDto =  employeeService.addEmployee(employeeDto);

        //when(employeeService.addEmployee(ArgumentMatchers.<EmployeeDto>any())).thenReturn(responseDto);

        //assertEquals(responseDto.isSuccessful());

    }

}