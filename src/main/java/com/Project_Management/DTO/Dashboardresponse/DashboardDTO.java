package com.Project_Management.DTO.Dashboardresponse;


import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DashboardDTO {

    private KpiMetricsDTO kpimetrics;
    private List<RiskAlertDTO> riskalert;
    private List<RecentActivitiesDTO> recentactivities;
    private ProjectstatusDTO projecthealth; 
}
