package com.decagon.employeemanagementapp.repository;

import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllAttendanceByEmployee(Employee employee);
    List<Attendance> findAllByAttendanceBetween(LocalDateTime resumption, LocalDateTime closing);
    Optional<Attendance> findByEmployeeAndAttendanceBetween(Employee employee, LocalDateTime resumption, LocalDateTime closing);


}
