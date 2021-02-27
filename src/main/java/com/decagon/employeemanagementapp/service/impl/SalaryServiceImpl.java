package com.decagon.employeemanagementapp.service.impl;



import com.decagon.employeemanagementapp.exception.ResourceNotFoundException;
import com.decagon.employeemanagementapp.model.Salary;
import com.decagon.employeemanagementapp.repository.SalaryRepository;
import com.decagon.employeemanagementapp.service.SalaryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {
    private SalaryRepository salaryRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public String addSalary(Salary salary) {
        salaryRepository.save(salary);
        return ("salary added");

    }

    @Override
    public Salary fetchSalary(Long salaryId) {
        Optional<Salary>  salary = salaryRepository.findById(salaryId);
        if(salary.isEmpty()){
            throw new ResourceNotFoundException("incorrect email or password");
        }
        return salary.get();
    }

    @Override
    public List<Salary> fetchAllSalary() {
        return salaryRepository.findAll();
    }
}
