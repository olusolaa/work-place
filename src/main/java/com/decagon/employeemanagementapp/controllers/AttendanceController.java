package com.decagon.employeemanagementapp.controllers;


import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.service.AttendanceService;
import com.decagon.employeemanagementapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AttendanceController {

    AttendanceService attendanceService;
    EmployeeService employeeService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, EmployeeService employeeService) {
        this.attendanceService=attendanceService;
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/employee/mark-attendance")
    public String markAttendance(HttpSession session, RedirectAttributes redirectAttributes){
        Employee employee = (Employee) session.getAttribute("principal");
        String response2 = attendanceService.markAttendance(employee);
        redirectAttributes.addFlashAttribute("marked", response2);
        return "redirect:/employee";
    }

    @GetMapping(path = "/admin/employee/attendance/{id}")
    public String getAttendanceById(HttpSession session, Employee employee, Model model){
        var admin = session.getAttribute("admin");
        if (admin == null){
            return "redirect:/";
        }
        model.addAttribute("attendances", attendanceService.getAttendanceById(employee));
        return "attendance-page";
    }

    @GetMapping("/admin/all-employee/attendance/")
    public String getAllEmployeeAttendance(HttpSession session, Model model){
        var admin = session.getAttribute("admin");
//        if (admin == null){
//            return "redirect:/";
//        }
        List<Attendance> attendanceList = attendanceService.getAllEmployeeAttendance();
        List<Employee> employeesList = attendanceList.stream().map(Attendance::getEmployee).collect(Collectors.toList());

        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("employeeList", employeesList);

        return "all-employee-attendance";

    }

    @GetMapping("/employee/attendance-history")
    public String getAllAttendanceById(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("principal");
        if (employee == null) {
            return "redirect:/";
        }
        model.addAttribute("attendanceList", attendanceService.getAttendanceById(employee));
        return "attendance-history";
    }
}
