package com.Project_Management.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.ManagerDashboardresponse.Managerdashboard;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.ProjectmanagerDashboardServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;


@RestController()
public class ManagerController {

    @Autowired
    private ProjectmanagerDashboardServices projectmanagerDashboardServices;

    @Autowired 
    private UsersAuthRepo usersAuthRepo;

    @GetMapping("/dashboard")
    public ResponseEntity<Managerdashboard> Dashboard() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String managername = authentication.getName();

        Users users = usersAuthRepo.findByUsername(managername);

        Managerdashboard result = projectmanagerDashboardServices.ManagerDashboardresponse(users.getId());

        return ResponseEntity.ok(result);
    }

    
}
