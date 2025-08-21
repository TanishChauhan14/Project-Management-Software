package com.Project_Management.Services;

import org.springframework.transaction.annotation.Transactional;

import com.Project_Management.DTO.EmployeeDashboardresponse.EmployeeDashboardDTO;


@Transactional(readOnly = true)
public interface EmployeeDashboardServices {

    EmployeeDashboardDTO EmployeeDashboardresponse(int employeeid); 

}
