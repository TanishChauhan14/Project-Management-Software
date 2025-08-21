package com.Project_Management.DTO.AdminDashboardresponse;


import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDashboardDTO {

    private KpiMetricsDTO kpimetrics;
    private List<RiskAlertDTO> riskalert;
    private List<RecentActivitiesDTO> recentactivities;
    private ProjectstatusDTO projecthealth; 
}
