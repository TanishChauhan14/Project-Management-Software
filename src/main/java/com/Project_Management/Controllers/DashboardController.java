package com.Project_Management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project_Management.DTO.Dashboardresponse.DashboardDTO;
import com.Project_Management.Services.DashboardServices;

@RestController
public class DashboardController {

    @Autowired
    private DashboardServices dashboardServices;

    @GetMapping("/dashbord")
    public DashboardDTO Dashboardresponse(){
        return dashboardServices.Dashboardresponse();
    }
}
