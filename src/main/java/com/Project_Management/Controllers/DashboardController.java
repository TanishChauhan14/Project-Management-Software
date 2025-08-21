package com.Project_Management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.AdminDashboardresponse.AdminDashboardDTO;
import com.Project_Management.Services.AdminDashboardServices;



@RestController
public class DashboardController {

    @Autowired
    private AdminDashboardServices dashboardServices;

    @GetMapping("/dashbord")
    public AdminDashboardDTO Dashboardresponse(){
        return dashboardServices.Dashboardresponse();
    }
}
