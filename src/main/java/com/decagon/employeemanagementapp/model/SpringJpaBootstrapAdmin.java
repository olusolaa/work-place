package com.decagon.employeemanagementapp.model;

import com.decagon.employeemanagementapp.dtos.EmployeeDto;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


    /**
     * Adds the first
     * user as admin
     * to database
     */

    @Component
    public class SpringJpaBootstrapAdmin implements ApplicationListener<ContextRefreshedEvent> {
        private boolean dataAlreadySetup = false;
        private final EmployeeService employeeService;
        private EmployeeRepository employeeRepository;

        @Autowired
        public SpringJpaBootstrapAdmin( EmployeeService employeeService, EmployeeRepository employeeRepository) {
            this.employeeService = employeeService;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            loadFirstUserAsAdmin();
        }

        private void loadFirstUserAsAdmin() {
            List<Employee> users = employeeService.getAllEmployees(1).getContent();

            if (dataAlreadySetup || users.size() > 0) return;

        EmployeeDto admin = new EmployeeDto("admin", "admin", "admin@2");

            employeeService.addEmployee(admin);
            dataAlreadySetup =   true;
        }
}
