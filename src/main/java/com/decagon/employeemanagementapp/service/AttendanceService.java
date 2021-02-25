package com.decagon.employeemanagementapp.service;

import com.decagon.employeemanagementapp.dtos.LatestEmployeeAttendanceDto;
import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public interface AttendanceService {

    String markAttendance(Employee employee);
//    Boolean getIsLateById(Employee employee);
    List<Attendance> getAttendanceById(Employee employee);
    LatestEmployeeAttendanceDto getLatestEmployeeAttendance(Employee employee);
    List<Attendance> getAllEmployeeAttendance();
}
