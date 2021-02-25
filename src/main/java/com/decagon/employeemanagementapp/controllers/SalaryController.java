package com.decagon.employeemanagementapp.controllers;

import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.model.Salary;
import com.decagon.employeemanagementapp.service.EmployeeService;
import com.decagon.employeemanagementapp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SalaryController {

    SalaryService salaryService;
    EmployeeService employeeService;

    @Autowired
    public SalaryController(SalaryService salaryService, EmployeeService employeeService) {
        this.salaryService = salaryService;
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/admin/add-salary/{id}")
    public String addSalaryGet(@PathVariable Long id, Model model){
        model.addAttribute("salary", new Salary());
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        System.out.println("i GOT HERE");
        return "admin-add-salary";
    }

    @PostMapping(path = "/admin/salaries/add/{id}")
    public String addSalaryPost(@ModelAttribute("salary") Salary salary, @PathVariable Long id, Model model){
        System.out.println("aBOUT TO ADD SALARY");
        salaryService.addSalary(id, salary);
        System.out.println(" I am At the point of redirecting");
        return "redirect:/admin/employees";
    }

    @GetMapping(path = "/admin/salary/view-all")
    public String getAllSalaries(Model model){
        model.addAttribute("salary", salaryService.getAllSalary());
        System.out.println("i GOT HERE");
        return "view-all-salaries";
    }
}
