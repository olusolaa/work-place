package com.decagon.employeemanagementapp.service.impl;

import com.decagon.employeemanagementapp.dtos.LatestEmployeeAttendanceDto;
import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.repository.AttendanceRepository;
import com.decagon.employeemanagementapp.repository.EmployeeRepository;
import com.decagon.employeemanagementapp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
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

    LocalDateTime newStartDateTime;
    LocalDateTime newEndDateTime;

    @Override
    public String markAttendance(Employee employee) {
        String responce2;

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        String resumption = "08:00:00.000";
        String closing = "17:00:00.000";


        String temp1 = localDate+"T"+resumption;
        String temp2 = localDate+"T"+closing;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        newStartDateTime = LocalDateTime.parse(temp1, dateTimeFormatter);
        newEndDateTime = LocalDateTime.parse(temp2, dateTimeFormatter);
        if (localDateTime.isBefore(newStartDateTime)){
            responce2 = "Too early to mark attendance";
        }else if (localDateTime.isAfter(newEndDateTime)){
            responce2 ="Too late to mark attendance";
        }else{
            Optional<Attendance> employeeAttendance = attendanceRepository.findByEmployeeAndAttendanceBetween(employee, newStartDateTime, newEndDateTime);
            if (employeeAttendance.isEmpty()) {
                Optional<Employee> employeeDb = employeeRepository.findById(employee.getId());
                    Attendance attendance = new Attendance();
                    attendance.setEmployee(employeeDb.get());
                    if (localTime.isBefore(LocalTime.parse("09:00:00.000", DateTimeFormatter.ofPattern("HH:mm:ss.SSS")))){
                    attendance.setIsLate(false);
                    }
                    attendance.setLocalTime(LocalTime.now());
                    attendance.setMonthDay(MonthDay.now());
                    attendanceRepository.save(attendance);
                    responce2 = "Attendance marked successfully";
            }else{
                responce2 = "Attendance already marked";
            }
        }
        return responce2;
    }

//    @Override
//    public Boolean getIsLateById (Employee employee) {
//        Optional<Attendance> employeeAttendance = attendanceRepository.findByEmployeeAndAttendanceBetween(employee, newStartDateTime, newEndDateTime);
//        if(employeeAttendance.isEmpty()){
//            return true;
//        }
//        return employeeAttendance.get().getIsLate();
//    }

    @Override
    public List <Attendance> getAttendanceById(Employee employee) {
        return attendanceRepository.findAllAttendanceByEmployee(employee);
    }

    @Override
    public LatestEmployeeAttendanceDto getLatestEmployeeAttendance(Employee employee) {
        Optional<Employee> employeedb = employeeRepository.findById(employee.getId());
        if (employeedb.isPresent()){
            List<Attendance> attendanceList = attendanceRepository.findAllAttendanceByEmployee(employeedb.get());
            if (!attendanceList.isEmpty()){
                Attendance attendance = attendanceList.get(attendanceList.size() - 1);
                LatestEmployeeAttendanceDto latestEmployeeAttendanceDto = new LatestEmployeeAttendanceDto();
                latestEmployeeAttendanceDto.setLate(attendance.getIsLate());
                latestEmployeeAttendanceDto.setEmail(employeedb.get().getEmail());
                latestEmployeeAttendanceDto.setFirstName(employeedb.get().getFirstName());
                latestEmployeeAttendanceDto.setLastName(employeedb.get().getLastName());
                return latestEmployeeAttendanceDto;
            }
            return null;
        }
        return null;
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
