package com.Project_Management.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project_Management.DTO.AdminDashboardresponse.AdminDashboardDTO;
import com.Project_Management.DTO.AdminDashboardresponse.KpiMetricsDTO;
import com.Project_Management.DTO.AdminDashboardresponse.ProjectstatusDTO;
import com.Project_Management.DTO.AdminDashboardresponse.RecentActivitiesDTO;
import com.Project_Management.DTO.AdminDashboardresponse.RiskAlertDTO;
import com.Project_Management.Models.Projectstatus;
import com.Project_Management.Models.UserRole;
import com.Project_Management.Models.Users;
import com.Project_Management.Repositories.ActivityRepo;
import com.Project_Management.Repositories.ProjectRepo;
import com.Project_Management.Repositories.UsersAuthRepo;
import com.Project_Management.Services.AdminDashboardServices;

@Service
public class AdminDashboardServicesImpl implements AdminDashboardServices {

    @Autowired
    private ActivityRepo activityRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired 
    private UsersAuthRepo usersAuthRepo;

    @Override
    public AdminDashboardDTO Dashboardresponse() {
        AdminDashboardDTO dto = new AdminDashboardDTO();
        int totalactiveprojects = (int) projectRepo.count();
        int totalclients = (int) usersAuthRepo.countByRole(UserRole.CLIENT);
        double teamUtilization = 10;
        Long monthlyrevenue = 100000l;

        KpiMetricsDTO kDto = new KpiMetricsDTO(totalactiveprojects, totalclients, teamUtilization, monthlyrevenue);

        List<RecentActivitiesDTO> activitiesDTOs = activityRepo.findTop5ByOrderByTimestampDesc()
                .stream()
                .map(a -> new RecentActivitiesDTO(
                        a.getId(),
                        a.getType(),
                        a.getUser(),
                        a.getMessage(),
                        a.getTimestamp()))
                .toList();

        List<RiskAlertDTO> riskAlertDTOs = getRiskAlertProject();

        ProjectstatusDTO projectstatusDTO = new ProjectstatusDTO(getactiveproject(), getcompletedproject(),
                getriskproject(), getcriticalproject());

        dto.setKpimetrics(kDto);
        dto.setRecentactivities(activitiesDTOs);
        dto.setRiskalert(riskAlertDTOs);
        dto.setProjecthealth(projectstatusDTO);

        return dto;
    }

    private int getcriticalproject() {
        int criticalprojectcount = 0;
        Projectstatus status = Projectstatus.critical;
        criticalprojectcount = projectRepo.countByStatus(status);
        return criticalprojectcount;
    }

    private int getriskproject() {
        int criticalprojectcount = 0;
        Projectstatus status = Projectstatus.at_risk;
        criticalprojectcount = projectRepo.countByStatus(status);
        return criticalprojectcount;
    }

    private int getcompletedproject() {
        int criticalprojectcount = 0;
        Projectstatus status = Projectstatus.completed;
        criticalprojectcount = projectRepo.countByStatus(status);
        return criticalprojectcount;
    }

    private int getactiveproject() {
        int criticalprojectcount = 0;
        Projectstatus status = Projectstatus.active;
        criticalprojectcount = projectRepo.countByStatus(status);
        return criticalprojectcount;
    }

    private List<RiskAlertDTO> getRiskAlertProject() {
        return projectRepo.findAll().stream()
                .filter(project -> project.getStatus() == Projectstatus.critical
                        || project.getStatus() == Projectstatus.at_risk)
                .map(project -> {
                    int managerId = project.getMembers().stream()
                            .filter(member -> member.getRole() == UserRole.MANAGER)
                            .map(Users::getId)
                            .findFirst()
                            .orElse(0);

                    return new RiskAlertDTO(
                            managerId,
                            project.getId(),
                            project.getStatus().name(),
                            project.getDeliverydate() != null
                                    ? project.getDeliverydate().atStartOfDay().toInstant(java.time.ZoneOffset.UTC)
                                    : null);
                })
                .toList();
    }

}
