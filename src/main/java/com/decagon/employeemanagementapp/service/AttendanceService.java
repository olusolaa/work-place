package com.decagon.employeemanagementapp.service;

import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {

    String markAttendance(Employee employee);
    List<Attendance> getAttendanceById(Employee employee);
    List<Attendance> getAllEmployeeAttendance();
}
