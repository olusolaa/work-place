package com.decagon.employeemanagementapp.service;

import com.decagon.employeemanagementapp.model.Leave;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LeaveService {

    String requestForLeaves(Leave leave);
    String approveLeaves(Leave leave);
    Leave fetchLeaves(Long leaveId);
    List<Leave> fetchAllLeaves();

}
