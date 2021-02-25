package com.decagon.employeemanagementapp.controllers;


import com.decagon.employeemanagementapp.dtos.*;
import com.decagon.employeemanagementapp.model.Attendance;
import com.decagon.employeemanagementapp.model.Employee;
import com.decagon.employeemanagementapp.service.AttendanceService;
import com.decagon.employeemanagementapp.service.EmployeeService;
import com.decagon.employeemanagementapp.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
//@RequestMapping("/api/v1")
public class EmployeeController {

    private EmployeeService employeeService;
    private AttendanceService attendanceService;
    private SalaryService salaryService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AttendanceService attendanceService, SalaryService salaryService) {
        this.employeeService = employeeService;
        this.attendanceService = attendanceService;
        this.salaryService = salaryService;
    }

    /**
     * home page.
     *
     * @param model model
     * @return String
     */

    @GetMapping("/")
    public String loginGet(Model model) {

        model.addAttribute("isSuccessful", true);
        model.addAttribute("loginDto", new LoginDto());
        model.addAttribute("activationDto", new ActivationDto());
        return "auth";
    }

//    @GetMapping("/home")
//    public String home(HttpSession session) {
//        var admin = session.getAttribute("principal");
//        if (admin == null) return "redirect:/";
//        return "home";
//    }

    @PostMapping(path = "/login")
    public String loginPost(Model model, LoginDto loginDto, HttpSession session) {
        log.info("in login post!");

        ResponseDto response = employeeService.logIn(loginDto);
        // you want to return the employee from employeeService.login
        // logout: session.invalidate();password
        System.out.println(response.isSuccessful());
        if (response.isSuccessful()) {
            session.setAttribute("principal", response.getEmployee());
            //Employee employee1 = (Employee) session.getAttribute("principal");
            if (response.getRole().equals("Admin")) {
                model.addAttribute("addEmployee", new EmployeeDto());
                return "redirect:/admin";
            } else return "redirect:/employee";
        } else {
            model.addAttribute("error", response.getError());
            model.addAttribute("loginDto", new LoginDto());
            model.addAttribute("activationDto", new ActivationDto());
            return "auth";
        }
    }

    @GetMapping (path = "/employee")
    public String getEmployeeDashboard(HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("principal");
        if (employee == null) return "redirect:/";

        model.addAttribute("employee", attendanceService.getLatestEmployeeAttendance(employee));

//        model.addAttribute("isLate", a));

        return "employee";
    }

    @RequestMapping(path = "/admin")
        public String redirectAdmin(HttpSession session) {
        var admin = session.getAttribute("principal");
        if (admin == null)return "redirect:/";
        return "dashboard";
    }

    @PostMapping(path = "/signUp")
    public String signUp(ActivationDto em, Model model) {
        ResponseDto response = employeeService.activateAccount(em);

        if (response.isSuccessful()) {
            return "redirect:/";
        }else {
            System.out.println(response.isSuccessful());
            model.addAttribute("error", response.getError());
            model.addAttribute("loginDto", new LoginDto());
            model.addAttribute("ActivationDto", new ActivationDto());
            return "auth";
        }
    }

    @GetMapping(path = "/admin/employees")
    public String getAllEmployees(Model model, HttpSession session) {
        var admin = session.getAttribute("principal");
        if (admin == null)return "redirect:/";
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "view-all-employee";
    }

    @GetMapping(path = "/admin/employee/add")
    public String addEmployee(Model model, HttpSession session){
        var admin = session.getAttribute("principal");
        if (admin == null)return "redirect:/";
        model.addAttribute("addEmployee", new EmployeeDto());
        return "add-employee";
    }

    @PostMapping(value = "/add-employee" )
    public String addEmployeePost(@Valid @ModelAttribute("addEmployee") EmployeeDto employeeDto, RedirectAttributes redirectAttributes) {
        ResponseDto response = employeeService.addEmployee(employeeDto);
        employeeService.addEmployee(employeeDto);
        if (response.isSuccessful()) {
            return "redirect:/admin/employees";
        }
        redirectAttributes.addFlashAttribute("error", response.getError());
        return "redirect:/admin/employee/add";
    }


    @GetMapping(path = "/admin/get-employee-detail/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model, HttpSession session) {
        var admin = session.getAttribute("principal");
        if (admin == null)return "redirect:/";
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employeeDetail", employee);
        model.addAttribute("attendanceList", attendanceService.getAttendanceById(employee));
        model.addAttribute("salaryList", salaryService.getAllSalaryById(employee));
        return "get-employee-detail";
        //@PathVariable(value = "id") long id
    }

//    @GetMapping(path = "/admin/view-employee-detail/{id}")
//    public String viewEmployeebyId(Model model){
//        model.getAttribute("employeeDetail");
//
//    }


    @GetMapping(value = "/admin/delete-employee/{id}")
    public String deleteEmployee(@PathVariable Long id, HttpSession session) {
        var admin = session.getAttribute("principal");
        if (admin == null)return "redirect:/";
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/update-employee/{id}")
    public String updateEmployeeGet(Model model, @PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update-employee";
    }

    @PostMapping("/admin/employees/update/{id}")
    public String updateEmployeePost(@ModelAttribute("employee") UpdateEmployeeDto updateEmployeeDto, @PathVariable Long id) {
        employeeService.updateEmployee(updateEmployeeDto, id);
        return "redirect:/admin/employees";
    }

}

