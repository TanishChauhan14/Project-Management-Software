package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.EmployeeDashboardresponse.EmployeeDashboardDTO;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.EmployeeDashboardServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDashboardServices employeeDashboardServices;

    @Autowired
    private UsersAuthRepo usersAuthRepo;

    @GetMapping("/Emdashboard")
    public ResponseEntity<EmployeeDashboardDTO> EmployeeDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users users = usersAuthRepo.findByUsername(username);

        EmployeeDashboardDTO employeeDashboardDTO = employeeDashboardServices.EmployeeDashboardresponse(users.getId());

        return ResponseEntity.ok(employeeDashboardDTO);
    }
    
}
