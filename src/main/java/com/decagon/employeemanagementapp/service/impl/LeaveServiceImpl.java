package com.decagon.employeemanagementapp.service.impl;


import com.decagon.employeemanagementapp.model.Leave;
import com.decagon.employeemanagementapp.repository.LeaveRepository;
import com.decagon.employeemanagementapp.service.LeaveService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    private LeaveRepository leaveRepository;

    public LeaveServiceImpl(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    @Override
    public String requestForLeaves(Leave leave) {
        leaveRepository.save(leave);
        return ("salary added");
    }

    @Override
    public String approveLeaves(Leave leave) {
        return "approved";
    }

    @Override
    public Leave fetchLeaves(Long salaryId) {
        return null;
    }

    @Override
    public List<Leave> fetchAllLeaves() {
        return null;
    }
}
