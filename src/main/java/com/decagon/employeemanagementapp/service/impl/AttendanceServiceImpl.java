package com.decagon.employeemanagementapp.service.impl;

import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.AttendanceRepository;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    AttendanceRepository attendanceRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public String markAttendance(Employee employee) {
        String responce2;
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.toString().split("T")[0];
        String time = localDateTime.toString().split("T")[1];
        String hour = time.split(":")[0];
        String newStartTime = hour.replace(hour,"08:00:00.000");
        String newEndTime = hour.replace(hour,"17:00:00.000");
        String newStartDate = date+"T"+newStartTime;
        String newEndDate = date+"T"+newEndTime;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime localStartDate = LocalDateTime.parse(newStartDate, dateTimeFormatter);
        LocalDateTime localEndDate = LocalDateTime.parse(newEndDate, dateTimeFormatter);
        if (localDateTime.isBefore(localStartDate)){
            responce2 = "Too early to mark attendance";
        }else if (localDateTime.isAfter(localEndDate)){
            responce2 ="Too late to mark attendance";
        }else{
            Optional<Attendance> employeeAttendance = attendanceRepository.findByEmployeeAndAttendanceBetween(employee, localStartDate, localEndDate);
            if (employeeAttendance.isEmpty()) {
                Optional<Employee> employeeDb = employeeRepository.findById(employee.getId());
                if (employeeDb.isPresent()) {
                    Attendance attendance = new Attendance();
                    attendance.setEmployee(employeeDb.get());
                    attendance.setPresent(true);
                    attendanceRepository.save(attendance);
                    responce2 = "Attendance marked successfully";
                }else{
                    responce2 = "Employee does not exists";
                }
            }else{
                responce2 = "Attendance already marked";
            }
        }
        return responce2;
    }

    @Override
    public List <Attendance> getAttendanceById(Employee employee) {
        return attendanceRepository.findAllAttendanceByEmployee(employee);
    }

    @Override
    public List<Attendance> getAllEmployeeAttendance() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.toString().split("T")[0];
        String time = localDateTime.toString().split("T")[1];
        String hour = time.split(":")[0];
        String newStartTime = hour.replace(hour,"00:00:00.000");
        String newEndTime = hour.replace(hour,"23:59:59.999");
        String newStartDate = date+"T"+newStartTime;
        String newEndDate = date+"T"+newEndTime;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime localStartDate = LocalDateTime.parse(newStartDate, dateTimeFormatter);
        LocalDateTime localEndDate = LocalDateTime.parse(newEndDate, dateTimeFormatter);
        return attendanceRepository.findAllByAttendanceBetween(localStartDate, localEndDate);
    }
}
