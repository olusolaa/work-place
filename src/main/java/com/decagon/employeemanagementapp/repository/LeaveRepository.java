package com.decagon.employeemanagementapp.repository;

import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long>  {
}
